package com.pdl.common.interfaces;

import java.io.IOException;
import java.util.List;

import com.pdl.lexer.lib.Token;

public interface ALex {
    
    /**
     * Acts as a it.getNext() function in a live TokenList,
     * mixes private funcs Gen_token and AppendToken()
     * 
     * @return Valid Token
     * @throws IOException
     */
    public Token nxToken() throws IOException;

    /**
     * 
     * @return Tokens list
     */
    public List<Token> getTokens();

}
