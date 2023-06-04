package com.pdl.common;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.pdl.lexer.lib.*;
import com.pdl.Compiler;

/**
 * Methods required by a basic lexer-orientated Symbol Table
 */
public interface TS {
    HashMap<Integer, SymbolAt> globalT = new HashMap<>();
    HashMap<String, HashMap<Integer,SymbolAt>> localT = new HashMap<>();
    FileWriter file = Compiler.FTS;

    /**
     * Creates new local TS in the array, after freeing the current one
     */
    public void createTS(String tableName);
    
    /**
     * Dumps the current state of the tables to the TS file
     * @throws IOException
     */
    public void OutTS() throws IOException; 

    /**
     * Allocates a new symbol in the needed scope
     * @param ID Read by the tokenizer
     * @return Token generated
     * @throws LexicalError
     */
    public Token insertAt(String ID);

    /**
     * Searchs for a symbol in any of the avalible tables
     * @param ID 
     * @return SymbolAt found | null if nothing found
     */
    public SymbolAt lookAt(String ID);

    /**
     * LookAt method implemented for known indexes at the current scope
     * @param index
     * @return Symbol at index | null if not found
     */
    public SymbolAt lookAtIndex(int index);

}
