package com.pdl.lexer.lib;

import com.pdl.common.utils.Constants;

public class Token {
    protected String Type;
    protected Object Info;
    int lineAt;

    /**
     * Default Token constructor
     * 
     * @param Type
     * @param Info
     */
    public Token(String Type, Object Info) {
        this.Type = Type;
        this.Info = Info;
        this.lineAt = 0;
    }

    /**
     * Creates a new {@link Token} adding the line
     * in the source file where it was found
     * 
     * @param Type
     * @param Info
     * @param lineAt
     */
    public Token(String Type, Object Info, int lineAt) {
        this.Type = Type;
        this.Info = Info;
        this.lineAt = lineAt;
    }

    /**
     * Checks if the token is a typing
     * 
     * @return true | false
     */
    public boolean isType() {
        return Type.equals(Constants.stringType)
                || Type.equals(Constants.intType)
                || Type.equals(Constants.booleanType);

    }

    /**
     * Checks if the token is an operand
     * 
     * @return true | false
     */
    public boolean isOperator() {
        return Type.equals(Constants.MOD)
                || Type.equals(Constants.AND)
                || Type.equals(Constants.GT);
    }

    /**
     * Checks if the token is a typing
     * 
     * @return true | false
     */
    public boolean isCTE() {
        return Type.equals(Constants.cad)
                || Type.equals(Constants.num)
                || Type.equals(Constants.falseKw)
                || Type.equals(Constants.trueKw);
    }

    public String toString() {
        return new String("<" + Type + "," + (Info == null ? "" : Info) + ">\n");
    }

    public String getType() {
        return this.Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public Object getInfo() {
        return this.Info;
    }

    public void setInfo(Object Info) {
        this.Info = Info;
    }

    public int getLineAt() {
        return this.lineAt;
    }

    public void setLineAt(int lineAt) {
        this.lineAt = lineAt;
    }

}