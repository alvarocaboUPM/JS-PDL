package com.pdl.old_sintax.expresion;

import java.util.Objects;

import es.upm.aedlib.Pair;
import com.pdl.lexer.ALex;
import com.pdl.lexer.lib.Token;
import com.pdl.old_sintax.ASin;
import com.pdl.common.ErrorAt;
import com.pdl.common.TS.TS;
import com.pdl.common.utils.Tables;
import com.pdl.Compiler;

/**
 * Instanciates a 2 operands expression for the parser
 */
public class Expresion extends Token {
    public static enum Insertion {
        LEFT, RIGHT, OP, ERROR;
    }

    private static class Operandos extends Pair<Tipado, Tipado> {
        public Operandos(Tipado arg0, Tipado arg1) {
            super(arg0, arg1);
        }

        public boolean validate() {
            if (this.getLeft() != this.getRight()) {
                return false;
            }
            return true;
        }

    }

    private Token left, right, op;
    private boolean parOpen;
    private Tipado t;
    private Insertion last;

    /**
     * Default constructor, cast token to expression
     * 
     * @param tk
     */
    public Expresion(Token tk) {
        super(tk.getType(), tk.getInfo(), tk.getLineAt());
        this.parOpen = false;
        this.t = Tipado.DEFAULT;
        last = null;
    }

    public Expresion(Token left, Token right, Token op, Tipado t) {
        super("Expresion", null);
        this.left = left;
        this.right = right;
        this.op = op;
        this.parOpen = false;
        this.t = t;
    }

    /**
     * Default constructor, instanciates an Expresion token
     */
    public Expresion() {
        super("Expresion", null);

        this.parOpen = false;
        this.t = Tipado.DEFAULT;
    }

    /**
     * Duplicates expresion
     */
    public Expresion(Expresion e) {
        super(e.Type, e.Info);
        this.left = e.left;
        this.right = e.right;
        this.op = e.op;
        this.parOpen = e.parOpen;
        this.t = e.getTipado();
    }

    /**
     * Handles inserting the tk in the correct
     * place
     * 
     * @param tk -> is going to be casted to expression
     * @throws Error 220 if non of the field are free
     * @return Position that has been inserted
     */
    private Insertion insertAux(Token tk) {
        if (tk.isOperator()) {
            if (this.op != null)
                return Insertion.ERROR;
            setOp(tk);
            return Insertion.OP;
        } else if (op != null && right == null) {
            // OP y LEFT ya están llenos
            setRight(tk);
            return Insertion.RIGHT;
        } else if (this.left == null) {
            setLeft(tk);
            return Insertion.LEFT;
        } else {
            if (!ASin.inFCall)
                ezError(220);
            return Insertion.ERROR;
        }
    }

    public Insertion insert(Token tk) {
        this.last = insertAux(tk);
        return last;
    }

    public void setLastNull() {
        switch (last) {
            case LEFT:
                setLeft(null);
                break;

            case RIGHT:
                setRight(null);
                break;

            default:
                break;
        }
    }

    public boolean validar(TS t) {
        Operandos aux = checkOpTypes(t);
        switch (this.t) {
            case INT:
                if (!this.op.getType().equals("MOD") ||
                        !aux.validate() && aux.getLeft() != Tipado.INT) {
                    this.ezError(230);
                    return false;
                }
                return true;
            case BOOL:
                if (!this.op.getType().equals("GT") ||
                        !aux.validate() && aux.getLeft() != Tipado.INT) {
                    this.ezError(230);
                    return false;
                }

                if (!this.op.getType().equals("AND") ||
                        !aux.validate() && aux.getLeft() != Tipado.BOOL) {
                    this.ezError(230);
                    return false;
                }

                return true;
            default:
                ezError(233);
                return false;
        }
    }

    private Operandos checkOpTypes(TS t) {
        return new Operandos(left.getTipado(t), right.getTipado(t));
    }

    public boolean isExpresion() {
        return this.getType().equals("Expresion");
    }

    public boolean isParOpen() {
        return this.parOpen;
    }

    public void setParOpen() {
        this.parOpen = true;
    }

    /**
     * Checks for an Empty new expression
     * 
     * @return true if all fields are null
     */
    public boolean isEmpty() {
        return this.left == null &&
                this.right == null &&
                this.op == null;
    }

    /**
     * Checks for an Empty new expression
     * 
     * @return true if all fields are null
     */
    public boolean isComplete() {
        return this.left != null &&
                this.right != null &&
                this.op != null;
    }

    /**
     * Empties all the fields in a expression
     * 
     * @return the cleared Expresion
     */
    public Expresion clear() {
        Expresion aux = new Expresion(this.left, this.right, this.op, this.t);
        this.left = null;
        this.right = null;
        this.op = null;
        this.t = Tipado.DEFAULT;
        return aux;
    }

    /**
     * Returs the side that has no expression already
     */
    public Insertion getFree() {
        if (this.left == null)
            return Insertion.LEFT;
        if (this.right == null)
            return Insertion.RIGHT;
        return null;
    }

    public Tipado getTipado() {
        return this.t;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Expresion)) {
            return false;
        }
        Expresion expresion = (Expresion) o;
        return Objects.equals(left, expresion.left) && Objects.equals(right, expresion.right)
                && Objects.equals(op, expresion.op);
    }

    @Override
    public String toString() {
        String left = (getLeft() != null) ? getLeft().toString() : "";
        String right = (getRight() != null) ? getRight().toString() : "";
        String op = (getOp() != null) ? getOp().toString() : "";
        String tipo = (getTipo() != null) ? getTipo().toString() : "";
        return String.format("{left=%s, right=%s, op=%s, tipo=%s}", left, right, op, tipo);
    }

    // =======GETTERS && SETTERS=============

    public Token getLeft() {
        return this.left;
    }

    public void setLeft(Token left) {
        this.left = left;
    }

    public Token getRight() {
        return this.right;
    }

    public void setRight(Token right) {
        this.right = right;
    }

    public Token getOp() {
        return this.op;
    }

    public void setOp(Token op) {
        this.op = op;
    }

    public Expresion left(Token left) {
        setLeft(left);
        return this;
    }

    public Expresion right(Token right) {
        setRight(right);
        return this;
    }

    public Expresion op(Token op) {
        setOp(op);
        return this;
    }

    public Tipado getTipo() {
        return this.t;
    }

    public void setTipo(Tipado t) {
        this.t = t;
    }

    void ezError(int c) {
        Compiler.errors.add(c);
        new ErrorAt(c, ALex.numLineas).toss(Tables.getErrorHandler(),
                "\n" + this.toString());
    }
}
