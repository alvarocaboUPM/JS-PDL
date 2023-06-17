package com.pdl.lexer;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.pdl.lexer.lib.*;
import com.pdl.common.ErrorAt;
import com.pdl.common.interfaces.ALex;
import com.pdl.common.interfaces.TS;
import com.pdl.common.utils.FilesAt;
import com.pdl.common.utils.Constants;
import com.pdl.common.utils.Pretty;
import com.pdl.common.utils.Tables;

public class Lexer implements ALex {
    // Input and output files
    private byte[] source;
    private boolean onTest;
    private int Pointer; // Reading buffer pointer
    public static int numLineas; // Number of lines in the FilesAt.Source file
    // Token-Generating tracking variables
    private String lex;
    private Integer num;
    // Data Structures
    public List<Token> tokenList; // Keeps track of the tokens generated
    public TS tab;

    public Lexer(TS t) {
        Pointer = 0;
        numLineas = 1;
        tokenList = new ArrayList<Token>();
        tab = t;
        source = FilesAt.Source;
        onTest = false;
    }

    // For testing
    public Lexer(TS t, String input) {
        Pointer = 0;
        numLineas = 1;
        tokenList = new ArrayList<Token>();
        tab = t;
        try {
            source = Files.readAllBytes(new File(input).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        onTest = true;
    }

    public Token nxToken() throws IOException {
        Token tk;
        tk = Gen_Token(leer());
        AppendToken(tk);
        return tk;
    }

    /**
     * Acts as a main function for the lexer
     * 
     * @implNote Only for debug*
     * 
     * @return TokenList List of tokens generated
     * @throws IOException
     */
    protected List<Token> lexerDebug() throws IOException {

        // Variable initalization
        Token token = null;
        Pointer = 0;
        numLineas = 1;

        try {
            /* Reading loop */
            while ((token = Gen_Token(leer())) != null)
                AppendToken(token);
            // No more tokens
            Pretty.printOK("Lexer analisis completado correctamente");
            return tokenList;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Easy constructor for tokens
     * 
     * @return new {@link Token} with lineAt <- numLineas
     */
    private Token nToken(String Type, Object Info) {
        return new Token(Type, Info, numLineas);
    }

    /**
     * Acts as a it.getNext() function in a live TokenList,
     * mixes private funcs Gen_token and AppendToken()
     * 
     * @return validated token
     * @throws IOException
     */
    private char leer() {
        byte aux = -1;
        try {
            aux = Pointer >= source.length ? -1 : source[Pointer];
        } catch (NullPointerException e) {

        }
        // Reads only 1 char
        char out = aux != -1 ? (char) aux : Constants.EOF; // Returns char or EOF
        Pointer++;
        return out;

    }

    public List<Token> getTokens() {
        return this.tokenList;
    }

    /* Métodos de libería */

    private String carString(char car) {
        return "" + car;
    }

    // private char peek() {
    // if (Pointer > source.length)
    // return 0;
    // if (Pointer == source.length)
    // return Constants.EOF;
    // return (char) source[Pointer];
    // }

    /**
     * Skips cars util it finds the end of commnent or EOF
     * 
     * @throws IOException
     */
    private void skipComment() {
        char nextChar1 = leer();
        char nextChar2 = leer();

        while (nextChar1 != '*' || nextChar2 != '/') {
            if (nextChar1 == Constants.EOF) {
                ErrorAt.ezError(17, null);
                return;
            }

            nextChar1 = nextChar2;
            nextChar2 = leer();
        }

    }

    /**
     * Iterates through the source code and tokenizes it
     * 
     * @return validToken | null in case of Error
     */
    private Token Gen_Token(char car) throws IOException {
        lex = null;
        Token res = null;

        // Checks for EOF
        if (car == Constants.EOF || Pointer > source.length) {
            return new Token("Teof", null);
        }

        // Some direct cases
        switch (car) {
            // Coments
            case '/':
                if ((car = leer()) != '*'){
                    break;
                }
                skipComment();
                return Gen_Token(leer());

            // Skippable cases
            case '\n':
                numLineas++;
            case '\r':
            case '\t':
            case ' ':
                return Gen_Token(leer());

            // Strings
            case '"':
                lex = carString(car);
                while ((car = leer()) != '"') {
                    lex += carString(car);
                    // Checks for maxsize
                    if (lex.length() > Constants.STR_MAX_SIZE) {
                        ErrorAt.ezError(12, null);
                        panic();
                        break;
                    }
                }
                res = nToken("Cad", lex + "\"");
                return res;

            default:
                break;
        }

        // Numbers
        if (Character.isDigit(car)) {
            // Case 1st digit
            if (num == null)
                num = Character.getNumericValue(car);
            while (Character.isDigit(car = leer())) {
                num = (num * 10) + Character.getNumericValue(car);
            }

            if (num > Constants.MAX_INT) {
                ErrorAt.ezError(11, null);
                num = Constants.MAX_INT;
            }

            res = nToken("CteInt", num);
            num = null;
            Pointer--;
            return res;
        }

        // IDs and Reserved words
        if ((Character.isAlphabetic(car) || car == '_')) {
            lex = carString(car);
            while (Character.isAlphabetic(car = leer()) || Character.isDigit(car) || (car == '_')) {
                lex += carString(car);
            }
            Pointer--;

            //Check for unimplemented kws
            if(Tables.getUnimplementedKW().contains(lex)){
                ErrorAt.ezError(24, lex);
                panic();
                return Gen_Token(leer());
            }

            // Si no es una palabra reservada, mete el símbolo en la tabla de símbolos
            return Tables.getResWords().containsKey(lex) ? Tables.getResWords().get(lex)
                    : nToken("ID", tab.insertAt(lex));

        } else {
            // Checks for direct token
            if (Tables.getDirToken().containsKey(carString(car))) {
                res = Tables.getDirToken().get(carString(car));
                return res;
            }

            // Special case -> ++ &&
            if (car == '+') {
                if (leer() == '+')
                    return nToken("ResAutoSum", null);
                else
                    ErrorAt.ezError(22, lex);
            }
            if (car == '&') {
                if (leer() == '&')
                    return nToken("AND", null);
                else
                    ErrorAt.ezError(22, lex);
            }

        }

        // ERROR HANDLING
        switch (car) {
            case '\'':
                ErrorAt.ezError(20, null);
                return Gen_Token('"');

            case '/':
                ErrorAt.ezError(23, null);
                while(leer()!='\n'){}
                numLineas++;
                break;

            case '*':
            case '+':
            case '-':
                ErrorAt.ezError(22, carString(car));
                return nToken(Constants.MOD, null);

            case '<':
                ErrorAt.ezError(22, carString(car));
                return nToken(Constants.GT, null);

            case '|':
            case '!':
                ErrorAt.ezError(22, carString(car));
                return nToken(Constants.AND, null);

            default:
                /* No valid tokens nor error generated */
                ErrorAt.ezError(14, carString(car));
                panic();
                break;
        }

        return Gen_Token(leer());
    }

    /**
     * Allows to write the tokens to the file
     * and keep them in the tokens array while
     * they're being generated
     * 
     * @param tk Token to be written
     * @exception InvalidToken if its type is not contained in the
     *                         valid Tokens list
     */
    private void AppendToken(Token tk) throws IOException {
        // Token validation
        if (tk == null) {
            return;
        }
        if (!Tables.getValidTokens().contains(tk.getType())) {
            ErrorAt.ezError(13, tk.getType());
            return;
        }
        this.tokenList.add(tk);
        if (!onTest) {
            FilesAt.FTokens.write(tk.toString());
        }
    }

    /**
     * Skips characters until it finds a safe one
     */
    private int panic() {
        int res = 0;
        while (!isSafe(leer())) {
            res++;
        }
        System.err.println("Skipped " + res + " chars");
        return res;
    }

    private static boolean isSafe(char c) {
        char[] safe = { ';', '"', '}'};
        for (char safeChar : safe) {
            if (c == safeChar) {
                
                return true;
            }
        }
        return false;
    }
}