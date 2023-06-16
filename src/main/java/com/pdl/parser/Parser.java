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
            callTX();

            getNext();
            ckParOp();
            PARM();

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
        if (checkTk(Constants.parenthesesClose)) {
            result += "7 ";
            return;
        }
        result += "6 ";
        T(); // Should already be at type token
        getNext();
        ckID();
        PARMX();
    }

    @Override
    public void PARMX() {
        getNext();
        if (checkTk(Constants.comma)) {
            result += "8 ";
            callT();
            getNext();
            ckID();
            PARMX();
        }
        if (checkTk(Constants.parenthesesClose)) {
            result += "9 ";
            return;
        } else
            ErrorAt.ezError(113, debugString());

    }

    @Override
    public void DECID() {
        result += "11 ";
        getNext();
        ckID();
        callT();
        DECLX();
    }

    @Override
    public void DECLX() {
        getNext();
        if (checkTk(Constants.equals)) {
            result += "12 ";
            ASIGN();
        }
        if (checkTk(Constants.semicolon)) {
            result += "13 ";
        } else
            ErrorAt.ezError(107, debugString());
    }

    @Override
    public void SEN() {
        switch (tk.getType()) {
            case Constants.id:
                result += "14 ";
                ASCALL();
                break;
            case Constants.print:
            case Constants.input:
                result += "15 ";
                IO();
                break;
            case Constants.increment:
                result += "16 ";
                INC();
                break;
        }
        getNext();
        ckSemCol();
    }

    @Override
    public void ASCALL() {
        getNext();
        if (checkTk(Constants.parenthesesOpen)) {
            result += "17 ";
            FCALL();
            return;
        }
        if (checkTk(Constants.equals)) {
            result += "18 ";
            ASIGN();
        }
    }

    @Override
    public void ASIGN() {
        // Cursor sobre el =
        result += "19 ";
        callEXP();
    }

    @Override
    public void FCALL() {
        getNext();
        if (checkTk(Constants.parenthesesClose)) {
            result += "21 ";
            return;
        }
        result += "20 ";
        EXP(); // Ya sobre la expresion
        FCALLX();
    }

    @Override
    public void FCALLX() {
        getNext();
        if (checkTk(Constants.comma)) {
            result += "22 ";
            callEXP();
            FCALLX();
        }
        if (checkTk(Constants.parenthesesClose)) {
            result += "23 ";
            return;
        } else
            ErrorAt.ezError(113, debugString());
    }

    @Override
    public void IO() {
        if (checkTk(Constants.print)) {
            result += "24 ";
            callEXP();
            return;
        }
        if (checkTk(Constants.input)) {
            getNext();
            ckID();

        } else {
            ErrorAt.ezError(109, debugString());
        }
    }

    @Override
    public void SENCOM() {
        if (checkTk(Constants.ifKw)) {
            result += "26 ";
            getNext();
            ckParOp();
            callEXP();

            getNext();
            ckParCl();

            IFX();
            return;
        }
        if (checkTk(Constants.doKw)) {
            result += "33 ";
            getNext();
            ckKeyOp();
            BODY();

            getNext();
            ckKeyCl();

            getNext();
            ckWhile();
            getNext();
            ckParOp();
            callEXP();

            getNext();
            ckParCl();

            getNext();
            ckSemCol();
        }

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
        if (checkTk(Constants.id)) {
            result += "44 ";
            XPX();
            return;
        }
        // TODO: pasar esto a clase token
        if (checkTk(Constants.cad)
                || checkTk(Constants.num)
                || checkTk(Constants.falseKw)
                || checkTk(Constants.trueKw)){

            result += "45 ";
            callCTE();
            return ;
                }
        if (checkTk(Constants.parenthesesOpen)) {

            result += "46 ";
        }
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

    private void ckSemCol() {
        if (!checkTk(Constants.semicolon))
            ErrorAt.ezError(107, debugString());
    }

    private void ckWhile() {
        if (!checkTk(Constants.whileKw))
            ErrorAt.ezError(118, debugString());
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

    private void callTX() {
        getNext();
        TX();
    }

    private void callT() {
        getNext();
        T();
    }

    private void callEXP() {
        getNext();
        EXP();
    }

    private void callCTE() {
        getNext();
        CTE();
    }

}
