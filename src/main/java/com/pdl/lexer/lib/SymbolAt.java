package com.pdl.lexer.lib;

import java.util.ArrayList;
import java.util.List;

public class SymbolAt extends Object {
    private String Lexema;
    private int ID; // Index in the table
    private String type; // Tipo
    private int NumParams; // numParam
    private List<String> TypesParams;
    private String ReturnType; // TipoRetorno
    private int offset;

    /**
     * Default values constructor
     */
    public SymbolAt(String Lexema, int index) {
        this.Lexema = Lexema;
        this.ID = index;
        this.type = "Unknown";
        this.NumParams = -1;
        this.TypesParams = new ArrayList<>();
        this.ReturnType = "Unknown";
        this.offset = -1;
    }

    public SymbolAt(String ID, int i, String type, int NumParams,
            List<String> TypesParams, String ReturnType, int offset) {
        this.Lexema = ID;
        this.ID = i++;
        this.type = type;
        this.NumParams = NumParams;
        this.TypesParams = TypesParams;
        this.ReturnType = ReturnType;
        this.offset = offset;
    }

    /**
     * Looks up itself in the Symbol Table and triggers type setters
     * to check for semantic errors
     * 
     * @return true if ok | false otherwise
     */
    /*
     * public boolean checkType() {
     * SymbolAt aux;
     * if ((aux = Compiler.ts.lookAt(ID)) != null
     * && this.type.equals(aux.getType())) {
     * return true;
     * }
     * return false;
     * }
     * 
     * 
     */
    @Override
    public String toString() {
        if (getType().equals("Function")) {
            return "* Lexema:'" + getLexema() + "'\n" +
                    "   Atributos:\n" +
                    // "| ID='" + getID() + "'" +
                    "   + type:'" + getType() + "'\n" +
                    "   + NumParams:" + getNumParams() + "\n" +
                    "   + TypesParams:'" + getTypesParams() + "'\n" +
                    "   + ReturnType:'" + getReturnType() + "'\n" +
                    "   + offset:" + getOffset() + "\n" +
                    "\n";
        } else {
            return "* Lexema:'" + getLexema() + "'\n" +
                    "   Atributos:\n" +
                    // "| ID='" + getID() + "'" +
                    "   + type:'" + getType() + "'\n" +
                    "   + offset:" + getOffset() + "\n" +
                    "\n";
        }
    }

    public String getLexema() {
        return this.Lexema;
    }

    public int getID() {
        return this.ID;
    }

    public void setPosition(int Position) {
        this.ID = Position;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumParams() {
        return this.NumParams;
    }

    public void setNumParams(int NumParams) {
        this.NumParams = NumParams;
    }

    public List<String> getTypesParams() {
        return this.TypesParams;
    }

    /**
     * Sets an already initialized list as params
     * 
     * @param TypesParams
     */
    public void setTypesParams(List<String> TypesParams) {
        this.TypesParams = TypesParams;
    }

    /**
     * Checks if it has been inititialized and adds a new param type at the end of
     * the list
     * 
     * @param param String
     */
    public void addTypesParams(String param) {
        if (this.TypesParams != null)
            this.TypesParams.add(param);
        else {
            this.TypesParams = new ArrayList<>();
            this.TypesParams.add(param);
        }
    }

    public String getReturnType() {
        return this.ReturnType;
    }

    public void setReturnType(String ReturnType) {
        this.ReturnType = ReturnType;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}