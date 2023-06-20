package com.pdl.common.interfaces;

import java.io.IOException;


import com.pdl.lexer.lib.SymbolAt;


/**
 * Methods required by a basic lexer-orientated Symbol Table
 */
public interface TS {

    /**
     * Creates new local TS in the array, after freeing the current one
     */
    public void createTS(String tableName);

    /**
     * Allocates a new symbol in the needed scope
     * 
     * @param ID Read by the tokenizer
     * @return Token generated
     * @throws LexicalError
     */
    public Integer insertAt(String ID);

    /**
     * Searchs for a symbol in any of the avalible tables
     * 
     * @param ID
     * @return SymbolAt found | null if nothing found
     */
    public SymbolAt lookAt(String ID);

    /**
     * LookAt method implemented for known indexes at the current scope
     * 
     * @param index
     * @return Symbol at index | null if not found
     */
    public SymbolAt lookAtIndex(int index);

    public boolean getScope();

    /**
     * Changes current TS scope
     * 
     * @param scope True -> Global | False -> Local
     */
    public void changeScope(boolean scope);

    public void setGlobal(SymbolAt tmp);

    /**
     * Dumps the current state of the tables to the TS file
     * 
     * @throws IOException
     */
    public void OutTS() throws IOException;

}
