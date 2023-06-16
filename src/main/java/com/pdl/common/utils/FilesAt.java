package com.pdl.common.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

import com.pdl.lexer.ALex;

public class FilesAt {

    // Paths
    private static final String home = new File("").getAbsolutePath();
    private static final String test = home + "/src/test/resources/";
    // Path de los archivos de output
    public final static String output = test + "outfiles/";

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

    public static void initFiles(String filename, String folder, String df){
        String input = test + folder + filename;
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
}
