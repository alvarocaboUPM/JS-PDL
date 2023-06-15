package com.pdl.symbols;

import com.pdl.common.interfaces.TS;

public class TSController {
    TS table;

    public TSController(){
        table = new SymbolTable();
    }

    public TSController(TS table) {
        this.table = table;
    }

    
}
