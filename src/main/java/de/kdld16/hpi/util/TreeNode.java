package de.kdld16.hpi.util;

import org.apache.commons.collections.ArrayStack;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jonathan on 05.01.17.
 */
public class TreeNode {
    private List<TreeNode> children = new ArrayList<TreeNode>();
    private TreeNode parent = null;
    private String key = null;
    private ArrayList<String> value = null;

    public TreeNode(String key) {
        this.key = key;
    }

    public TreeNode(String key, ArrayList<String>value, TreeNode parent) {
        this.key = key;
        this.value= value;
        this.parent = parent;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public void addChild(String data) {
        TreeNode child = new TreeNode(data);
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(TreeNode child) {
        child.setParent(this);
        this.children.add(child);
    }

    public String getKey() {
        return this.key;
    }
    public ArrayList<String>getValue() {
        return this.value;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public void setValue(ArrayList<String>value) {
        this.value = value;
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        if(this.children.size() == 0)
            return true;
        else
            return false;
    }

    public void removeParent() {
        this.parent = null;
    }

    public ArrayList<TreeNode> getLeafNodes() {
        ArrayList<TreeNode> out = new ArrayList<>();
        for (TreeNode node : this.getChildren()) {
            if (node.isLeaf()) {
                out.add(node);
            } else {
                out.addAll(node.getLeafNodes());
            }
        }
        return out;
    }
}
