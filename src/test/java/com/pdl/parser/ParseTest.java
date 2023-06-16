package com.pdl.parser;
import org.junit.jupiter.api.*;

import com.pdl.common.interfaces.TS;
import com.pdl.symbols.SymbolTable;

public class ParseTest {
    Parser p;
    TS t;
    

    @BeforeAll
    public void init(){
        t = new SymbolTable();
        p = new Parser(t);
    }

    @Test
    public void testFuncDec(){
        
    }

}
