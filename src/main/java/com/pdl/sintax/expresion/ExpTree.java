package com.pdl.sintax.expresion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pdl.lexer.lib.*;
import com.pdl.sintax.expresion.Expresion.Insertion;

public class ExpTree {
    private ExpNode root;

    public ExpTree(ExpNode root) {
        Expresion data = new Expresion(new Token("Root", null));
        root.setValue(data);
        this.root = root;
    }

    public boolean isEmpty() {
        return root.getChildren().isEmpty();
    }

    public ExpTree destroyTree(ExpNode cursor) {
        System.out.println(this.toString());
        return new ExpTree(cursor);
    }

    public ExpNode getRoot() {
        return root;
    }

    /**
     * Casts Expression to a new {@link ExpNode}
     * 
     * @param e
     * @return
     */
    public ExpNode crearNode(Expresion e) {
        return new ExpNode(e);
    }

    public void insertChild(ExpNode parent, ExpNode child) {
        parent.addChild(child);
    }

    public void createSibling(ExpNode node, Expresion data) {
        ExpNode sibling = crearNode(data);
        insertChild(node.getParent(), sibling);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toGraphicTree(root, sb, "");
        // toJSON(root, sb, 0);
        return sb.toString();
    }

    @SuppressWarnings("all")
    private void toGraphicTree(ExpNode node, StringBuilder sb, String prefix) {
        if (node == null) {
            return;
        }
        if (node.isRoot()) {
            sb.append("ROOT\n");
        } else {
            if (!node.getValue().isComplete()) {
                try {
                    if (node.getValue().getFree() != Insertion.LEFT) {
                        sb.append(prefix + "└ " + node.getValue().getLeft().getType());
                        sb.append(" " + node.getValue().getOp().getType() + "\n");
                    }else{
                        sb.append(prefix + "└ " + node.getValue().getOp().getType());
                        sb.append(" " + node.getValue().getRight().getType());
                    }
                } 
                
                catch (NullPointerException e) {
                    sb.append(prefix+ "Error en la creación del arbol");
                }
            }
            else{               
                sb.append(prefix + "└ EXP_" + node.getValue().getTipado() + "\n");
                }       
         
        }
        for (int i = 0; i < node.getChildren().size(); i++) {
            ExpNode child = node.getChildren().get(i);
            if (i == node.getChildren().size() - 1) {
                toGraphicTree(child, sb, prefix + "  ");
            } else {
                toGraphicTree(child, sb, prefix + "│ ");
            }
        }
    }

    @SuppressWarnings("all")
    private void toJSON(ExpNode node, StringBuilder sb, int level) {
        if (node == null) {
            return;
        }
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level; i++) {
            indent.append("\t");
        }
        sb.append(indent + "{\n");
        if (node.isRoot()) {
            sb.append(indent + "  \"value\": \"" + "ROOT" + "\",\n");
        } else {
            sb.append(indent + "  \"value\": \"" + node.getValue().toString() + "\",\n");
        }
        sb.append(indent + "  \"children\": [\n");
        for (ExpNode child : node.getChildren()) {
            toJSON(child, sb, level + 1);
            sb.append(",\n");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("\n" + indent + "  ]\n");
        sb.append(indent + "}");
    }

    /**
     * Creates the correct path for the expression using the comparator
     * 
     * @return valid path
     */
    public List<List<ExpNode>> findValidPath() {
        List<ExpNode> path = new ArrayList<>();
        List<List<ExpNode>> validPaths = new ArrayList<>();
        //path.add(root);
        findValidPath(root, path, validPaths);
        return validPaths;
    }
    
    
    private void findValidPath(ExpNode node, List<ExpNode> path, List<List<ExpNode>> validPaths) {
        if (node.isLeaf()) {
            Collections.reverse(path);
            validPaths.add(new ArrayList<>(path));
        } else {
            for (ExpNode child : node.getChildren()) {
                path.add(child);
                findValidPath(child, path, validPaths);
                path.remove(path.size() - 1);
            }
        }
    }
    
}
