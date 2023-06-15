package com.pdl.common;

import java.util.*;
import com.pdl.Compiler;

import com.pdl.common.utils.Pretty;
import com.pdl.common.utils.Tables;
import com.pdl.lexer.ALex;

/**
 * Instanciates an error with code and message
 */
public class ErrorAt {

    int code, n_line;

    public ErrorAt(int c, int line) {
        code = c;
        n_line = line;
        Compiler.errors.add(c);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getN_line() {
        return this.n_line;
    }

    public void setN_line(int n_line) {
        this.n_line = n_line;
    }

    /**
     * Gets the return message from an error handler data-structure
     * 
     * @param handler
     * @return String | null if no code found
     */
    public String getMessage(Map<Integer, String> handler) {
        try {
            return handler.get(this.code);
        } catch (NullPointerException e) {
            Pretty.printErr("BAD ERROR CODE: " + this.code);
            return null;
        }
    }

    /**
     * @return Analyzer that produces the error
     */
    private String getAnalizer() {
        if (this.code < 10)
            return "Main";
        else if (this.code < 100)
            return "Lexical Analyzer";
        else if (this.code < 200)
            return "Sintactic Analyzer";
        else if (this.code < 300)
            return "Semantic Analyzer";
        else
            return "INVALID CODE";
    }

    /**
     * Library function that gets the translates error code to analizer
     * 
     * @param c Valid error code
     * @return [0: Main, 1: Lexer, 2: Parser, 3: Semantic, -1: Invalid]
     */
    public static int getAnalizer(int c) {
        if (c < 10)
            return 0;
        else if (c < 100)
            return 1;
        else if (c < 200)
            return 2;
        else if (c < 300)
            return 3;
        else
            return -1;
    }

    /**
     * Prints error message to Stderr
     * 
     * @param handler
     * @param extraInfo
     */
    public void toss(Map<Integer, String> handler, Object extraInfo) {

        String out = "Error #" + code + " @ line " + n_line + ": " + getMessage(handler);

        if (extraInfo != null)
            out += extraInfo;

        System.err.println("ERROR FOUND USING THE " + getAnalizer().toUpperCase() + "\n" +
                "###################\n" + out + "\n");
    }

     /**
     * Handles errors with Error table
     * 
     * @param c         Error code
     * @param extraInfo Optional information to append at the end of the mesg
     */
    public static void ezError(int c, String extraInfo) {
        new ErrorAt(c, ALex.numLineas).toss(Tables.getErrorHandler(),
                extraInfo);
    }
}
