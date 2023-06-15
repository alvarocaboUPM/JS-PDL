package com.pdl.common.TS;

import com.pdl.common.TS.implementation.TS_imp;

public class TSController {
    TS table;

    public TSController(){
        table = new TS_imp();
    }

    public TSController(TS table) {
        this.table = table;
    }

    
}
