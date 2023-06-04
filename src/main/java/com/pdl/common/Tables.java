package com.pdl.common;

import java.util.*;

import com.pdl.lexer.lib.*;

public class Tables {
    List<String> validTokens;
    Map<String, Token> DirToken;
    Map<String, Token> ResWords;
    Map<Integer, String> ErrorHandler;
    Map<Integer, String> Analyzers;

    /**
     * Constructor that fills the tables
     */
    public Tables(){
        validTokens = new ArrayList<String>();
        DirToken = new HashMap<String, Token>();
        ResWords = new HashMap<String, Token>();
        ErrorHandler = new HashMap<Integer, String>();
        Analyzers = new HashMap<Integer, String>();
        fillTables(validTokens, DirToken, ResWords, ErrorHandler, Analyzers);
    }

    private void fillTables(List<String> validTokens,
    Map<String, Token> DirToken,
    Map<String, Token> ResWords,
    Map<Integer, String> ErrorHandler,
    Map<Integer, String> Analyzer){
        //------------VALID TOKENS TABLE ----------

        //TOKENS INMEDIATOS Y RES
        validTokens.add(("TypeBool"));//0
        validTokens.add(("LoopDo")); //1
        validTokens.add(("FunID")); //2
        validTokens.add(("CondIf"));//3
        validTokens.add(("ResIn"));//4
        validTokens.add(("TypeInt"));//5
        validTokens.add(("ResLet"));//6
        validTokens.add(("ResPrint"));//7
        validTokens.add(("Return"));//8
        validTokens.add(("TypeString"));//9
        validTokens.add(("LoopWhile"));//10
        validTokens.add(("ResAutoSum"));//11
        //
        validTokens.add("CteInt");//12
        validTokens.add("Cad");//13
        validTokens.add("ID");//14
        //
        validTokens.add("AsValue");//15
        validTokens.add("Com");//16
        validTokens.add("SemCol");//17
        validTokens.add("ParOpen");//18
        validTokens.add("ParClose");//19
        validTokens.add("KeyOpen");//20
        validTokens.add("KeyClose");//21
        
        //NO TOCAR -> Ordenados en preferencia de operaciones de menor a mayor
        validTokens.add(("MOD"));//22
        validTokens.add(("GT"));//23
        validTokens.add(("AND"));//24 
   
        //*Especial? */
        validTokens.add(("TokT"));//25
        validTokens.add(("TokF"));//26
        validTokens.add(("Teof"));//27

        //------------DIRECT TOKENS TABLE ----------
        DirToken.put("{", new Token(validTokens.get(20), null));
        DirToken.put("}", new Token(validTokens.get(21), null));
        DirToken.put("(", new Token(validTokens.get(18), null));
        DirToken.put(")", new Token(validTokens.get(19), null));
        DirToken.put("=", new Token(validTokens.get(15), null));
        DirToken.put(",", new Token(validTokens.get(16), null));
        DirToken.put(";", new Token(validTokens.get(17), null));
        DirToken.put("++", new Token(validTokens.get(11), null));
        DirToken.put("%", new Token(validTokens.get(22), null));
        DirToken.put(">", new Token(validTokens.get(23), null));

        //-----------RESERVED WORDS TABLE-----------
        ResWords.put("do", new Token(validTokens.get(1), null));
        ResWords.put("while", new Token(validTokens.get(10), null));
        ResWords.put("boolean", new Token(validTokens.get(0), null));
        ResWords.put("int", new Token(validTokens.get(5), null));
        ResWords.put("string", new Token(validTokens.get(9), null));
        ResWords.put("function", new Token(validTokens.get(2), null));
        ResWords.put("let", new Token(validTokens.get(6), null));
        ResWords.put("input", new Token(validTokens.get(4), null));
        ResWords.put("print", new Token(validTokens.get(7), null));
        ResWords.put("return", new Token(validTokens.get(8), null));
        ResWords.put("if", new Token(validTokens.get(3), null));
        //-----------SPECIAL WORDS -----------
        ResWords.put("true", new Token(validTokens.get(25), null));
        ResWords.put("false", new Token(validTokens.get(26), null));


        /* *******************ERROR HANDLER*******************
         * CODE: 1-10 Main || 11-99 Lexer || 100-199 Sintax || 200 - 299 Semantic
         * Lower numbers will handle
        */
        ErrorHandler.putAll(new HashMap<Integer, String>(){{ 
            put(1, "No se encuentra el archivo: ");
            put(2, "NullPointer");
            put(3, "InOut Exception");
            put(4, "Error al volcar la tabla de símbolos");
            //Lexer
            put(11, "SE HA SUPERADO EL MÁXIMO INT");
            put(12, "String demasiado largo");
            put(13, "INVALID TOKEN: ");
            put(14, "No ha formado token -> ");
            put(20, "Utilice commillas dobles para indicar string");
            put(21, "Debe cerrar el comentario");
            put(22, "Operador no disponible: ");
            //Sintax
            put(100, "Error sintáctico genérico");
            put(101, "Se esperaba fin de fichero");
            put(102, "Se esperaba una declaración o un condicional");
            put(103, "Se esperaba el inicio de la expresión: '(");
            put(104, "Se esperaba el inicio de la declaración: '{");
            put(105, "Se esperaba un identificador");
            put(106, "Se esperaba un tipado de la variable");
            put(107, "Se esperaba ';'");
            put(108, "Se esperaba un identificador, ++, return o operacion E-S");
            put(109, "Se esperaba 'ResPrint'");
            put(110, "Se esperaba una variable o una expresión");
            put(111, "Se esperaba una constante");
            put(113, "Se esperaba el cierre de la expresión: ')'");
            put(114, "Se esperaba el cierre de la declaración: '}'");
            put(116, "Expresion Invalida");
            put(117, "Necesaria previa declaracion de las funciones ");
            put(118, "Estructura inválida del bucle do { something } while(cond); ");
            put(120, "El parser ha recibido un token nulo");
            //Semantics
            put(200, "Error semántico genérico");
            put(201, "Se está intentando declarar una variable global en un ámbito local");
            put(202, "Error de unicidad; se ha declarado de nuevo el identificador: ");
            put(203, "Error de unicidad; se ha declarado de nuevo la función: ");
            put(204, "Numero de argumentos invalidos para la funcion: ");
            put(205, "Solo se pueden incrementar variables numericas");
            put(210, "Función no declarada: ");
            put(211, "Variable global no declarada: ");
            put(212, "Variable local no declarada: ");
            put(213, "Use 'let' para declarar variables globales");
            put(214, "las operacion de entrada o salida solo aceptan un valor entero o cadena");
            put(215, "Constante inválida introducida -> ");
            put(220, "Intentando declarar una expresión ternaria");
            put(221, "Intentando declarar una expresión vacía/inválida");
            put(230, "Error de tipo: int % int -> int");
            put(231, "Error de tipo: int > int -> bool");
            put(232, "Error de tipo: bool && bool -> int");
            put(233, "Error de tipo: Expresión sin tipo"); 
         }}); 

         Analyzer.put(0, "UserInput");
         Analyzer.put(1, "Lexer");
         Analyzer.put(2, "Parser");
         Analyzer.put(3, "Semantic");

         /* Las hacemos inmodificables */
         ResWords = Collections.unmodifiableMap(ResWords);
         ErrorHandler = Collections.unmodifiableMap(ErrorHandler);
         validTokens = Collections.unmodifiableList(validTokens);
         DirToken = Collections.unmodifiableMap(DirToken);
         Analyzer = Collections.unmodifiableMap(Analyzer);
    }        

    /* Getters and setters autogenerated */

    public List<String> getValidTokens() {
        return this.validTokens;
    }

    public Map<String,Token> getDirToken() {
        return this.DirToken;
    }

    public Map<String,Token> getResWords() {
        return this.ResWords;
    }

    public Map<Integer,String> getErrorHandler() {
        return this.ErrorHandler;
    }

    public Map<Integer,String> getAnalyzers() {
        return this.Analyzers;
    }



}