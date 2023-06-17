package com.pdl.common.interfaces;

public interface ASin {
    /**
     * Allows declarations, sentences and complex sentences
     * [1-3]
     * @return final trace
     */
    String START();

    /**
     * Allows function and variables declarations
     * [5,10]
     */
    void DEC();

    /**
     * Assigns the parameters in a function declaration
     * [6,7] + PARAMX[8,9]
     */
    void PARM();
    void PARMX();

    /**
     * Allows ID declaration with/out assignation
     * 11 +[12,13]
     */
    void DECID();
    void DECLX();

    /*
     * Simple sentences
     * [14-16]
     */
    void SEN();

    /**
     * Parses var assignation and func calls
     * [17,18]
     */
    void ASCALL();

    /**
     * Parses var assignation
     * 19
     */
    void ASIGN();
    
    /**
     * Parses function calls
     * [20,21] + ([22,23]) 
     */
    void FCALL();
    void FCALLX();

    /**
     * Parses print and input operations
     * [24,25]
     */
    void IO();

    /**
     * Parses complex sentences (if and do-while)
     * [26,33]
     */
    void SENCOM();

    /**
     * [27,28]
     */
    void IFX();

    /**
     * SEN + return statement
     * [29, 30]
     */
    void SENB();
    //[31,32]
    void RX();

    /**
     * [34-37]
     */
    void BODY();
    /**
     * Expresions: Value or Inc
     * [38,39]
     */
    void EXP();
    //[40-43]
    void EXPX();
    //[44,45,46]
    void VALUE();
    /**
     * Handles possible func calls
     * [47,48]
     */
    void XPX();
    //49
    void INC();
    //[50,51]
    void TX();
    /**
     * Types
     * [52,53,54]
    */
    void T();
    //[55,56,57,58]
    void CTE();
}
