package com.pdl.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import com.pdl.Compiler;
import com.pdl.common.interfaces.TS;
import com.pdl.common.utils.Constants;
import com.pdl.symbols.SymbolTable;

public class ParseTest {
    static Parser p;
    static TS t;
    static String ok, testFile;

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
        ok = "D\t 1 10 11 52 13 1 10 11 53 13 1 5 50 52 6 52 8 54 9 9 35 11 54 13 36 29 14 18 19 38 45 56 36 29 14 18 19 38 45 55 36 30 31 38 44 48 37 1 5 50 52 6 52 8 52 9 9 36 29 14 18 19 38 45 56 36 29 14 18 19 38 44 48 42 38 44 47 20 38 45 56 22 38 44 48 23 36 30 31 38 45 56 37 2 15 2 14 18 19 38 45 56 4 ";
        String res = p.parserDebug(testFile);
        assertTrue(Compiler.errors.isEmpty());
        assertEquals(ok, res);
    }

    @Test
    public void basicProgramTest() {
        testFile += "t1.js";
        ok = "D	 1 10 11 52 13 1 10 11 52 13 1 10 11 54 13 2 14 18 19 38 45 56 2 14 18 19 38 44 48 1 10 11 54 13 2 14 18 19 38 44 48 40 38 44 48 3 26 38 44 48 28 29 14 18 19 38 45 56 2 14 18 19 38 44 48 42 38 44 48 2 15 24 38 44 48 2 15 24 38 44 48 4 ";
        String res = p.parserDebug(testFile);
        assertTrue(Compiler.errors.isEmpty());
        assertEquals(ok, res);
    }

    @Test
    public void longIfExpressionTest() {
        testFile += "t2.js";
        ok = "D\t 1 10 11 52 13 1 10 11 54 13 1 10 11 52 13 2 15 2 15 24 38 44 48 2 15 2 15 24 38 44 48 42 38 44 48 2 14 18 19 38 44 48 40 38 44 48 3 26 38 44 48 28 29 14 18 19 38 44 48 42 38 45 56 42 38 44 48 42 38 45 56 42 38 46 38 45 56 42 38 44 48 42 38 45 56 40 38 45 56 4 ";
        String res = p.parserDebug(testFile);
        assertTrue(Compiler.errors.isEmpty());
        assertEquals(ok, res);
    }

    @Test
    public void funcDecCallTest() {
        testFile += "t3.js";
        ok = "D\t 1 10 11 53 13 2 15 24 38 45 55 2 15 2 15 24 38 44 48 1 5 51 6 53 9 36 29 15 24 38 44 48 37 1 5 51 7 36 29 15 24 38 45 55 36 29 15 37 2 14 17 21 2 14 17 20 38 44 48 22 38 44 48 23 4 ";
        String res = p.parserDebug(testFile);
        assertTrue(Compiler.errors.isEmpty());
        assertEquals(ok, res);
    }

    @Test
    public void funcDecCallTest2() {
        testFile += "t4.js";
        ok = "D	 1 10 11 52 13 1 10 11 54 13 1 10 11 53 13 1 10 11 52 13 1 10 11 54 13 2 15 2 14 18 19 38 44 48 3 26 38 44 48 41 38 44 48 28 29 14 18 19 38 45 55 2 14 18 19 38 44 48 42 38 45 56 2 15 24 38 45 56 42 38 44 48 42 38 44 48 1 5 50 54 6 54 9 36 29 14 18 19 38 45 56 34 26 38 44 48 28 29 14 18 19 38 44 47 20 38 44 48 22 38 44 48 23 36 30 31 38 44 48 37 3 26 38 44 47 20 38 44 48 23 28 29 15 24 38 44 48 4 ";
        String res = p.parserDebug(testFile);
        assertTrue(Compiler.errors.isEmpty());
        assertEquals(ok, res);
    }

}
