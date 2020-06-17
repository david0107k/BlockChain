package com.blockchain;

public class MerkleNode {
    private MerkleNode leftNode;
    private MerkleNode rightNode;
    private MerkleNode parent;
    private String hash;
    MerkleNode(MerkleNode leftNode, MerkleNode rightNode){
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        if(leftNode != null ){
            if(rightNode!= null){
                this.hash = StringUtil.applySha256(this.leftNode.hash + this.rightNode.hash);
            }
            else{
                this.hash = StringUtil.applySha256(this.leftNode.hash);
            }
        }
        else {
            hash = "";
        }
    }
    MerkleNode(String data){
        this.hash = StringUtil.applySha256(data);
    }
    public MerkleNode getLeftNode()
    {
        return leftNode;
    }
    public MerkleNode getRightNode()
    {
        return rightNode;
    }
    public String getHash(){
        return hash;
    }
    void ShowStruct(){
        System.out.println("=====");
        System.out.println("Node Hash: "+hash);
        if(leftNode != null){
            System.out.println("Not a Leaf:");
            System.out.println("Left Node Hash: "+leftNode.hash);
        }
        if(rightNode != null){
            System.out.println("Right Node Hash: "+rightNode.hash);
        }
        if(leftNode != null){
            leftNode.ShowStruct();
        }
        if(rightNode != null){
            rightNode.ShowStruct();
        }
    }
}
