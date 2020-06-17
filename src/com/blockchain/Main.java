package com.blockchain;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Main {
    static void waitAsec(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<String> data1 = new ArrayList<String>();
        data1.add("first");
        data1.add("data");
        data1.add("list");

        ArrayList<String> data2 = new ArrayList<String>();
        data2.add("second");
        data2.add("value");
        data2.add("table!!");

        ArrayList<String> data3 = new ArrayList<String>();
        data3.add("thrid");
        data3.add("one");
        data3.add("item!!");

        Blockchain blockchain = new Blockchain(new Block(data1, "0"),5);
        blockchain.addBlock(new Block(data2,blockchain.getLastBlock().hash));
        blockchain.addBlock(new Block(data3,blockchain.getLastBlock().hash));

        blockchain.ShowAllBlock();


//        blockchain.addBlockByData("Yo im the second block");
//        blockchain.addBlockByData("Hey im the third block");

//        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
//        System.out.println("\nThe block chain: ");
//        System.out.println(blockchainJson);



//        SocketServer server = new SocketServer();
//        server.start();
//        System.out.println("Server is Starting");
//
//        String targetIp = "192.168.56.102";// 連線的ip
//        //String targetIp = "127.0.0.1";// 連線的ip
//        int port = 8765;// 連線的port
//
//        int i = 0;
//        SocketClient client = new SocketClient(targetIp,port);
//        client.sendMessage(blockchain.getLastBlock());


//        while(i++ <= 10){
//            waitAsec();
//            client.sendMessage(blockchain.getLastBlock());
//        }
    }
}
