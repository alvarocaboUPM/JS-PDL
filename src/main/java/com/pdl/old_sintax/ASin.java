package com.pdl.old_sintax;

import java.io.*;
import java.util.List;

import com.pdl.Compiler;
import com.pdl.common.ErrorAt;
import com.pdl.lexer.ALex;
import com.pdl.lexer.lib.*;
import com.pdl.old_sintax.expresion.ExpNode;
import com.pdl.old_sintax.expresion.ExpTree;
import com.pdl.old_sintax.expresion.Expresion;
import com.pdl.old_sintax.expresion.Expresion.Insertion;

public class ASin {

    /* GLOBAL VARIABLES */
    static Token tk; // cursor
    static SymbolAt id, funcID; // current symbol
    static int CurrID, nParams, nArgs, OffsetG, OffsetL;// Counters
    static String trace, LastType, ExpType;
    static ALex lexer;
    static Expresion e;
    static ExpNode cursor;

    // Data structures for handling operator preference and associativity
    static ExpTree expresions;
    public static String TabLex;
    // Flagset
    public static boolean inFunc, inCond, inVarDec, noErr,
            inParms, setID, inFCall, nestedExp, inAss;

    /**
     * Asks lexer for a new token and handles null tokens
     */
    static void getNext() {
        Token aux;
        try {
            if ((aux = lexer.nexToken()) != null) {
                tk = aux;
            } else {
                ParseLib.ezError(120);
                getNext();
            }
        } catch (IOException e) {
            ErrorAt.ezError(3, "Leyendo un nuevo token");
            e.printStackTrace();
        }
    }

    /**
     * Asin constructor that builds the parsing trace
     * 
     * @return Full parsing trace
     */
    public static String Parser() {
        lexer = new ALex();
        cursor = new ExpNode();
        expresions = new ExpTree(cursor);
        e = new Expresion();
        trace = "D\t ";
        Compiler.ts.changeScope(true);
        nParams = OffsetG = OffsetL = 0;
        inFunc = inCond = inVarDec = nestedExp = inAss = false;
        return Start();
    }

    /**
     * Starts decision tree
     * 
     * @return String
     */
    private static String Start() {
        getNext();
        switch (tk.getType()) {
            case "ResLet":
            case "ResPrint":
            case "ResIn":
            case "LoopDo":
            case "ResAutoSum":
            case "CondIf":
            case "ID":
            case "Return": {
                trace += "1 ";
                SENA();
                return Start();
            }
            case "FunID": {
                trace += "2 ";
                inFunc = true;
                FUN();
                return Start();
            }
            case "Teof": {
                trace += "3 ";
                break;
            }

            default: {
                if (tk.isType()) {
                    ParseLib.ezError(213);
                    break;
                } else {
                    ParseLib.ezError(100);
                    break;
                }
            }
        }

        try {
            Compiler.FParser.write(trace);
        } catch (IOException e) {
            ErrorAt.ezError(3, " Escribiendo la traza en archivo");
        }

        return trace;
    }

    /**
     * Sentencias condicionales y declaraciones
     */
    static void SENA() {
        switch (tk.getType()) {
            case "CondIf": {
                trace += "31 ";
                IFX();
                break;
            }
            case "ResLet": {
                trace += "32 ";
                inVarDec = true;
                DECL();
                break;
            }
            case "LoopDo": {
                trace += "33 ";
                LOOP();
                break;
            }

            case "ResAutoSum":
            case "ResPrint":
            case "ResIn":
            case "Return":
            case "ID": {
                trace += "35 ";
                SENB();
                break;
            }
            default: {
                ParseLib.ezError(102);
            }
        }
    }

    /**
     * Parses IF conditionals
     */
    static void IFX() {
        trace += "42 ";
        getNext();
        if (tk.getType() != "ParOpen")
            ParseLib.ezError(103);
        getNext();
        EXP();
        if (tk.getType() != "ParClose") {
            ParseLib.ezError(113);
        }
        while (tk.getType() == "ParClose") {
            getNext();
        }
        // Abro llave o SENB
        if (tk.getType() == "KeyOpen") {
            trace += "43 ";
            BODY();
        } else {
            trace += "44 ";
            SENB();
        }
    }

    /**
     * Handles expressions and operands
     */
    static void EXP() {
        switch (tk.getType()) {
            case "ParOpen":
            case "ID":
            case "CteInt":
            case "TokT":
            case "TokF":
            case "Cad": {
                VALUE();
                EXPX();
                break;
            }
            case "ResAutoSum": {
                trace += "10 ";
                INC();
                getNext();
                EXPX();
                break;
            }
            default:
                ParseLib.ezError(221);
        }
    }

    /**
     * Handles > && % and entering a ending an expresion
     */
    static void EXPX() {
        if (!tk.isOperator()) {
            trace += "14 ";
            if (e.getFree() != Insertion.LEFT || e.isEmpty())
                e.clear();
            else {
                cursor.addChild(new ExpNode(e.clear()));
            }
            if (tk.getType().equals("ParClose")) {
                if (cursor.isRoot()) {
                    try {
                        // Aqui has acabado toda la expresion
                        for (List<ExpNode> nodesList : expresions.findValidPath()) {
                            int i = 1;
                            for (ExpNode node : nodesList) {
                                System.out.println(i + ") " + node.getValue().toString() + "\n");
                                i++;
                            }
                            i = 0;
                        }
                        ParseLib.endTree();

                    } catch (NullPointerException e) {
                        System.out.println("Null Pointer en el árbol");
                    }

                } else {
                    cursor = cursor.getParent();
                    getNext();
                }

            }
            return;
        }
        e.insert(tk);

        switch (tk.getType()) {
            case "GT": {
                trace += "11 ";
                e.setTipo(Token.Tipado.BOOL);
                getNext();
                EXP();
                break;
            }
            case "AND": {
                trace += "12 ";
                e.setTipo(Token.Tipado.BOOL);
                getNext();
                EXP();
                break;
            }
            case "MOD": {
                trace += "13 ";
                e.setTipo(Token.Tipado.INT);
                getNext();
                EXP();
                break;
            }
            default: {
                ParseLib.ezError(220);
            }
        }
    }

    /**
     * Handles {@link Expresion} operands
     */
    static void VALUE() {
        trace += "9 ";
        switch (tk.getType()) {
            case "ID": {
                trace += "15 ";
                ParseLib.setID();
                ParseLib.insertOperand();
                getNext();
                XPX();
                break;
            }
            case "ParOpen": {
                trace += "17 ";
                cursor = cursor.addChild(new ExpNode(e.clear()));
                getNext();
                EXP();
                break;
            }
            case "CteInt":
            case "TokT":
            case "TokF":
            case "Cad": {
                trace += "16 ";
                CTE();
                break;
            }

            case "SemCol":
                // Expresión vacía

            default: {
                ParseLib.ezError(221);
            }
        }
    }

    /**
     * Handles constant declarations
     */
    static void CTE() {
        switch (tk.getType()) {
            case "Cad":
                trace += "4 ";
                break;
            case "CteInt":
                trace += "5 ";
                break;
            case "TokT":
                trace += "6 ";
                break;
            case "TokF":
                trace += "7 ";
                break;
            default:
                ErrorAt.ezError(215, tk.getType());
        }
        ParseLib.insertOperand();
        getNext();
    }

    /**
     * Permite utilizar el retorno de una función en una expresión
     */
    static void XPX() {
        if (tk.getType() == "ParOpen") {
            // if (id.getType() != "Function")
            // ParseLib.ezError(117);
            // LastType = Compiler.ts.lookAtIndex(CurrID).getReturnType();
            SymbolAt tmp = id;
            trace += "18 ";
            getNext();
            inFCall = true;
            nArgs = 0;
            FCALL();
            if (nArgs != tmp.getNumParams())
                ParseLib.ezError(204, tmp.getLexema());
            inFCall = false;

        } else
            ParseLib.CheckExplicitness();
        trace += "19 ";// lambda
    }

    /**
     * Handles function calls
     */
    static void FCALL() {
        if (tk.getType() != "ParClose") {
            trace += "45 ";
            EXP();
            nArgs++;
            FCALLX();
        } else {
            trace += "46 ";
            getNext();
        } // lambda
    }

    /**
     * Function call handle
     */
    static void FCALLX() {
        if (tk.getType() == "Com") {
            trace += "47 ";
            getNext();
            EXP();
            nArgs++;
            FCALLX();
        } else if (tk.getType() == "ParClose") {
            trace += "48 ";
            getNext();
        } // lambda
        else
            ParseLib.ezError(113);
    }

    /**
     * Handles lower scope declarations
     */
    static void BODY() {
        if (tk.getType() == "KeyClose") {
            Compiler.ts.changeScope(true);
            trace += "41 ";
            return;
        }
        if (tk.getType() == "KeyOpen") {
            getNext();
        }
        trace += "40 ";
        SENA();
        getNext();
        BODY();
    }

    /**
     * Variable declaration handler
     */
    static void DECL() {
        trace += "21 ";
        getNext();
        ParseLib.setID();
        getNext();
        TD();
        id.setOffset(inFunc ? OffsetL : OffsetG);
        ParseLib.IncOffset(tk.getType());
        id.setType(tk.getType());
        getNext();
        DECLX();
        // if(tk.getType() != "SemCol") ezError(107);
        inVarDec = false;
    }

    /**
     * Gestión de tipos
     */
    static void TD() {

        switch (tk.getType()) {
            case "TypeInt":
                trace += "24 ";
                break;
            case "TypeString":
                trace += "25 ";
                break;
            case "TypeBool":
                trace += "26 ";
                break;
            default:
                id.setType("Unknown");
                ParseLib.ezError(106);
        }
    }

    /**
     * Typing handle for function declaration
     */
    static void TDX() {
        if (tk.getType() == "ParOpen") {
            funcID.setReturnType("Void");
            trace += "28 ";
        } else {
            trace += "27 ";
            TD();
            funcID.setOffset(OffsetG);
            ParseLib.IncOffset(tk.getType());
            funcID.setReturnType(tk.getType());
            getNext();
        }

    }

    /**
     * Aux declaration handle
     */
    static void DECLX() {
        if (tk.getType() == "AsValue") {
            trace += "22 ";
            inAss = true;
            ASIGN();
            inAss = false;
        } else {
            trace += "23 ";
        }
    }

    /**
     * Handles loop declaration
     */
    static void LOOP() {
        getNext();
        if (tk.getType() != "KeyOpen")
            ParseLib.ezError(104);
        else {
            getNext();
            BODY();
            getNext();
            WILE();
        }
    }

    /**
     * Handles WHILE loop
     */
    static void WILE() {
        trace += "34 ";
        if (!tk.getType().equals("LoopWhile")) {
            ParseLib.ezError(118);
        }
        getNext();
        if (tk.getType() != "ParOpen")
            ParseLib.ezError(103);
        else {
            getNext();
            EXP();
            if (tk.getType() != "ParClose")
                ParseLib.ezError(113);
            getNext();
            if (tk.getType() != "SemCol")
                ParseLib.ezError(107);

            if (!expresions.isEmpty())
                ParseLib.endTree();
        }
    }

    /**
     * Handles if conditionals with no { } structure
     * Handles sentences with IO operations or ++
     */
    public static void SENB() {
        switch (tk.getType()) {
            case "ID": {
                trace += "36 ";
                IDX();
                break;
            }
            case "ResPrint":
            case "ResIn": {
                trace += "37 ";
                INOUT();
                break;
            }
            case "Return": {
                trace += "38 ";
                RX();
                if (tk.getType() != "SemCol")
                    ParseLib.ezError(107);
                break;
            }
            case "ResAutoSum": {
                trace += "39 ";
                INC();
                getNext();
                if (!tk.getType().equals("SemCol"))
                    ParseLib.ezError(107);
                e.clear();
                break;
            }
            default: {
                ParseLib.ezError(108);
            }
        }
    }

    /**
     * Hadles value assigment to vars and function calls
     */
    static void IDX() {
        ParseLib.setID();
        ParseLib.CheckExplicitness();
        getNext();
        if (tk.getType() == "AsValue") {
            trace += "51 ";
            ASIGN();
        } else if (tk.getType() == "ParOpen") {
            // Funciones
            trace += "52 ";
            getNext();
            // Comprobar numero de parametros
            inFCall = true;
            FCALL();
            if (tk.getType() != "SemCol")
                ParseLib.ezError(107);
        } else
            ParseLib.ezError(103);
    }

    /*
     * Marks '=' symbol and expects value
     */
    static void ASIGN() {
        trace += "20 ";
        getNext();
        EXP();
        if (tk.getType() != "SemCol")
            ParseLib.ezError(107);

    }

    /**
     * Handles IO operations
     */
    static void INOUT() {
        if (tk.getType() == "ResIn") {
            trace += "30 ";
            getNext();
            if (ParseLib.setID()) {
                ParseLib.CheckExplicitness();
                if (id.getType() != "TypeInt" && id.getType() != "TypeString")
                    ParseLib.ezError(214);
                getNext();
                if (tk.getType() != "SemCol")
                    ParseLib.ezError(107);
            }
        } else if (tk.getType() == "ResPrint") {
            trace += "29 ";
            getNext();
            EXP();
            if (tk.getType() != "SemCol")
                ParseLib.ezError(107);
            if (!expresions.isEmpty())
                ParseLib.endTree();
        } else
            ParseLib.ezError(109);
    }

    /**
     * Handles ending expression with ';'
     */
    static void RX() {
        getNext();
        if (tk.getType() == "SemCol")
            trace += "50 ";
        else {
            trace += "49 ";
            EXP();
        }
    }

    /**
     * Handles ++
     * 
     * @implNote Asumimos que la documentación especifica que el
     *           operador solo puede afectar a variables, en ningún caso expresiones
     */
    public static void INC() {
        trace += "8 ";
        getNext();
        ParseLib.setID();
        ParseLib.CheckExplicitness();
        ParseLib.insertOperand();
        if (!id.getType().equals("TypeInt"))
            ParseLib.ezError(205);
    }

    /**
     * Handles function declaration
     */
    public static void FUN() {
        trace += "53 ";
        getNext();
        try {
            funcID = Compiler.ts.lookAtIndex((int) tk.getInfo());
        } catch (NullPointerException e) {
            return;
        }
        TabLex = funcID.getLexema();
        //
        funcID.setType("Function");
        funcID.setOffset(OffsetG);
        getNext();
        TDX();
        inFunc = true;
        //
        if (tk.getType() != "ParOpen")
            ParseLib.ezError(103);
        ParseLib.toLowerSc();
        getNext();
        inParms = true;
        PARAM();
        inParms = false;
        funcID.setNumParams(nParams);
        getNext();
        if (tk.getType() != "KeyOpen") {
            ParseLib.ezError(104);
        }
        BODY();
        //

        OffsetG += OffsetL;
        OffsetL = 0;
        //
        Compiler.ts.changeScope(true);
        inFunc = false;
        nParams = 0;
    }

    /**
     * Handles function parameters
     */
    static void PARAM() {
        if (tk.isType()) {
            trace += "54 ";
            TD();
            funcID.addTypesParams(tk.getType());
            LastType = tk.getType();
            getNext();
            if (tk.getType() != "ID")
                ParseLib.ezError(103);
            else {
                ParseLib.setID();
                id.setOffset(OffsetL);
                ParseLib.IncOffset(LastType);
                id.setType(LastType);
                getNext();
                nParams++;
                PARAMX();
            }
        } else if (tk.getType() == "ParClose")
            trace += "55 ";
        else
            ParseLib.ezError(113);
    }

    /**
     * Handles following function params
     */
    static void PARAMX() {
        if (tk.getType() == "Com") {
            getNext();
            if (tk.isType()) {
                trace += "56 ";
                TD();

                funcID.addTypesParams(tk.getType());
                LastType = tk.getType();
                nParams++;
                getNext();
                if (ParseLib.setID()) {
                    CurrID = (int) tk.getInfo();
                    id.setOffset(OffsetL);
                    ParseLib.IncOffset(LastType);
                    id.setType(LastType);
                    getNext();
                    PARAMX();
                }
            }
        } else if (tk.getType() == "ParClose")
            trace += "57 ";
        else
            ParseLib.ezError(113);
    }
}
