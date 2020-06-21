package com.blockchain;

import java.util.ArrayList;
import java.util.Date;
public class Block implements java.io.Serializable {
    public String hash;
    public String previousHash;
    private MerkleTree merkleTree;
    private ArrayList<String> dataList; //our data will be a simple message.
    private long timeStamp; //as number of milliseconds since 1/1/1970.
    private int nonce;
    private boolean isPoWFinished = false;
    private String sourceWork = "";
    private int blockId;
    //Block Constructor.
    public Block(ArrayList<String> data,String previousHash,String sourceWork,int blockId) {
        this.dataList = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.merkleTree = new MerkleTree(data);
        this.hash = this.merkleTree.getTreeHash(); //Making sure we do this after we set the other values.
        this.sourceWork = sourceWork;
        this.blockId = blockId;
        calculateHash();
    }
    public void ShowTrascation(){
        for(int i = 0 ; i < dataList.size() ; i++){
            System.out.println("Trasction " + i + ": " + dataList.get(i));
        }
    }
    public int getBlockId(){
        return blockId;
    }
    public void ShowHashTree(){
        merkleTree.ShowTreeStruct();
    }
    //Calculate new hash based on blocks contents
    public String calculateHash() {
        String input = new StringBuilder(previousHash)
                .append(Long.toString(timeStamp))
                .append(Integer.toString(nonce))
                .append(Integer.toString(blockId))
                .append(merkleTree.getRoot().getHash()).toString();
        String blockHash = StringUtil.applySha256(input);
        return blockHash;
    }
    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " +  hash);
        isPoWFinished = true;
    }
}
