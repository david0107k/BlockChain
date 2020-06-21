package com.blockchain;

import java.util.ArrayList;

public class Blockchain implements java.io.Serializable {
    private ArrayList<Block> blockchain;
    private int blockId = 0;
    public int difficulty;

    public Blockchain(Block firstBlock,int difficulty){
        this.difficulty = difficulty;
        blockchain = new ArrayList<Block>();
        blockchain.add(firstBlock);
        blockId++;
    }
    public void addBlock(Block block){
        blockchain.add(block);
        blockId++;
    }
    public void addBlockByData(ArrayList<String> data){
//        Block block = new Block(data, getLastBlock().hash);
//        block.mineBlock(difficulty);
//        addBlock(block);
    }
    public ArrayList<Block> get(){
        return blockchain;
    }
    public void ShowAllBlock(){
        for(int i = 0 ; i < blockchain.size() ; i++){
            System.out.println("Block - " + i + " : " );
            getBlock(i).ShowTrascation();
            getBlock(i).ShowHashTree();
            System.out.println("*******************************");
        }
    }
    public int getBlockChainLength(){
        return blockchain.size();
    }

    public Block getBlock(int i){
        return blockchain.get(i);
    }
    public Block getLastBlock(){
        return blockchain.get(blockchain.size()-1);
    }
    public void mineBlock(){
        System.out.println("Trying to Mine block "+ blockId +"... ");
        getLastBlock().mineBlock(difficulty);
    }
    public Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
//loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++  ) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
//compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
//compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
//check if hash is solved
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}
