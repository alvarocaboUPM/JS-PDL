package com.pdl.common.TS.implementation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.pdl.common.TS.TS;
import com.pdl.lexer.ALex;
import com.pdl.lexer.lib.SymbolAt;
import com.pdl.lexer.lib.Token;
import com.pdl.old_sintax.ASin;

/**
 * Instancia un Symbol Table Manager que cuenta con una estructura
 * definida por sus {@link SymbolAt}, tabla
 * global y array de tablas locales
 */
public class TS_imp implements TS {
    Map<Integer, SymbolAt> globalT = new HashMap<>();
    Map<String, Map<Integer, SymbolAt>> localT = new HashMap<>();
    Map<Integer, SymbolAt> curLocal;
    private boolean Global = true, FoInLoc = false;

    static int index = 0, nLocales = 0;

    @Override
    public void createTS(String tableName) {
        // index = 0;
        nLocales++;
        localT.put(new String(tableName), curLocal = new HashMap<Integer, SymbolAt>());
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

    @Override
    public Token insertAt(String ID) {
        // Check if already present in the current scope
        SymbolAt tmp;
        tmp = lookAt(ID);

        if (Global && tmp != null) {
            // if ((ASin.inVarDec || ASin.inFunc)&&!ASin.inAss) ALex.ezError(202, ID);
            return ALex.nToken("ID", tmp.getID());
        } else if (ASin.inFunc && tmp != null && FoInLoc) {
            // if ((ASin.inVarDec || ASin.inParms)&&!ASin.inAss) ALex.ezError(202, ID);
            return ALex.nToken("ID", tmp.getID());
        } else if (ASin.inParms && ASin.TabLex.equals(ID))
            ALex.ezError(202, ID);
        else if (ASin.TabLex != null && ASin.TabLex.equals(ID))
            return ALex.nToken("ID", tmp.getID());
        else if (tmp != null && !ASin.inVarDec)
            return ALex.nToken("ID", tmp.getID());

        // Insert in the needed scope
        if (Global)
            globalT.put(index, new SymbolAt(ID, index));
        else
            curLocal.put(index, new SymbolAt(ID, index));

        // Generating token
        return ALex.nToken("ID", index++);
    }

    @Override
    public SymbolAt lookAt(String ID) {
        if (!Global) {
            for (SymbolAt s : curLocal.values()) {
                if (s.getLexema().equals(ID)) {
                    FoInLoc = true;
                    return s;
                }
            }
        }
        for (SymbolAt s : globalT.values()) {
            if (s.getLexema().equals(ID)) {
                FoInLoc = false;
                return s;
            }
        }
        return null;
    }

    @Override
    public SymbolAt lookAtIndex(int index) {
        SymbolAt tmp;
        if (!Global && (tmp = curLocal.get(index)) != null) {
            FoInLoc = true;
            return tmp;
        } else if ((tmp = globalT.get(index)) != null) {
            FoInLoc = false;
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

}
