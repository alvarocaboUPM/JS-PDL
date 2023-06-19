package com.pdl;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pdl.common.ErrorAt;
import com.pdl.common.utils.FilesAt;
import com.pdl.common.utils.Pretty;
import com.pdl.common.utils.Tables;
import com.pdl.parser.Parser;
import com.pdl.symbols.SymbolTable;

/**
 * Frontend class of the JS-PDL FilesAt
 * 
 * @author Álvaro Cabo / Oussama El-Hatifi
 * @version 1.0
 */
public class Compiler {
   
    public static List<Integer> errors;
    static SymbolTable ts;
    static Parser p;
    static String filename, folder, df, ast;

    public static void main(String args[]) {
        askInput();
        init();
        ast = p.START();
        finish();
    }
    
    private static void askInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nSelecciona la temática de la prueba");
        System.out.println("1) Errors");
        System.out.println("2) Sentencias");
        System.out.println("o.c) General");

        folder = sc.nextLine();
        int a;
        try {
            a = (int) Integer.valueOf(folder);
        } catch (Exception e) {
            a = 3;
        }
        
        switch (a) {
            case 1:
            folder = "errors/";
                df = "e1.js";
                break;
                
                case 2:
                folder = "sentencias/";
                df = "s5.js";
                break;
                
            default:
                folder = "";
                df = "t1.js";
                break;
            }
            
            System.out.print("Nombre del archivo a analizar [por defecto " + df + "]: ");
            
            filename = sc.nextLine();
            if (filename.isBlank())
            filename = df;
            sc.close();
        }

        private static void init() {
            // Iniciamos tablas
            ts = new SymbolTable();
            errors = new ArrayList<>();
            // Iniciamos los archivos
            FilesAt.initFiles(filename, folder, df);
            // Iniciamos el parser
            p = new Parser(ts);
        }
        
        private static void finish(){
            try {
            // Dupms Ast
            FilesAt.FParser.write(ast);
            // Dumps TS
            //ts.OutTS();
        } catch (IOException | NullPointerException e) {
            e.getStackTrace();
        } finally {
            // Resets the standard error output
            FilesAt.closeFiles();
            System.setErr(System.err);
            checkingErrors();
        }
    }

    private static void checkingErrors() {
        ArrayList<String> ok = new ArrayList<>(Tables.getAnalyzers().values());
        for (Integer c : errors) {
            int analyzer = ErrorAt.getAnalizer(c);
            String a = Tables.getAnalyzers().get(analyzer);
            if (ok.indexOf(a) != -1) {
                Pretty.printRed("Hay errores en el " + a);
                ok.remove(ok.indexOf(a));
            }
        }
        for (String good : ok) {
            if (good != "UserInput") {
                Pretty.printOK(good + " completado correctamente");
            }
        }
    }
}
