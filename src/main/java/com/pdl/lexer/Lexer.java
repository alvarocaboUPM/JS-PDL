package com.pdl.lexer;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import com.pdl.lexer.lib.*;
import com.pdl.Compiler;
import com.pdl.common.*;
import com.pdl.common.interfaces.ALex;
import com.pdl.common.utils.Constants;
import com.pdl.common.utils.Pretty;
import com.pdl.common.utils.Tables;

public class Lexer implements ALex{

    private static int Pointer; // Reading buffer pointer
    public static int numLineas; // Number of lines in the Compiler.Source file
    // Token-Generating tracking variables
    private static String lex;
    private static Integer num;
    // Data Structures
    public List<Token> tokenList ; // Keeps track of the tokens generated


    public Lexer() {
        Pointer = 0;
        numLineas = 1;
        tokenList = new ArrayList<Token>();
    }


    public Token nxToken() throws IOException {
        Token tk;
        tk = Gen_Token(leer());
        AppendToken(tk);
        return tk;
    }


    public List<Token> getTokens() {
        return this.tokenList;
    }

    

    /**
     * Acts as a main function for the lexer
     * 
     * @implNote Only for debug*
     * 
     * @return TokenList List of tokens generated
     * @throws IOException
     */
    protected List<Token> LexerDebug() throws IOException {

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
    private static Token nToken(String Type, Object Info) {
        return new Token(Type, Info, numLineas);
    }

    /**
     * Reads 1 byte and seeks the pointer 1 position
     * 
     * @return char
     * @throws IOException
     */
    private static char leer() {
        byte aux = -1;
        try {
            aux = Pointer >= Compiler.Source.length ? -1 : Compiler.Source[Pointer];
        } catch (NullPointerException e) {

        }
        // Reads only 1 char
        char out = aux != -1 ? (char) aux : Constants.EOF; // Returns char or EOF
        Pointer++;
        return out;

    }

    /**
     * Casts a char variable to String
     * 
     * @param car
     * @return String
     */
    private static String carString(char car) {
        return "" + car;
    }

    @SuppressWarnings("unused")
    /**
     * Peeks next char without increasing the pointer
     * 
     * @return next char
     */
    private static char peek() {
        if (Pointer > Compiler.Source.length)
            return 0;
        if (Pointer == Compiler.Source.length)
            return Constants.EOF;
        return (char) Compiler.Source[Pointer];
    }

    /**
     * Skips cars util it finds the end of commnent or EOF
     * 
     * @throws IOException
     */
    private static void skipComment() {
        char c;
        while ((c = leer()) != '*' && leer() != '/') {
            if (c == Constants.EOF) {
                ErrorAt.ezError(17, null);
                return;
            }
        }

        Pointer += 2; // Skips '*/'
    }

    /**
     * @return validToken | null in case of Error
     */
    private static Token Gen_Token(char car) throws IOException {
        lex = null;
        Token res = null;

        // Checks for EOF
        if (car == Constants.EOF || Pointer > Compiler.Source.length) {
            return new Token("Teof", null);
        }

        // Some direct cases
        switch (car) {
            // Coments
            case '/':
                if ((car = leer()) != '*')
                    ErrorAt.ezError(13, "'/'" + car);
                skipComment();

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
                if (num > Constants.EOF)
                    ErrorAt.ezError(11, null);
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
            // Si no es una palabra reservada, mete el símbolo en la tabla de símbolos
            return Tables.getResWords().containsKey(lex) ? Tables.getResWords().get(lex)
                    : Compiler.ts.insertAt(lex);

        } else {
            // Checks for direct token
            if (Tables.getDirToken().containsKey(carString(car))) {
                res = Tables.getDirToken().get(carString(car));

                switch (res.getType()) {
                    case "AsValue":
                }

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
                break;

            default:
                /* No valid tokens nor error generated */
                ErrorAt.ezError(14, lex);
                break;
        }

        return null;
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
        Compiler.FTokens.write(tk.toString());
    }

    

   
}