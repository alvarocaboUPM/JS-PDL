package com.pdl.common.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

import com.pdl.common.ErrorAt;


public class FilesAt {

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

    public static void initFiles(String filename, String folder, String df) {
        String input = Constants.TEST_FOLDER + folder + filename;
        try {
            Source = Files.readAllBytes(new File(input).toPath());
            // Directorio outfiles
            File outdir = new File(Constants.OUTPUT + folder+filename);

            if (!outdir.exists()) {
                outdir.mkdirs();
            }

            FSource = new FileWriter(new File(outdir + "/Source.txt"));

            FSource.write(new String(Source, "US-ASCII"));

            FTokens = new FileWriter(new File(outdir + "/Tokens.txt"));

            FTS = new FileWriter(new File(outdir + "/TS.txt"));

            FParser = new FileWriter(new File(outdir + "/Parser.txt"));

            FErr = new PrintStream(new File(outdir + "/Errors.txt"));

            System.setErr(FErr);

        } catch (IOException e) {
            ErrorAt.ezError(1, filename + "\nRuta de archivos: " + input);
        } catch (NullPointerException nException) {
            ErrorAt.ezError(2, null);
            nException.printStackTrace();
        }
    }

    public static void closeFiles(){
        try {
            FilesAt.FSource.close();
            FilesAt.FParser.close();
            FilesAt.FTokens.close();
            FilesAt.FTS.close();
            FilesAt.FErr.close();
        } catch (IOException e) {
            System.err.println("Could not close the files");
            e.printStackTrace();
        }
    }
}
