package com.pdl.lexer;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import com.pdl.lexer.lib.*;
import com.pdl.Compiler;
import com.pdl.common.*;

public class ALex {

    // ---------GLOBAL VARIABLES-------------
    static final char EOF = '\u001a';
    static final int STR_MAX_SIZE = 64;
    static final int MAX_INT = 32767;

    private static int Pointer; // Reading buffer pointer
    public static int numLineas; // Number of lines in the Compiler.Source file
    // Token-Generating tracking variables
    private static String Lexema;
    private static Integer num;
    // Data Structures
    public List<Token> TokenList = new ArrayList<Token>(); // Keeps track of the tokens generated
    /*
     * ______ _ _
     * | ___| | | (_)
     * | |_ _ _ _ __ ___ | |_ _ ___ _ __ ___
     * | _|| | | || '_ \ / __|| __|| | / _ \ | '_ \ / __|
     * | | | |_| || | | || (__ | |_ | || (_) || | | |\__ \
     * \_| \__,_||_| |_| \___| \__||_| \___/ |_| |_||___/
     * 
     */

    /**
     * Reads 1 byte and seeks the pointer 1 position
     * 
     * @return char
     * @throws IOException
     */
    private static char leer(){
        byte aux=-1;
        try {
            aux = Pointer >= Compiler.Source.length ? -1 : Compiler.Source[Pointer]; 
        } catch (NullPointerException e) {
            
        }
        // Reads only 1 char
        char out = aux != -1 ? (char) aux : EOF; // Returns char or EOF
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
     * @return next char
     */
    private static char peek(){
        if(Pointer> Compiler.Source.length)
            return 0;
        if(Pointer== Compiler.Source.length)
            return EOF;
        return (char)Compiler.Source[Pointer];
    }

    /**
     * Skips cars util it finds the end of commnent or EOF
     * 
     * @throws IOException
     */
    private static void skipComment(){
        char c;
        while ((c = leer()) != '*' && leer()!='/') {
            if (c == EOF) {
                ezError(17, null);
                return ;
            }
        }
        
        Pointer += 2; // Skips '*/'
    }

    /**
     * @return validToken | null in case of Error
     */
    private static Token Gen_Token(char car) throws IOException {
        Lexema = null;
        Token res = null;

        // Checks for EOF
        if (car == EOF || Pointer > Compiler.Source.length) {
            return new Token("Teof", null);
        }

        //Some direct cases
        switch (car) {
            // Coments
            case '/':
                if ((car = leer()) != '*')
                    ezError(13, "'/'" + car);
                skipComment();

            //Skippable cases
            case '\n':
                numLineas++;
            case '\r':
            case  '\t':
            case ' ':
                return Gen_Token(leer());
            
            //Strings
            case '"':
                Lexema = carString(car);
                while ((car = leer()) != '"') {
                    Lexema += carString(car);
                    // Checks for maxsize
                    if (Lexema.length() > STR_MAX_SIZE) {
                        ezError(12, null);
                    }
                }
                res = nToken("Cad", Lexema + "\"");
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
                if (num > MAX_INT)
                    ezError(11, null);
            }
            res = nToken("CteInt", num);
            num=null;
            Pointer--;
            return res;
        }

        // IDs and Reserved words
        if ((Character.isAlphabetic(car) || car == '_')) {
            Lexema = carString(car);
            while (Character.isAlphabetic(car = leer()) || Character.isDigit(car) || (car == '_')) {
                Lexema += carString(car);
            }
            Pointer--;
            // Si no es una palabra reservada, mete el símbolo en la tabla de símbolos
            return Compiler.t.getResWords().containsKey(Lexema) ? Compiler.t.getResWords().get(Lexema)
                    : Compiler.ts.insertAt(Lexema);

        } else {
            // Checks for direct token
            if (Compiler.t.getDirToken().containsKey(carString(car))){
                res= Compiler.t.getDirToken().get(carString(car));

                switch(res.getType()){
                    case "AsValue":
                }

                return res;
            }
                
            // Special case -> ++ &&
            if (car == '+') {
                if (leer() == '+')
                    return nToken("ResAutoSum", null);
                else
                    ezError(22, Lexema);
            }
            if (car == '&'){
                if (leer() == '&')
                    return nToken("AND", null);
                else
                    ezError(22, Lexema);
            }
            
        }

        // ERROR HANDLING
        switch (car) {
            case '\'':
                ezError(20, null);
                break;

            default:
                /* No valid tokens nor error generated */
                ezError(14, Lexema);
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
        if(tk == null){
            return;
        }
        if (!Compiler.t.getValidTokens().contains(tk.getType())) {
            ezError(13, tk.getType());
            return;
        }
          this.TokenList.add(tk);
        Compiler.FTokens.write(tk.toString());
    }


    /* Métodos objeto */

    /**
     * Class constructor that initializes variables, orientated
     * to generated a new Token iterator for the Parser
     */
    public ALex() {
        Pointer = 0;
        numLineas = 1;
    }

    /**
     * Acts as a it.getNext() function in a live TokenList,
     * mixes private funcs Gen_token and AppendToken()
     * @return validated token 
     * @throws IOException
     */
    public Token nexToken() throws IOException {
        Token tk;
        tk = Gen_Token(leer());
        AppendToken(tk);
        return tk;
    }

    /**
     * Acts as a main function for the lexer
     * @IMP: No llamar porque resetea el puntero y el num lineas (variables estáticas)
     * 
     * @return TokenList List of tokens generated
     * @throws IOException
     */
    public List<Token> Lexer() throws IOException {

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
            return TokenList;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 
     * @return lexer token list
     */
    public List<Token> getTokens(){
        return this.TokenList;
    }

    /* Métodos de libería */

    /**
     * Easy constructor for tokens
     * 
     * @return new {@link Token} with lineAt <- numLineas
     */
    public static Token nToken(String Type, Object Info) {
        return new Token(Type, Info, numLineas);
    }

    /**
     * Handles errors with Error table
     * 
     * @param c         Error code
     * @param extraInfo Optional information to append at the end of the mesg
     */
    public static void ezError(int c, String extraInfo) {
        Compiler.errors.add(c);
        // String info = new String()extraInfo!=null?extraInfo:""+
        // "\n>LEXEMA -> " + Lexema!=null?Lexema:" " +
        // "\n>Last char read -> " + c +
        // "\n>NUM -> " + num!=null?""+num:" ";

        new ErrorAt(c, numLineas).toss(Compiler.t.getErrorHandler(), 
        extraInfo
        );
    }

}