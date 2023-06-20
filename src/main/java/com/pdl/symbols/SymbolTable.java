package com.pdl.symbols;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.pdl.common.interfaces.TS;
import com.pdl.common.utils.FilesAt;
import com.pdl.lexer.lib.SymbolAt;


/**
 * Instancia un Symbol Table Manager que cuenta con una estructura
 * definida por sus {@link SymbolAt}, implementada con HashMaps
 */
public class SymbolTable implements TS {
    FileWriter file = FilesAt.FTS;
    Map<Integer, SymbolAt> globalT; // Global table
    Map<String, Map<Integer, SymbolAt>> localT; // Map of local tables
     Map<Integer, SymbolAt> curLocal; // Current local table
    public String CurrentLTSName;
    // Flagsets
    private boolean Global, function, inParams, inVarDeclaration; //flags para saber el scope global, funcion; Shadowing es un flag que controla declaraciones en distintos ambitos de un mismo id


    // Counters
    int indexL, indexG, nLocales;

    public SymbolTable() {
        // Initial state
        globalT = new HashMap<>();
        localT = new HashMap<>();
        Global = true;
        function = inParams = inVarDeclaration = false;
        indexL = -1; indexG = 1; nLocales = 0;
        CurrentLTSName = null;
    }

    @Override
    public void createTS(String tableName) {
        indexL = -1;
        CurrentLTSName = tableName;
        nLocales++;
        localT.put(new String(tableName), curLocal = new HashMap<Integer, SymbolAt>());
    }

    @Override
    public Integer insertAt(String ID) {
        SymbolAt tmp;

        // Check in the current scope only
        if (Global) {
            for (SymbolAt s : globalT.values()) {
                if (s.getLexema().equals(ID)) {
                    tmp = s;
                    return tmp.getID();
                }
            }
            // If not found, insert
            globalT.put(indexG, new SymbolAt(ID, indexG));
            return indexG++;
        } else {
            for (SymbolAt s : curLocal.values()) {
                if (s.getLexema().equals(ID)) {
                    tmp = s;
                    return tmp.getID();
                }
            }
            if(!inParams && !inVarDeclaration){
                // If not found, insert
                globalT.put(indexG, new SymbolAt(ID, indexG));
                return indexG++;
            }
            // If not found, insert
            localT.get(CurrentLTSName).put(indexL, new SymbolAt(ID, indexL));
            return indexL--;
        }
    }


    @Override
    public SymbolAt lookAt(String ID) {
        if (!Global) {
            for (SymbolAt s : curLocal.values()) {
                if (s.getLexema().equals(ID)) {
                    return s;
                }
            }
        }

        for (SymbolAt s : globalT.values()) {
            if (s.getLexema().equals(ID)) {
                return s;
            }
        }

        return null;
    }


    @Override
    public SymbolAt lookAtIndex(int index) {
        SymbolAt tmp;
        if (!Global && (tmp = curLocal.get(index)) != null) {
            return tmp;
        } else if ((tmp = globalT.get(index)) != null){// && !shadowing) {
            return tmp;
        }
        return null;
    }

    public void setGlobal(SymbolAt tmp) {// ???

        int id = tmp.getID();
        // tmp.setPosition(index);
        globalT.put(indexG++, tmp);
        indexL++;
        rmID(id);
    }

    public void setLocal(boolean state) {
        this.Global = !state;
    }
    public void setCurLocal() {

    }
    //----
    public void functionOn() {
        this.function = true;
    }
    public void functionOff() {
        this.function = false;
    }
    public boolean functionState() {
        return function;
    }

    public void setInParams(boolean state) { //REMOVE
        this.inParams = state;
    }
    public boolean isInParams() { ////REMOVE
        return inParams;
    }

    public void setInVarDeclaration(boolean state) {
        this.inVarDeclaration = state;
    }
    public boolean isInVarDeclaration() {
        return inVarDeclaration;
    }


    //----

    public void rmID(int index) {
        if (!Global)
            curLocal.remove(index);

    }

    /**
     * Changes current TS scope
     *
     * @param scope True -> Global | False -> Local
     */
    public void changeScope(boolean scope) {
        this.Global = scope;
    }

    /**
     * Changes current TS scope
     *
     * True -> Global | False -> Local
     */
    public String getScopeToString() {
        return this.Global ? "Global" : "Local";
    }

    /**
     * Scope getter
     */
    public boolean getScope() {
        return this.Global;
    }
    public Map<Integer, SymbolAt> getCurrentLocalTs() {
        return this.curLocal;
    }

    @Override
    public void OutTS() throws IOException {
        file.write("TABLA PRINCIPAL #1:\n");
        for (SymbolAt s : globalT.values()) {
            file.write("--------- ----------\n");
            file.write(s.toString());

        }
        int numTab = 2;
        for (Entry<String, Map<Integer, SymbolAt>> tmp : localT.entrySet()) {
            file.write("--------- ----------\n");
            file.write("\nTABLA DE LA FUNCION " + tmp.getKey() + " #" + numTab++ + ":\n");
            for (SymbolAt s : tmp.getValue().values()) {
                file.write("--------- ----------\n");
                file.write(s.toString());

            }
            file.write("--------- ----------\n");
        }
        // Liberamos los objetos
        localT.clear();
        globalT.clear();
    }

}
