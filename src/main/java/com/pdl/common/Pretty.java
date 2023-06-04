package com.pdl.common;
/**
 * Clase que implementa funciones decorativas reciclables 
 * para la terminal
 */
public class Pretty {
    /* Colores */
    public static final String GREEN = "\u001B[32m";
    public static final String ORANGE = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    public static String fMsg(String msg, String format){
        return format + msg + RESET;
    }

    public static void printOK(String str){
        System.out.println(fMsg(str, GREEN));
    }
    public static void printErr(String str){
        System.err.println(fMsg(str, RED));
    }
    public static void printRed(String str){
        System.out.println(fMsg(str, RED));
    }
    public static void printWarn(String str){
        System.out.println(fMsg(str, ORANGE));
    }
}
