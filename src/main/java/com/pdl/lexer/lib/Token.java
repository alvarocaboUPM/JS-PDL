package com.pdl.lexer.lib;

import com.pdl.common.TS;
import com.pdl.common.utils.Tables;

public class Token{
    protected String Type;
    protected Object Info;
    int lineAt;
    
    public static enum Tipado{
        INT, BOOL, STRING, DEFAULT
    }

    /**
     * Default Token constructor
     * @param Type
     * @param Info
     */
    public Token(String Type, Object Info){
        this.Type = Type;
        this.Info = Info;
        this.lineAt = 0; 
    }
    
    /**
     * Creates a new {@link Token} adding the line
     * in the source file where it was found
     * @param Type
     * @param Info
     * @param lineAt
     */
    public Token(String Type, Object Info, int lineAt){
        this.Type = Type;
        this.Info = Info;
        this.lineAt = lineAt;
    }
    
    /**
     * Checks if the token is a typing 
     * @return true | false
     */
    public boolean isType(){
        int[] tipados = {0,5,9};
        int index = Tables.getValidTokens().indexOf(this.Type);
        for (int i : tipados) {
            if(index == i) return true;
        }
        return false;
    }

    /**
     * Tries to find the typing of the token
     * @return Tipado | Default if no valid type found
     */
    public Tipado getTipado(TS t){
        if(this.Type.equals("CteInt")) 
            return Tipado.INT;
        if(this.Type.equals("Cad")) 
            return Tipado.STRING;
        if(this.Type.equals("TokF")||this.Type.equals("TokT")) 
            return Tipado.BOOL;
        if(this.Type.equals("ID")){
            SymbolAt aux = t.lookAtIndex((int)Info);
            switch (aux.getType()) {
                case "TypeInt":
                    return Tipado.INT;
                case "TypeString":
                    return Tipado.STRING;
                case "TypeBool":
                    return Tipado.BOOL;
                default:
                    break;
            }
        }
        return Tipado.DEFAULT;
        
    }

    /**
     * Checks if the token is an operand 
     * @return true | false
     */
    public boolean isOperator(){
        int[] operands = {22,23,24};
        int index = Tables.getValidTokens().indexOf(this.Type);
        for (int i : operands) {
            if(index == i) return true;
        }
        return false;
    }
    
    public String toString(){
        return new String("<" + Type + "," + (Info == null ?  "" : Info) + ">\n");
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