package com.pdl.sintax.expresion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.pdl.lexer.lib.Token;

/**
 * Instancia de un node del árbol que implementa el algoritmo de expresiones
 * @version 0.1
 * @author Álvaro Cabo
 */
public class ExpNode {
    private Expresion value;
    private ExpNode parent;
    private List<ExpNode> children;

    public ExpNode(Expresion value) {
        this.value = value;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public ExpNode() {
        this.value = null;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public static class NodeComparator implements Comparator<ExpNode>{
        @Override
        public int compare(ExpNode arg0, ExpNode arg1) {
            //1. Orden de operaciones
            Token op1, op2;
            op1 = arg0.getValue().getOp();
            op2 = arg1.getValue().getOp();

            if (op1.getType() == "MOD" && op2.getType() != "MOD") {
                return -1;
            } else if (op1.getType() != "MOD" && op2.getType() == "MOD") {
                return 1;
            } else if (op1.getType() == "GT" && op2.getType() == "AND") {
                return -1;
            } else if (op1.getType() == "AND" && op2.getType() == "GT") {
                return 1;
            }

            //2. De izquierda a derecha
            return 1;
        } 
    }
     /**
     * Sorts the current children set and returns the first element
     * @return the valid node for the path
     */
    public ExpNode getValidChild(){
        return sortChildren().get(0);
    }

    /*
     * Sorts the current Children set using the operator preference 
     */
    private List<ExpNode> sortChildren(){
        Collections.sort(this.getChildren(), new NodeComparator());
        return this.getChildren();
    }

    public boolean isLeaf(){
        return this.children.isEmpty();
    }

    /**
     * El último añadido es en el que se trabaja
     * @return last child of the set
     */
    public ExpNode getWorkingChild(){
        return children.get(children.size()-1);
    }

   

    public Expresion getValue() {
        return value;
    }

    public void setValue(Expresion value) {
        this.value = value;
    }

    public ExpNode getParent() {
        return parent;
    }

    public void setParent(ExpNode parent) {
        this.parent = parent;
    }

    public List<ExpNode> getChildren() {
        return children;
    }

    public boolean isRoot(){
        return this.value.getType().equals("Root");
    }

    public ExpNode addChild(ExpNode child) {
        this.children.add(child);
        child.setParent(this);
        return child;
    }
}
