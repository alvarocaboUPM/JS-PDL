package com.pdl.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.pdl.common.ErrorAt;
import com.pdl.common.interfaces.ASin;
import com.pdl.common.interfaces.TS;
import com.pdl.common.utils.Constants;
import com.pdl.lexer.Lexer;
import com.pdl.lexer.lib.SymbolAt;
import com.pdl.lexer.lib.Token;
import com.pdl.parser.lib.Exprss;

public class Parser implements ASin {

    private String result;
    private Lexer lexer;
    private static Token tk;
    private TS t;
    private SymbolAt id, funcID; // current symbol
    private int CurrID, nParams, nArgs, OffsetG, OffsetL;// Counters
    private String LastType, TypeToCmp;
    // for evaluating expresions
    private Queue<List<String>> ExpQueue = new LinkedList<>();
    private List<String> tmpExp, Emded;
    private List<String> tmpArgs;
    private String TabLex;
    // Flagset
    private boolean inFunc, inCond, inVarDec, noErr,
            inParms, setID, inFCall, nestedExp, inAss;

    public Parser(TS t) {
        result = "D\t ";
        lexer = new Lexer(t);
        nParams = OffsetG = OffsetL = 0;
        inFunc = inCond = inVarDec = nestedExp = inAss = false;
        this.t = t;
    }

    protected String parserDebug(String testFile) {
        result = "D\t ";
        lexer = new Lexer(t, testFile);

        return START();
    }

    public String getResult() {
        return this.result;
    }

    private void getNext() {
        try {
            tk = lexer.nxToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String START() {
        getNext();
        switch (tk.getType()) {
            // 1. Declaraciones
            case Constants.function:
            case Constants.let:
            case Constants.id:
            case Constants.print:
            case Constants.input:
            case Constants.increment:
            case Constants.ifKw:
            case Constants.doKw:
                result += "3 ";
                SENCOM();
                return START();

            // 3. Fin Del Analisis
            case Constants.eof:
                result += "3 ";
                break;

            default:
                if (tk.isType()) {
                    ErrorAt.ezError(213, debugString());

                } else {
                    ErrorAt.ezError(100, debugString());
                }
                panic();
                return START();
        }
        return result;
    }

    @Override
    public void DEC() {
        if (checkTk(Constants.function)) {
            result += "5 ";
            getNext();
            ckID();
            callTX();

            ckParOp();
            PARM();

            getNext();
            ckKeyOp();
            BODY();

            return;
        }

        if (checkTk(Constants.let)) {
            result += "10 ";
            DECID();
            return;
        }
    }

    @Override
    public void PARM() {
        getNext();
        ckParOp();
        inCond = true;
        getNext();
        ckID();
        PARMX();
    }

    @Override
    public void PARMX() {
        getNext();
        if (checkTk(Constants.comma)) {
            result += "8 ";
            callT();
            getNext();
            ckID();
            PARMX();
        }

    }

    @Override
    public void DECID() {
        result += "11 ";
        getNext();
        ckID();
        callT();
        DECLX();
    }

    @Override
    public void DECLX() {
        getNext();
        if (checkTk(Constants.equals)) {
            result += "12 ";
            ASIGN();
        }
        if (checkTk(Constants.semicolon)) {
            result += "13 ";
        } else
            ErrorAt.ezError(107, debugString());
    }

    @Override
    public void SEN() {
        switch (tk.getType()) {
            case Constants.id:
                result += "14 ";
                ASCALL();

                return;
            case Constants.print:
            case Constants.input:
                result += "15 ";
                IO();
                return;
            case Constants.increment:
                result += "16 ";
                INC();

                break;
        }
    }

    @Override
    public void ASCALL() {
        getNext();
        if (checkTk(Constants.parenthesesOpen)) {
            result += "17 ";
            // TODO Set Priority??
            getNext();
            EXP();
            ckParCl();
            tmpExp.add("ParClose");
            getNext();
            ckSemCol();
            return;
        }
        if (checkTk(Constants.equals)) {
            result += "18 ";
            ASIGN();

        }
    }

    /**
     * Handles function calls
     */
    public void FCALL() {
        getNext();
        if (checkTk(Constants.parenthesesClose)) {
            result += "21 ";

            return;
        }
        result += "20 ";
        EXP(); // Ya sobre la expresion
        FCALLX();
    }

    @Override
    public void FCALLX() {
        if (checkTk(Constants.comma)) {
            result += "22 ";
            callEXP();
            FCALLX();
            return;
        }

        if (checkTk(Constants.parenthesesClose)) {
            result += "23 ";
            return;
        } else
            ErrorAt.ezError(113, debugString());
    }

    @Override
    public void IO() {
        if (checkTk(Constants.print)) {
            result += "24 ";
            callEXP();
            return;
        }
        if (checkTk(Constants.input)) {
            getNext();
            ckID();
            getNext();
            ckSemCol();
            return;
        } else {
            ErrorAt.ezError(109, debugString());
        }
        result += "40 ";
        SENA();
        getNext();
        BODY();
    }

    /**
     * Variable declaration handler
     */
    public void DECL() {
        result += "21 ";
        getNext();
        ckID();
        try {
            id = t.lookAtIndex((int) tk.getInfo());
        } catch (Exception e) {
            return;
        }
        if (checkTk(Constants.doKw)) {
            result += "33 ";
            getNext();
            ckKeyOp();
            BODY();

            getNext();
            ckWhile();
            getNext();
            ckParOp();
            callEXP();

            getNext();
            ckSemCol();

        }
    }

    @Override
    public void IFX() {
        getNext();
        if (checkTk(Constants.curlyBraceOpen)) {
            result += "27 ";
            BODY();

            getNext();
        }

    }

    @Override
    public void SENB() {
        if (checkTk(Constants.returnKw)) {
            result += "30 ";
            RX();
        } else {
            result += "23 ";
        }
    }

    /**
     * Handles loop declaration
     */
    public void LOOP() {
        getNext();
        ckKeyOp();
        getNext();
        BODY();
        getNext();
        WILE();
    }

    @Override
    public void EXP() {
        if (checkTk(Constants.id, Constants.parenthesesOpen) || tk.isCTE()) {
            result += "38 ";
            VALUE();
            EXPX();
            return;
        }
        if (checkTk(Constants.increment)) {
            result += "39 ";
            INC();
        } else
            ErrorAt.ezError(116, debugString());

    }

    @Override
    public void EXPX() {
        if (checkTk(Constants.semicolon, Constants.comma, Constants.parenthesesClose)) {
            return;
        }

        if (!tk.isOperator()) {
            ErrorAt.ezError(116, debugString());
        }
        switch (tk.getType()) {
            case Constants.GT:
                result += "40 ";
                break;
            case Constants.AND:
                result += "41 ";
                break;
            case Constants.MOD:
                result += "42 ";
                break;
        }
        callEXP();
    }

    @Override
    public void VALUE() {
        if (checkTk(Constants.id)) {
            result += "44 ";
            XPX();
            return;
        }
        if (tk.isCTE()) {
            result += "45 ";
            CTE();
            getNext();
            return;
        }
        if (checkTk(Constants.parenthesesOpen)) {
            result += "46 ";
            callEXP();
            getNext();
        }
    }

    /**
     * Handles ending expression with ';'
     */
    public void RX() {
        getNext();
        // TODO: Ojo a este getNext
        if (!checkTk(Constants.parenthesesOpen)) {
            result += "48 ";
            return;
        }
    }

    /**
     * Handles ++
     *
     * @implNote Asumimos que la documentación especifica que el
     *           operador solo puede afectar a variables, en ningún caso expresiones
     */
    public void INC() {
        result += "8 ";
        getNext();
        ckID();

    }

    /**
     * Handles function declaration
     */
    public void FUN() {
        t.functionOn();
        result += "53 ";
        getNext();
        try {
            funcID = t.lookAtIndex((int) tk.getInfo());
        } catch (Exception e) {
            return;
        }

        result += "50 ";
        T();
        getNext();
    }

    @Override
    public void T() {
        switch (tk.getType()) {
            case Constants.intType:
                result += "52 ";
                break;
            case Constants.stringType:
                result += "53 ";
                break;
            case Constants.booleanType:
                result += "54 ";
                break;
            default:
                ckType();
        }
    }

    /**
     * Handles following function params
     */
    public void PARAMX() {
        if (checkTk(Constants.comma)) {
            getNext();
            if (tk.isType()) {
                result += "56 ";
                TD();
                funcID.addTypesParams(tk.getType());
                LastType = tk.getType();
                nParams++;
                getNext();
                ckID();
                try {
                    id = t.lookAtIndex((int) tk.getInfo());
                } catch (Exception e) {
                    return;
                }
                // CurrID = (int) tk.getInfo();
                id.setOffset(OffsetL);
                IncOffset(LastType);
                id.setType(LastType);
                getNext();
                PARAMX();
                // }
            }
        } else if (checkTk(Constants.parenthesesClose))
            result += "57 ";
        else
            ErrorAt.ezError(113, debugString());

    }
    // ------------------

    private String debugString() {
        return new String("\n- TRAZA -> " + result +
                "\n- ÚLTIMO TK LEIDO -> " + tk.toString());
    }

    /**
     * Returns true if the token equals
     * any of the params
     * 
     * @param cmp
     * @return
     */
    private boolean checkTk(String... cmp) { //
        for (String string : cmp) {
            if (tk.getType().equals(string))
                return true;
        }
        return false;
    }

    private void ckID() {
        if (!checkTk(Constants.id)) {
            ErrorAt.ezError(105, debugString());
            panic();
        }
    }

    private void ckParOp() {
        if (!checkTk(Constants.parenthesesOpen)) {
            ErrorAt.ezError(103, debugString());
            panic();
        }
    }

    private void ckKeyOp() {
        if (!checkTk(Constants.curlyBraceOpen)) {
            ErrorAt.ezError(104, debugString());
            panic();
        }
    }

    private void ckKeyCl() {
        if (!checkTk(Constants.curlyBraceClose)) {
            ErrorAt.ezError(114, debugString());
            panic();
        }
    }

    private void ckSemCol() {
        if (!checkTk(Constants.semicolon)) {
            ErrorAt.ezError(107, debugString());
            panic();
        }
    }

    private void ckWhile() {
        if (!checkTk(Constants.whileKw)) {
            ErrorAt.ezError(118, debugString());
            panic();
        }
    }

    private void ckCte() {
        if (!checkTk(Constants.cad, Constants.num, Constants.falseKw, Constants.trueKw)) {
            ErrorAt.ezError(111, debugString());
            panic();
        }
    }

    private void ckType() {
        if (!checkTk(Constants.stringType, Constants.booleanType, Constants.intType)) {
            ErrorAt.ezError(106, debugString());
            panic();
        }
    }

    private void callTX() {
        getNext();
        TX();
    }

    private void callT() {
        getNext();
        T();
    }

    /**
     * Skips characters until it finds a safe one
     */
    private int panic() {
        int res = 0;
        while (!isSafe(tk.getType())) {
            res++;
        }
        System.err.println("Skipped " + res + " chars");
        return res;
    }

    private static boolean isSafe(String c) {
        String[] safe = { Constants.semicolon, Constants.parenthesesClose, Constants.curlyBraceClose };
        for (String safeChar : safe) {
            if (c == safeChar) {
                return true;
            }
        }
        return false;
    }
}
