package com.pdl.parser;

import java.io.IOException;

import com.pdl.common.ErrorAt;
import com.pdl.common.interfaces.ASin;
import com.pdl.common.interfaces.TS;
import com.pdl.common.utils.Constants;
import com.pdl.lexer.Lexer;
import com.pdl.lexer.lib.Token;

public class Parser implements ASin {

    private String result;
    private Lexer lexer;
    private static Token tk;

    public Parser(TS t) {
        result = "D\t ";
        lexer = new Lexer(t);
    }

    public String getResult() {
        return this.result;
    }

    private void getNext() {
        try {
            tk = lexer.nxToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String START() {
        getNext();
        switch (tk.getType()) {
            // 1. Declaraciones
            case Constants.function:
            case Constants.let:
                result += "1 ";
                DEC();
                return START();

            // 2. Sentencias simples
            case Constants.id:
            case Constants.print:
            case Constants.input:
            case Constants.increment:
                result += "2 ";
                SEN();
                return START();

            // 3. Sentencias complejas
            case Constants.ifKw:
            case Constants.doKw:
                result += "3 ";
                SENCOM();
                return START();

            case Constants.eof:
                result += "4 ";
                break;

            default:
                if (tk.isType()) {
                    ErrorAt.ezError(213, debugString());
                    break;
                } else {
                    ErrorAt.ezError(100, debugString());
                    break;
                }
        }

        return result;
    }

    @Override
    public void DEC() {
        if (checkTk(Constants.function)) {
            result += "5 ";
            getNext();
            ckID();
            TX();

            getNext();
            ckParOp();
            PARM();
            getNext();
            ckParCl();

            getNext();
            ckKeyOp();
            BODY();
            getNext();
            ckKeyCl();

            return;
        }

        if (checkTk(Constants.let)) {
            result += "10 ";
            DECID();
            return;
        }
    }

    @Override
    public void PARM() {
        getNext();
        if (!tk.isType()) {
            result += "7 ";
            return;
        }
        result += "6 ";

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
        getNext();
        ckID();
        T();
        DECLX();
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
        if (!tk.isType()) {
            result += "51 ";
            return;
        }

        result += "50 ";
        T();
    }

    @Override
    public void T() {
        switch (tk.getType()) {
            case Constants.intType:
                result += "52 ";
                break;
            case Constants.stringType:
                result += "53 ";
                break;
            case Constants.booleanType:
                result += "54 ";
                break;
            default:
                ckType();
        }
    }

    @Override
    public void CTE() {
        switch (tk.getType()) {
            case Constants.cad:
                result += "55 ";
                break;
            case Constants.num:
                result += "56 ";
                break;
            case Constants.trueKw:
                result += "57 ";
                break;
            case Constants.falseKw:
                result += "57 ";
                break;
            default:
                ckCte();
        }

    }

    private String debugString() {
        return new String("\n- TRAZA -> " + result +
                "\n- ÚLTIMO TK LEIDO -> " + tk.toString());
    }

    private boolean checkTk(String cmp) {
        return tk.getType().equals(cmp);
    }

    private void ckID() {
        if (!checkTk(Constants.id))
            ErrorAt.ezError(105, debugString());
    }

    private void ckParOp() {
        if (!checkTk(Constants.parenthesesOpen))
            ErrorAt.ezError(103, debugString());
    }

    private void ckKeyOp() {
        if (!checkTk(Constants.curlyBraceOpen))
            ErrorAt.ezError(104, debugString());
    }

    private void ckParCl() {
        if (!checkTk(Constants.parenthesesClose))
            ErrorAt.ezError(113, debugString());
    }

    private void ckKeyCl() {
        if (!checkTk(Constants.curlyBraceClose))
            ErrorAt.ezError(114, debugString());
    }

    private void ckPrint() {
        if (!checkTk(Constants.print))
            ErrorAt.ezError(109, debugString());
    }

    private void ckCte() {
        if (!checkTk(Constants.cad)
                && !checkTk(Constants.num)
                && !checkTk(Constants.falseKw)
                && !checkTk(Constants.trueKw))
            ErrorAt.ezError(111, debugString());
    }

    private void ckType() {
        if (!checkTk(Constants.stringType)
                && !checkTk(Constants.booleanType)
                && !checkTk(Constants.intType))
            ErrorAt.ezError(106, debugString());
    }

}
