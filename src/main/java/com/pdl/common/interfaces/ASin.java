package com.pdl.common.interfaces;

public interface ASin {
    /**
     * Allows declarations, sentences, and complex sentences
     * [1-3]
     * @return final trace
     */
    String START();

    void SENA();
    void CTE();
    void INC();
    void EXP();
    void EXPX();
    void VALUE();
    void XPX();
    void ASIGN();
    void DECL();
    void DECLX();
    void TD();
    void TDX();
    void INOUT();
    void WILE();
    void SENB();
    void BODY();

    void FCALL();
    void FCALLX();
    void RX();
    void IDX();
    void IFAX();
    void FUN();
    void PARAM();
    void PARAMX();
}
