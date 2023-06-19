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

//public class Parser implements ASin {
public class Parser {
    private String result;
    private Lexer lexer;
    private static Token tk;
    private TS t;
    static SymbolAt id, funcID; // current symbol
    static int CurrID, nParams, nArgs, OffsetG, OffsetL;// Counters
    static String LastType, TypeToCmp;
    // for evaluating expresions
    static Queue<List<String>> ExpQueue = new LinkedList<>();
    static List<String> tmpExp, Emded;
    static List<String> tmpArgs;
    //static Exprss Evaluator = new Exprss();
    //
    public static String TabLex;
    // Flagset
    public static boolean inFunc, inCond, inVarDec, noErr,
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


    public String START() {
        getNext();
        switch (tk.getType()) {
            // 1. Tratamiento de sentencias

            case Constants.let:
            case Constants.id:
            case Constants.print:
            case Constants.input:
            case Constants.increment:
            case Constants.ifKw:
            case Constants.doKw:
            case Constants.returnKw:
                result += "1 ";
                SENA();
                return START();

            // 2. Tratamiento de funciones

            case Constants.function:
                result += "2 ";
                FUN();
                return START();

            // 3. Fin Del Analisis
            case Constants.eof:
                result += "3 ";
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

    //----
    public void SENA() {
        switch (tk.getType()) {
            case Constants.ifKw: {
                result += "31 ";
                IFX();
                break;
            }
            case Constants.let: {
                result += "32 ";
                inVarDec = true;
                DECL();
                break;
            }
            case Constants.doKw: {
                result += "33 ";
                LOOP();
                break;
            }

            case Constants.increment:
            case Constants.print:
            case Constants.input:
            case Constants.returnKw:
            case Constants.id: {
                result += "35 ";
                SENB();
                break;
            }
            default: {
                //ParseLib.ezError(102); ERROR!!
            }
        }
    }

    //-----------------
    public void IFX() {
        result += "42 ";
        getNext();
        ckParOp();
        inCond = true;
        getNext();
        tmpExp = new ArrayList<>();
        EXP();
        //erro punto y com
        ExpQueue.add(tmpExp);
        //if(!Evaluator.evaluate(ExpQueue.poll()).equals("TypeBool")) ParseLib.ezError(239);
        ckParCl();
        getNext();
        inCond = false;
        if (checkTk(Constants.curlyBraceOpen)){
            result+="43 ";
            BODY();
        }
        else{
            result+="44 ";
            SENB();
        }

    }

    //------------------
    /**
     * Handles expressions and operands
     */
    public void EXP() {

        // cursor.data == null
        switch (tk.getType()) {
            case Constants.parenthesesOpen:
            case Constants.id:
            case Constants.num:
            case Constants.falseKw:
            case Constants.trueKw:
            case Constants.cad: {
                VALUE();
                EXPX();
                break;
            }
            case Constants.increment: {
                result += "10 ";
                INC();
                tmpExp.add(Constants.intType);
                getNext();
                EXPX();
                break;
            }
            default:
                //ParseLib.ezError(221); Error??
        }
        // Fin de la expresión (Lee el ;)
        // ParseLib.operatorPreference();
    }
    //------------------
    /**
     * Handles > && % and entering a ending an expresion
     */
    public void EXPX() {
        switch (tk.getType()) {
            case Constants.GT: {
                tmpExp.add("GT");
                result += "11 ";
                getNext();
                EXP();
                break;
            }
            case Constants.AND: {
                tmpExp.add("AND");
                result += "12 ";
                getNext();
                EXP();
                break;
            }
            case Constants.MOD: {
                tmpExp.add("MOD");
                result += "13 ";
                getNext();
                EXP();
                break;
            }
            default: {
                result += "14 ";
                break;
            }
        }
    }
    //------------------
    /**
     * Handles {@link Expresion} operands
     */
    public void VALUE() {
        result += "9 ";
        if(checkTk(Constants.id)){
            result += "15 ";
            ckID();
            try {
                id = t.lookAtIndex((int) tk.getInfo());
            } catch (Exception e) {
                return;
            }
            //ParseLib.setID();
            getNext();
            XPX();
        }
        else if(checkTk(Constants.parenthesesOpen)){
            tmpExp.add("ParOpen");
            result += "17 ";
            // TODO Set Priority??
            getNext();
            EXP();
            ckParCl();
            tmpExp.add("ParClose");
            getNext();

        }
        else if(checkTk(Constants.num,Constants.trueKw,Constants.falseKw,Constants.cad)){
            result += "16 ";
            CTE();
        }
        //ckSemCol();//TODO revisar
    }
    //------------------
    /**
     * Handles constant declarations
     */
    public void CTE() {
        switch (tk.getType()) {
            case Constants.cad:
                tmpExp.add("TypeString");
                result += "4 ";
                break;
            case Constants.num:
                tmpExp.add("TypeInt");
                result += "5 ";
                break;
            case Constants.trueKw:
                tmpExp.add("TypeBool");
                result += "6 ";
                break;
            case Constants.falseKw:
                tmpExp.add("TypeBool");
                result += "7 ";
                break;
            default:
                //ALex.ezError(215, tk.getType()); TODO error?
        }
        //ParseLib.insertOperand();
        getNext();
    }
    //------------------
    /**
     * Permite utilizar el retorno de una función en una expresión
     */
    public void XPX() {
        if (checkTk(Constants.parenthesesOpen)) {
            if (id.getType() != "Function") //ParseLib.ezError(117); TODO error?
            // LastType = Compiler.ts.lookAtIndex(CurrID).getReturnType();
            tmpExp.add(id.getReturnType()); // TODO review
            SymbolAt tmp = id;
            result += "18 ";
            getNext();

            inFCall = true;
            Emded = tmpExp;
            nArgs = 0;
            //ExpQueue.add(tmpExp);
            FCALL();
            tmpExp = Emded;
            //if(!tmpArgs.equals(tmp.getTypesParams())) ParseLib.ezError(237,tmp.getLexema());
            //if (nArgs != tmp.getNumParams()) ParseLib.ezError(204, tmp.getLexema());
            inFCall = false;

        } else {
            CheckExplicitness();
            tmpExp.add(id.getType());//todo review
            result += "19 ";// lambda
        }
    }
    //------------------

    /**
     * Handles function calls
     */
    public void FCALL() {
        if (!checkTk(Constants.parenthesesClose)) {
            result += "45 ";
            tmpExp = new ArrayList<>();
            EXP();
            ExpQueue.add(tmpExp);
            tmpArgs = new ArrayList<>();
            //tmpArgs.add(Evaluator.evaluate(ExpQueue.poll()));
            nArgs++;
            FCALLX();
        } else {
            result += "46 ";
            getNext();
        } // lambda
    }
    //------------
    /**
     * Function call handle
     */
    public void FCALLX() {
        if (checkTk(Constants.comma)) {
            result += "47 ";
            getNext();
            tmpExp = new ArrayList<>();
            EXP();
            ExpQueue.add(tmpExp);
            //tmpArgs.add(Evaluator.evaluate(ExpQueue.poll()));
            nArgs++;
            FCALLX();
        } else if (checkTk(Constants.parenthesesClose)) {
            result += "48 ";
            getNext();
        } // lambda
        //else TODO check what error to return !
            //ParseLib.ezError(113);
    }
    //------------------

    /**
     * Handles lower scope declarations
     */
    public void BODY() {
        if (checkTk(Constants.curlyBraceClose)) {
            result += "41 ";
            return;
        }
        if (checkTk(Constants.curlyBraceOpen)) {
            getNext();
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
        getNext();
        TD();
        id.setOffset(inFunc ? OffsetL : OffsetG);
        IncOffset(tk.getType());
        id.setType(tk.getType());
        TypeToCmp = tk.getType();
        getNext();
        DECLX();
        // if(tk.getType() != "SemCol") ezError(107);
        inVarDec = false;
    }
    //------------------
    /**
     * Gestión de tipos
     */
    public void TD() {

        switch (tk.getType()) {
            case Constants.intType:
                result += "24 ";
                break;
            case Constants.stringType:
                result += "25 ";
                break;
            case Constants.booleanType:
                result += "26 ";
                break;
            default:
                id.setType("Unknown"); // TODO review
                //ParseLib.ezError(106); TODO review if should ret error
        }
    }

    /**
     * Typing handle for function declaration
     */
    public void TDX() {
        if (checkTk(Constants.parenthesesOpen)) {
            funcID.setReturnType("Void");
            result += "28 ";
        } else {
            result += "27 ";
            TD();
            funcID.setOffset(OffsetG);
            IncOffset(tk.getType());
            funcID.setReturnType(tk.getType());
            getNext();
        }

    }
    //------------------
    /**
     * Aux declaration handle
     */
    public void DECLX() {
        if (checkTk(Constants.equals)) {
            result += "22 ";
            inAss = true;
            ASIGN();
            inAss = false;
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

    /**
     * Handles WHILE loop
     */
    public void WILE() {
        result += "34 ";
        ckWhile(); //checks token =? while
        getNext();
        ckParOp();
        inCond = true;
        getNext();
        tmpExp = new ArrayList<>();
        EXP();
        ExpQueue.add(tmpExp);
        //if(!Evaluator.evaluate(ExpQueue.poll()).equals("TypeBool")) ParseLib.ezError(239);
        getNext();
        inCond = false;
         ckSemCol();

    }
    //------------------
    /**
     * Handles if conditionals with no { } structure
     * Handles sentences with IO operations or ++
     */
    public void SENB() {
        switch (tk.getType()) {
            case Constants.id: {
                result += "36 ";
                IDX();
                break;
            }
            case Constants.print:
            case Constants.input: {
                result += "37 ";
                INOUT();
                break;
            }
            case Constants.returnKw: {
                result += "38 ";
                RX();
                ckSemCol();
                break;
            }
            case Constants.increment: {
                result += "39 ";
                INC();
                getNext();
                ckSemCol();
                break;
            }
            default: {
                //ParseLib.ezError(108); TODO throw error?
            }
        }
    }
    //----------------
    /**
     * Hadles value assigment to vars and function calls
     */
    public void IDX() {
        //ParseLib.setID();
        ckID();
        try {
            id = t.lookAtIndex((int) tk.getInfo());
        } catch (Exception e) {
            return;
        }
        CheckExplicitness();
        SymbolAt tmp = id;
        TypeToCmp = tmp.getType();
        getNext();
        if (checkTk(Constants.equals)) {
            result += "51 ";
            ASIGN();
        } else if (checkTk(Constants.parenthesesOpen)) {
            // Funciones
            result += "52 ";
            getNext();
            // Comprobar numero de parametros
            inFCall = true;
            FCALL();
            //if(!tmpArgs.equals(tmp.getTypesParams())) ParseLib.ezError(237,tmp.getLexema()); Sementic error
            ckSemCol();
        } //else TODO Throw error?
            //ParseLib.ezError(103);
    }

    /*
     * Marks '=' symbol and expects value
     */
    public void ASIGN() {
        result += "20 ";
        getNext();
        tmpExp = new ArrayList<>();
        EXP();
        ExpQueue.add(tmpExp);
        //if(!Evaluator.evaluate(ExpQueue.poll()).equals( TypeToCmp)) ParseLib.ezError(234,"Tipo de la Expresion != " + TypeToCmp); Semantic Error
        ckSemCol();
    }

    /**
     * Handles IO operations
     */
    public void INOUT() {
        if (checkTk(Constants.input)) {
            result += "30 ";
            getNext();
            ckID();
            try {
                id = t.lookAtIndex((int) tk.getInfo());
            } catch (Exception e) {
                return;
            }
            //if (ParseLib.setID()) { TODO review
                CheckExplicitness();
                //tmpExp.add(id.getType());
                //if (id.getType() != "TypeInt" && id.getType() != "TypeString")
                  //  ErrorAt.ezError(214, debugString()); // Semantic Error
                getNext();
               ckSemCol();
           // }
        } else if (checkTk(Constants.print)) {
            result += "29 ";
            getNext();
            tmpExp = new ArrayList<>();
            EXP();
            ExpQueue.add(tmpExp);
            //String tp  = Evaluator.evaluate(ExpQueue.poll());
            //if(!tp.equals("TypeInt") && !tp.equals("TypeString")) ParseLib.ezError(240); semantic Error
            ckSemCol();

        } //else
            //ParseLib.ezError(109); TODO throw error?
    }

    /**
     * Handles ending expression with ';'
     */
    public void RX() {
        getNext();
        if (checkTk(Constants.semicolon)) {
            result += "50 ";
            //if (!funcID.getReturnType().equals("Void")) ParseLib.ezError(238, funcID.getLexema()); semantic error
        }
        else {
            result += "49 ";
            tmpExp = new ArrayList<>();
            EXP();
            ExpQueue.add(tmpExp);
            //if(!Evaluator.evaluate(ExpQueue.poll()).equals(funcID.getReturnType())) ParseLib.ezError(238, funcID.getLexema());
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
        //ParseLib.setID();
        ckID();
        try {
            id = t.lookAtIndex((int) tk.getInfo());
        } catch (Exception e) {
            return;
        }
        CheckExplicitness();
        tmpExp = new ArrayList<>();
        tmpExp.add(id.getType());
        //ParseLib.insertOperand();
        //if (!id.getType().equals("TypeInt")) ParseLib.ezError(205); semantic error?
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
        TabLex = funcID.getLexema();
        //
        funcID.setType("Function");
        funcID.setOffset(OffsetG++);//ver cuanto hay que incrementar, como no se especifica en el enunciado +1??
        getNext();
        TDX();
        inFunc = true;
        //
        ckParOp();
        t.setLocal();
        getNext();
        inParms = true;
        PARAM();
        inParms = false;
        funcID.setNumParams(nParams);
        getNext();
        ckKeyOp();
        BODY();
        //
        OffsetG += OffsetL; //revisar
        OffsetL = 0;
        //
        t.functionOff();
        inFunc = false;
        nParams = 0;
    }

    /**
     * Handles function parameters
     */
    public void PARAM() {
        if (tk.isType()) {
            result += "54 ";
            TD();
            funcID.addTypesParams(tk.getType());
            LastType = tk.getType();
            getNext();
            ckID();
            try {
                id = t.lookAtIndex((int) tk.getInfo());
            } catch (Exception e) {
                return;
            }
            //ParseLib.setID();
            id.setOffset(OffsetL);
            IncOffset(LastType);
            id.setType(LastType);
            getNext();
            nParams++;
            PARAMX();

        } else if (checkTk(Constants.parenthesesClose))
            result += "55 ";
        //else
            //ParseLib.ezError(113); TODO throw error?
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
                //if (ParseLib.setID()) { //review
                    CurrID = (int) tk.getInfo();
                    id.setOffset(OffsetL);
                    IncOffset(LastType);
                    id.setType(LastType);
                    getNext();
                    PARAMX();
                //}
            }
        } else if (checkTk(Constants.parenthesesClose))
            result += "57 ";
        //else
            //ParseLib.ezError(113); todo throw error?
    }
    //------------------

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
        if (!checkTk(Constants.id))
            ErrorAt.ezError(105, debugString());
    }

    private void ckParOp() {
        if (!checkTk(Constants.parenthesesOpen))
            ErrorAt.ezError(103, debugString());
    }
    private void ckParCl() {
        if (!checkTk(Constants.parenthesesClose))
            ErrorAt.ezError(99, debugString());
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
    static void IncOffset(String Type) {
        switch (Type) {
            case "TypeInt":
            case "TypeBool": {
                if (inFunc)
                    OffsetL++;
                else
                    OffsetG++;
                break;
            }
            case "TypeString": {
                if (inFunc)
                    OffsetL += 64;
                else
                    OffsetG += 64;
                break;
            }
        }
    }
     void CheckExplicitness() {
         try {// Debug
             id = t.lookAtIndex((int) tk.getInfo());
             if (id.getType().equals("Unknown")) {
                 id.setType("TypeInt");
                 id.setOffset(OffsetG++);
                 //TODO Decrementar offset de la tabla global??
                 if (inFunc)
                     t.setGlobal(id);
             }
         } catch (Exception e) {
             return;
         }
    }
}
