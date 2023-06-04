package com.pdl.sintax;

import java.util.Comparator;

import com.pdl.lexer.ALex;
import com.pdl.lexer.lib.*;
import com.pdl.lexer.lib.Token.Tipado;
import com.pdl.common.*;
import com.pdl.common.utils.Tables;
import com.pdl.Compiler;
import com.pdl.sintax.expresion.ExpNode;
import com.pdl.sintax.expresion.Expresion;
import com.pdl.sintax.expresion.Expresion.Insertion;

public class ParseLib {
    /**
     * Handles error and dumps current parsing trace
     * 
     * @param c Error code
     */
    static void ezError(int c) {
        Compiler.errors.add(c);
        new ErrorAt(c, ALex.numLineas).toss(Tables.getErrorHandler(),
                new String("\n- TRAZA -> " + ASin.trace +
                        "\n- ÚLTIMO TK LEIDO -> " + ASin.tk.toString()));
    }
    public static void ezError(int c, String extraInfo) {
        new ErrorAt(c, ALex.numLineas).toss(Tables.getErrorHandler(),
                extraInfo
        );
    }
    /**
     * Sets id to the read token info field and handles
     * small sintax analisys errors
     *
     * @return true if ID has been set | false if tk type is not ID
     */
    static boolean setID() {
        if (ASin.tk.getType() != "ID") {
            ParseLib.ezError(105);
            return false;
        }
        // Setting ID
        SymbolAt target;
        if ((target = Compiler.ts.lookAtIndex((int) ASin.tk.getInfo())) == null) {
            int code = Compiler.ts.getScope() ? 211 : 212;
            ParseLib.ezError(code);
        } else
            ASin.id = target;

        return true;
    }

    /**
     * Handles entering a lower scope
     */
    static void toLowerSc() {
        // inFunc=false;
        Compiler.ts.createTS(ASin.TabLex);
        Compiler.ts.changeScope(false);

    }

    static void CheckExplicitness() {
        if (ASin.id.getType().equals("Unknown")) {
            ASin.id.setType("TypeInt");
            ASin.id.setOffset(++ASin.OffsetG);
            if (ASin.inFunc)
                Compiler.ts.setGlobal(ASin.id);
        }
    }

    static void IncOffset(String Type) {
        switch (Type) {
            case "TypeInt":
            case "TypeBool": {
                if (ASin.inFunc)
                    ASin.OffsetL++;
                else
                    ASin.OffsetG++;
                break;
            }
            case "TypeString": {
                if (ASin.inFunc)
                    ASin.OffsetL += 64;
                else
                    ASin.OffsetG += 64;
                break;
            }
        }
    }

    static void endTree(){
        ASin.cursor = new ExpNode();
        ASin.expresions = ASin.expresions.destroyTree(ASin.cursor);
    }

    static void insertOperand() {
        if(ASin.tk.isOperator())
            return;
        if (ASin.e.insert(ASin.tk) == Insertion.ERROR)
            return;
        // Comprobamos que ya está formada
        if (ASin.e.isComplete() && ASin.e.getTipado()!= Tipado.DEFAULT) {
            ASin.cursor.addChild(new ExpNode(new Expresion(ASin.e)));
            ASin.e.setLeft(ASin.e.clear());
        }
    }

    /**
     * Comparator instance for operator prefference
     */
    static class OperatorPreference implements Comparator<Expresion> {

        @Override
        public int compare(Expresion arg0, Expresion arg1) {
            // Comparing operators

            return 0;
        }

    }
}
