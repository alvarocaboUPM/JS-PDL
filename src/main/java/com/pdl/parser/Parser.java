package com.pdl.parser;

import com.pdl.common.interfaces.ASin;
import com.pdl.lexer.ALex;

public class Parser implements ASin {

    private String result;
    private ALex lexer;

    public Parser() {
        result = "D\t ";
    }

    private static void getNext(){

    }

    @Override
    public void START() {
        result += "1 ";
        result += "2 ";
        result += "3 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'START'");
    }

    @Override
    public void DEC() {
        result += "5 ";
        result += "10 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'DEC'");
    }

    @Override
    public void PARM() {
        result += "6 ";
        result += "7 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'PARM'");
    }

    @Override
    public void PARMX() {
        result += "8 ";
        result += "9 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'PARMX'");
    }

    @Override
    public void DECID() {
        result += "11 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'DECID'");
    }

    @Override
    public void DECLX() {
        result += "12 ";
        result += "13 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'DECLX'");
    }

    @Override
    public void SEN() {
        result += "14 ";
        result += "15 ";
        result += "16 ";

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'SEN'");
    }

    @Override
    public void ASCALL() {
        result += "17 ";
        result += "18 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ASCALL'");
    }

    @Override
    public void ASIGN() {
        result += "19 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ASIGN'");
    }

    @Override
    public void FCALL() {
        result += "20 ";
        result += "21 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'FCALL'");
    }

    @Override
    public void FCALLX() {
        result += "22 ";
        result += "23 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'FCALLX'");
    }

    @Override
    public void IO() {
        result += "24 ";
        result += "25 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'IO'");
    }

    @Override
    public void SENCOM() {
        result += "26 ";
        result += "33 ";

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'SENCOM'");
    }

    @Override
    public void IFX() {
        result += "27 ";
        result += "28 ";

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'IFX'");
    }

    @Override
    public void SENB() {
        result += "29 ";
        result += "30 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'SENB'");
    }

    @Override
    public void RX() {
        result += "31 ";
        result += "32 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'RX'");
    }

    @Override
    public void BODY() {
        result += "34 ";
        result += "35 ";
        result += "36 ";
        result += "37 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'BODY'");
    }

    @Override
    public void EXP() {
        result += "38 ";
        result += "39 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'EXP'");
    }
    
    @Override
    public void EXPX() {
        result += "40 ";
        result += "41 ";
        result += "42 ";
        result += "43 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'EXPX'");
    }
    
    @Override
    public void VALUE() {
        result += "44 ";
        result += "45 ";
        result += "46 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'VALUE'");
    }
    
    @Override
    public void XPX() {
        result += "47 ";
        result += "48 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'XPX'");
    }
    
    @Override
    public void INC() {
        result += "49 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'INC'");
    }
    
    @Override
    public void TX() {
        result += "50 ";
        result += "51 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'TX'");
    }
    
    @Override
    public void T() {
        result += "52 ";
        result += "53 ";
        result += "54 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'T'");
    }
    
    @Override
    public void CTE() {
        result += "55 ";
        result += "56 ";
        result += "57 ";
        result += "58 ";
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'CTE'");
    }

}
