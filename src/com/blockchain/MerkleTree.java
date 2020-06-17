package com.blockchain;

import java.util.ArrayList;
import java.util.List;

public class MerkleTree {
    private MerkleNode root;
    private ArrayList<MerkleNode> nodes;
    private ArrayList<MerkleNode> leaves;

    public String getTreeHash(){
        return root.getHash();
    }
    public MerkleTree() {
        this.nodes = new ArrayList<>();
        this.leaves = new ArrayList<>();
    }
    public MerkleTree(ArrayList<String> dataList){

        this.nodes = new ArrayList<>();
        this.leaves = new ArrayList<>();
        for(String data : dataList){
            this.appendLeaf(data);
        }

        buildTree(nodes);
    }

    public ArrayList<MerkleNode> getLeaves() {
        return leaves;
    }
    public ArrayList<MerkleNode> getNodes() {
        return nodes;
    }
    public MerkleNode getRoot() {
        return root;
    }
    public void appendLeaf(String data) {
        this.appendLeaf(new MerkleNode(data));
    }
    public MerkleNode appendLeaf(MerkleNode node){
        nodes.add(node);
        leaves.add(node);
        return node;
    }


    public void buildTree(ArrayList<MerkleNode> nodes){

        if (nodes.size() <= 0) {
            throw new IllegalArgumentException();
        }
        else if (nodes.size() == 1) {
            this.root = nodes.get(0);
        }
        else{
            ArrayList<MerkleNode> parentNodes = new ArrayList<>();
            for(int i = 0 ; i < nodes.size() ; i+=2)
            {
                MerkleNode rightNode = (i + 1 < nodes.size()) ? nodes.get(i + 1) : null;
                MerkleNode parentNode = new MerkleNode(nodes.get(i),rightNode);
                parentNodes.add(parentNode);
            }
            buildTree(parentNodes); //Recursive;
        }

    }
    public void ShowTreeStruct(){
        root.ShowStruct();
    }
}
