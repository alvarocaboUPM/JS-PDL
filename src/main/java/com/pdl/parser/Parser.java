package com.pdl.parser;

import java.io.IOException;

import com.pdl.common.ErrorAt;
import com.pdl.common.interfaces.ASin;
import com.pdl.common.interfaces.TS;
import com.pdl.common.utils.Constants;
import com.pdl.lexer.Lexer;
import com.pdl.lexer.lib.Token;

public class Parser implements ASin {

    private String result;
    private Lexer lexer;
    private static Token tk;
    private TS t;

    public Parser(TS t) {
        result = "D\t ";
        lexer = new Lexer(t);
        this.t = t;
    }

    protected String parserDebug(String testFile){
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
                result += "1 ";
                DEC();
                return START();

            // 2. Sentencias simples
            case Constants.id:
            case Constants.print:
            case Constants.input:
            case Constants.increment:
                result += "2 ";
                SEN();
                return START();

            // 3. Sentencias complejas
            case Constants.ifKw:
            case Constants.doKw:
                result += "3 ";
                SENCOM();
                return START();

            case Constants.eof:
                result += "4 ";
                break;

            default:
                if (tk.isType()) {
                    ErrorAt.ezError(213, debugString());
                    break;
                } else {
                    ErrorAt.ezError(100, debugString());
                    break;
                }
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

            getNext();
            ckParOp();
            PARM();

            getNext();
            ckKeyOp();
            BODY();
            getNext();
            ckKeyCl();

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
        if (checkTk(Constants.parenthesesClose)) {
            result += "7 ";
            return;
        }
        result += "6 ";
        T(); // Should already be at type token
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
        if (checkTk(Constants.parenthesesClose)) {
            result += "9 ";
            return;
        } else
            ErrorAt.ezError(113, debugString());

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
            FCALL();
            return;
        }
        if (checkTk(Constants.equals)) {
            result += "18 ";
            ASIGN();
        }
    }

    @Override
    public void ASIGN() {
        // Cursor sobre el =
        result += "19 ";
        callEXP();
    }

    @Override
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
        getNext();
        if (checkTk(Constants.comma)) {
            result += "22 ";
            callEXP();
            FCALLX();
        }
        // TODO: OJO
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
            return ;
        } else {
            ErrorAt.ezError(109, debugString());
        }
    }

    @Override
    public void SENCOM() {
        if (checkTk(Constants.ifKw)) {
            result += "26 ";
            getNext();
            ckParOp();
            callEXP();

            IFX();
            return;
        }
        if (checkTk(Constants.doKw)) {
            result += "33 ";
            getNext();
            ckKeyOp();
            BODY();

            getNext();
            ckKeyCl();

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
            ckKeyCl();
        } else {
            result += "28 ";
            SENB();
        }
    }

    @Override
    public void SENB() {
        if (checkTk(Constants.returnKw)) {
            result += "30 ";
            RX();
        } else {
            result += "29 ";
            SEN();
        }
    }

    @Override
    public void RX() {
        getNext();
        if (checkTk(Constants.semicolon)) {
            result += "32 ";
            return;
        }
        result += "31 ";

        EXP(); // Already in the expression
    }

    @Override
    public void BODY() {
        if (checkTk(Constants.semicolon)) {
            result += "37 ";
            return;
        }

        if (checkTk(Constants.let)) {
            result += "35 ";
            DECID();
            BODY();
        }

        else if (checkTk(Constants.ifKw, Constants.whileKw)) {
            result += "34 ";
            SENCOM();
        } else {
            result += "36 ";
            SENB();
        }

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
        // TODO: Ojo con este tb
        if (checkTk(Constants.semicolon, Constants.parenthesesClose)) {
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
        }
    }

    @Override
    public void XPX() {
        getNext();
        // TODO: Ojo a este getNext
        if (!checkTk(Constants.parenthesesOpen)) {
            result += "48 ";
            return;
        }

        result += "47 ";
        FCALL();
    }

    @Override
    public void INC() {
        result += "49 ";
        getNext();
        ckID();
        getNext();
        ckSemCol();
    }

    @Override
    public void TX() {
        if (!tk.isType()) {
            result += "51 ";
            return;
        }

        result += "50 ";
        T();
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

    @Override
    public void CTE() {
        switch (tk.getType()) {
            case Constants.cad:
                result += "55 ";
                break;
            case Constants.num:
                result += "56 ";
                break;
            case Constants.trueKw:
                result += "57 ";
                break;
            case Constants.falseKw:
                result += "57 ";
                break;
            default:
                ckCte();
        }

    }

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
    private boolean checkTk(String... cmp) {
        for (String string : cmp) {
            if (tk.getType().equals(string))
                return true;
        }
        return false;
    }

    private void ckID() {
        if (!checkTk(Constants.id))
            ErrorAt.ezError(105, debugString());
    }

    private void ckParOp() {
        if (!checkTk(Constants.parenthesesOpen))
            ErrorAt.ezError(103, debugString());
    }

    private void ckKeyOp() {
        if (!checkTk(Constants.curlyBraceOpen))
            ErrorAt.ezError(104, debugString());
    }

    private void ckKeyCl() {
        if (!checkTk(Constants.curlyBraceClose))
            ErrorAt.ezError(114, debugString());
    }

    private void ckSemCol() {
        if (!checkTk(Constants.semicolon))
            ErrorAt.ezError(107, debugString());
    }

    private void ckWhile() {
        if (!checkTk(Constants.whileKw))
            ErrorAt.ezError(118, debugString());
    }

    private void ckCte() {
        if (!checkTk(Constants.cad, Constants.num, Constants.falseKw, Constants.trueKw))
            ErrorAt.ezError(111, debugString());
    }

    private void ckType() {
        if (!checkTk(Constants.stringType, Constants.booleanType, Constants.intType))
            ErrorAt.ezError(106, debugString());
    }

    private void callTX() {
        getNext();
        TX();
    }

    private void callT() {
        getNext();
        T();
    }

    private void callEXP() {
        getNext();
        EXP();
    }

    private void callCTE() {
        getNext();
        CTE();
    }

}
