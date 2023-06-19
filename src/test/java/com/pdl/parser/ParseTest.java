package com.pdl.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import com.pdl.lexer.lib.SymbolAt;
import org.junit.jupiter.api.*;

import com.pdl.Compiler;
import com.pdl.common.interfaces.TS;
import com.pdl.common.utils.Constants;

import com.pdl.symbols.SymbolTable;

public class ParseTest {
    static Parser p;
    static TS t;
    static String ok, testFile;
    static SymbolAt tmpFunction;

    @BeforeAll
    public static void init() {
        t = new SymbolTable();
        p = new Parser(t);
    }
    
    @BeforeEach
    public void resetTestfile() {
        testFile = Constants.TEST_FOLDER;
        Compiler.errors = new ArrayList<>();
    }

    @Test
    public void testFuncDec() {
        testFile += "entrega.js";
        ok = "D\t 1 32 21 24 23 1 32 21 25 23 2 53 27 24 54 24 56 26 57 40 32 21 26 23 40 35 36 51 20 9 16 5 14 40 35 36 51 20 9 16 4 14 40 35 38 49 9 15 19 14 41 2 53 27 24 54 24 56 24 57 40 35 36 51 20 9 16 5 14 40 35 36 51 20 9 15 19 13 9 15 18 45 9 16 5 14 47 9 15 19 14 48 14 40 35 38 49 9 16 5 14 41 1 35 37 30 1 35 36 51 20 9 16 5 14 3 ";
        String res = p.parserDebug(testFile);
        assertTrue(Compiler.errors.isEmpty());
        assertEquals(ok, res);
    }

    @Test
    public void basicProgramTest() {
        testFile += "t1.js";
        ok = "D\t 1 32 21 24 23 1 32 21 24 23 1 32 21 26 23 1 35 36 51 20 9 16 5 14 1 35 36 51 20 9 15 19 14 1 32 21 26 23 1 35 36 51 20 9 15 19 11 9 15 19 14 1 31 42 9 15 19 14 44 36 51 20 9 16 5 14 1 35 36 51 20 9 15 19 13 9 15 19 14 1 35 37 29 9 15 19 14 1 35 37 29 9 15 19 14 3 ";
        String res = p.parserDebug(testFile);
        assertTrue(Compiler.errors.isEmpty());
        assertEquals(ok, res);
    }

    @Test
    public void longIfExpressionTest() {
        testFile += "t2.js";
        ok = "D\t 1 32 21 24 23 1 32 21 26 23 1 32 21 24 23 1 35 37 30 1 35 37 29 9 15 19 14 1 35 37 30 1 35 37 29 9 15 19 13 9 15 19 14 1 35 36 51 20 9 15 19 11 9 15 19 14 1 31 42 9 15 19 14 44 36 51 20 9 15 19 13 9 16 5 13 9 15 19 13 9 16 5 13 9 17 9 16 5 13 9 15 19 13 9 16 5 14 11 9 16 5 14 3 ";
        String res = p.parserDebug(testFile);
        assertTrue(Compiler.errors.isEmpty());
        assertEquals(ok, res);
    }

    @Test
    public void funcDecCallTest() {
        testFile += "t3.js";
        ok = "D\t 1 32 21 25 23 1 35 37 29 9 16 4 14 1 35 37 30 1 35 37 29 9 15 19 14 2 53 28 54 25 57 40 35 37 29 9 15 19 14 41 2 53 28 55 40 35 37 29 9 16 4 14 40 35 37 30 41 1 35 36 52 46 1 35 36 52 45 9 15 19 14 47 9 15 19 14 48 3 ";
        String res = p.parserDebug(testFile);
        assertTrue(Compiler.errors.isEmpty());
        assertEquals(ok, res);
    }

    @Test
    public void funcDecCallTest2() {
        testFile += "t4.js";
        ok = "D\t 1 32 21 24 23 1 32 21 26 23 1 32 21 25 23 1 32 21 24 23 1 32 21 26 23 1 35 37 30 1 35 36 51 20 9 15 19 14 1 31 42 9 15 19 12 9 15 19 14 44 36 51 20 9 16 4 14 1 35 36 51 20 9 15 19 13 9 16 5 14 1 35 37 29 9 16 5 13 9 15 19 13 9 15 19 14 2 53 27 26 54 26 57 40 35 36 51 20 9 16 5 14 40 31 42 9 15 19 14 44 36 51 20 9 15 18 45 9 15 19 14 47 9 15 19 14 48 14 40 35 38 49 9 15 19 14 41 1 31 42 9 15 18 45 9 15 19 14 48 14 44 37 29 9 15 19 14 3 ";
        String res = p.parserDebug(testFile);
        assertTrue(Compiler.errors.isEmpty());
        assertEquals(ok, res);
    }


    @Test
    public void loopTest1() {
        testFile += "t5.js";
        ok = "D\t 1 32 21 25 23 1 32 21 24 23 1 32 21 26 23 2 53 27 25 54 25 57 40 33 40 35 37 29 9 17 9 16 4 14 14 40 32 21 24 23 40 35 36 51 20 9 15 19 14 40 31 42 9 15 19 14 44 38 49 9 15 19 14 41 34 9 15 19 11 9 16 5 12 9 15 19 14 40 35 36 51 20 9 15 18 45 9 15 19 14 47 9 15 19 14 47 9 15 19 14 48 14 40 35 36 51 20 9 16 4 14 40 35 38 49 9 15 19 14 41 3 ";
        String res = p.parserDebug(testFile);
        assertTrue(Compiler.errors.isEmpty());
        assertEquals(ok, res);
    }

}
