package com.pdl;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pdl.common.*;
import com.pdl.common.interfaces.TS;
import com.pdl.symbols.SymbolTable;
import com.pdl.common.utils.Pretty;
import com.pdl.common.utils.Tables;
//Project modules
import com.pdl.lexer.*;
import com.pdl.old_sintax.*;

/**
 * Frontend class of the JS-PDL compiler
 * 
 * @author Álvaro Cabo / Usema El-Hatifi
 * @version 1.0
 */
public class Compiler {
    // Paths
    private static final String home = new File("").getAbsolutePath();
    private static final String test = home + "/src/test/resources/";
    // Path de los archivos de output
    public final static String output = test + "outfiles/";

    // Lista de errores para el input final
    public static List<Integer> errors;

    public static TS ts;

    // Byte[] que guarda el fichero fuente
    public static byte[] Source;

    // Fichero código fuente
    public static FileWriter FSource;

    // Fichero tokens
    public static FileWriter FTokens;

    // Fichero Tabla de Símbolos
    public static FileWriter FTS;

    // Fichero Parser
    public static FileWriter FParser;

    // Fichero Errores
    public static PrintStream FErr;

    static String filename, folder, df;

    public static void main(String args[]) {
        askInput();
        init();
        ASin.Parser();
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
                df = "examen2.js";
                break;
        }

        System.out.print("Nombre del archivo a analizar [por defecto " + df + "]: ");

        filename = sc.nextLine();
        if (filename.isBlank())
            filename = df;
        sc.close();
    }

    private static void init() {

        String input = test + folder + filename;

        // Iniciamos tablas
        ts = new SymbolTable();
        errors = new ArrayList<>();

        try {
            Source = Files.readAllBytes(new File(input).toPath());
            // Directorio outfiles
            File outdir = new File(output + filename);

            if (!outdir.exists()) {
                outdir.mkdirs();
            }

            FSource = new FileWriter(new File(outdir + "/Source.txt"));

            FSource.append(new String(Source, "US-ASCII"));

            FTokens = new FileWriter(new File(outdir + "/Tokens.txt"));

            FTS = new FileWriter(new File(outdir + "/TS.txt"));

            FParser = new FileWriter(new File(outdir + "/Parser.txt"));

            FErr = new PrintStream(new File(outdir + "/Errors.txt"));

            System.setErr(FErr);

        } catch (IOException e) {
            ALex.ezError(1, filename + "\nRuta de archivos: " + input);
        } catch (NullPointerException nException) {
            ALex.ezError(2, null);
            nException.printStackTrace();
        }
    }

    private static void finish() {
        try {
            // Dumps TS
            ts.OutTS();
            // Resets the standard error output
            Compiler.FSource.close();
            Compiler.FParser.close();
            Compiler.FTokens.close();
            Compiler.FTS.close();
            Compiler.FErr.close();
        } catch (IOException | NullPointerException e) {
            e.getStackTrace();
        } finally {
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
