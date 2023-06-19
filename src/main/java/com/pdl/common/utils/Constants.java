package com.pdl.common.utils;

import java.io.File;

public class Constants {
    public static final char EOF = '\u001a';
    public static final int STR_MAX_SIZE = 64;
    public static final int MAX_INT = 32767;

     // Paths
    public static final String HOME = new File("").getAbsolutePath();
    public static final String TEST_FOLDER = HOME + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;
    public final static String OUTPUT = TEST_FOLDER + "outfiles" + File.separator;
    


    public static final String eof = "Teof";
    public static final String let = "ResLet";
    public static final String id = "ID";
    public static final String semicolon = "SemCol";
    public static final String ifKw = "CondIf";
    public static final String parenthesesOpen = "ParOpen";
    public static final String parenthesesClose = "ParClose";
    public static final String curlyBraceOpen = "KeyOpen";
    public static final String curlyBraceClose = "KeyClose";
    public static final String whileKw = "LoopWhile";
    public static final String MOD = "MOD";
    public static final String doKw = "LoopDo";
    public static final String elseKw = "Else";
    public static final String function = "FunID";
    public static final String returnKw = "Return";
    public static final String input = "ResIn";
    public static final String print = "ResPrint";
    public static final String intType = "TypeInt";
    public static final String booleanType = "TypeBool";
    public static final String stringType = "TypeString";
    public static final String AND = "AND";
    public static final String GT = "GT";
    public static final String equals = "AsValue";
    public static final String cad = "Cad";
    public static final String num = "CteInt";
    public static final String increment = "ResAutoSum";
    public static final String comma = "Com";
    public static final String andOperator = "AND";
    public static final String trueKw = "TokT";
    public static final String falseKw = "TokF";
}
