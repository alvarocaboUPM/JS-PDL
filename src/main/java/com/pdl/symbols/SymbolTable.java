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

    // Flagsets
    private boolean Global;

    // Counters
    int index, nLocales;

    public SymbolTable() {
        // Initial state
        globalT = new HashMap<>();
        localT = new HashMap<>();
        Global = true;
        index = 0;
        nLocales = 0;
    }

    @Override
    public void createTS(String tableName) {
        nLocales++;
        localT.put(new String(tableName), curLocal = new HashMap<Integer, SymbolAt>());
    }

    @Override
    public Integer insertAt(String ID) {
        // Check if already present in the current scope
        SymbolAt tmp;
        tmp = lookAt(ID);

        //Case 1: Global and already declared
        if (Global && tmp != null) {
            return tmp.getID();
        }
        // if (ASin.inFunc && tmp != null && FoInLoc) {
        //     return tmp.getID();
        // }  if (ASin.inParms && ASin.TabLex.equals(ID))
        //     new ErrorAt(202, ALex.numLineas).toss(Tables.getErrorHandler(), ID);
        // else if (ASin.TabLex != null && ASin.TabLex.equals(ID))
        //     return tmp.getID();
        // else if (tmp != null && !ASin.inVarDec)
        //     return tmp.getID();

        // // Insert in the needed scope
        // if (Global)
        //     globalT.put(index, new SymbolAt(ID, index));
        // else
        //     curLocal.put(index, new SymbolAt(ID, index));

        // Generating token
        return index++;
    }

    @Override
    public SymbolAt lookAt(String ID) {
        if (!Global) {
            for (SymbolAt s : curLocal.values()) {
                if (s.getLexema().equals(ID)) {
                    //FoInLoc = true;
                    return s;
                }
            }
        }
        for (SymbolAt s : globalT.values()) {
            if (s.getLexema().equals(ID)) {
                //FoInLoc = false;
                return s;
            }
        }
        return null;
    }

    @Override
    public SymbolAt lookAtIndex(int index) {
        SymbolAt tmp;
        if (!Global && (tmp = curLocal.get(index)) != null) {
            //FoInLoc = true;
            return tmp;
        } else if ((tmp = globalT.get(index)) != null) {
            //FoInLoc = false;
            return tmp;
        }
        return null;
    }

    public void setGlobal(SymbolAt tmp) {

        int id = tmp.getID();
        // tmp.setPosition(index);
        globalT.put(id, tmp);
        // index++;
        rmID(id);
    }

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
