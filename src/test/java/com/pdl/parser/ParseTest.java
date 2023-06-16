package com.pdl.parser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

import com.pdl.common.interfaces.TS;
import com.pdl.common.utils.Constants;
import com.pdl.symbols.SymbolTable;

public class ParseTest {
    static Parser p;
    static TS t;
    static String ok, testFile;
    

    @BeforeAll
    public static void init(){
        t = new SymbolTable();
        p = new Parser(t);
    }

    @BeforeEach
    public void resetTestfile(){
        testFile = Constants.TEST_FOLDER;
    }

    @Test
    public void testFuncDec(){
        
    }

    @Test
    public void basicProgramTest(){
        testFile += "t1.js";
        ok = "D	 1 10 11 52 13 1 10 11 52 13 1 10 11 54 13 2 14 18 19 38 45 56 2 14 18 19 38 44 48 1 10 11 54 13 2 14 18 19 38 44 48 40 38 44 48 3 26 38 44 48 28 29 14 18 19 38 45 56 2 14 18 19 38 44 48 42 38 44 48 2 15 24 38 44 48 2 15 24 38 44 48 4 ";
        assertEquals(ok, p.parserDebug(testFile));
    }

}
