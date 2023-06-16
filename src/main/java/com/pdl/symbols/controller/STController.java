package com.pdl.symbols.controller;

import com.pdl.symbols.model.SymbolTable;

public class STController {
    SymbolTable ts;

    public STController(){
        ts = new SymbolTable();
    }

    public SymbolTable getTable(){
        return this.ts;
    }
}
