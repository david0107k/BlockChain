package com.blockchain;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static void waitAsec(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        ArrayList<String> firstData = new ArrayList<String>();
        firstData.add("firstData");
        Blockchain blockchain = new Blockchain(new Block(firstData,"0" , "node-2",0),5);

        ArrayList<String> edgeIPList = new ArrayList<String>();
        edgeIPList.add("192.168.56.101");
        edgeIPList.add("192.168.56.102");

        RawDataReceiver rawDataReceiver = new RawDataReceiver(blockchain,"192.168.56.101",1234 , edgeIPList, 2);
        rawDataReceiver.start();

        BlockReceiver blockReceiver = new BlockReceiver(blockchain,"192.168.56.101",1235 , edgeIPList, 2);
        blockReceiver.start();

        boolean stop = false;
        while(!stop){
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            switch (input){
                case "status":
                    System.out.println(blockchain.getBlockChainLength());
                    break;
                case "exit":
                    rawDataReceiver.terminate();
                    System.out.println("Stop Now.");
                    stop = true;
                case "getohterip":
                    System.out.println(rawDataReceiver.getOtherIPList());
                default:
                    System.out.println("Wrong Input");
                    break;
            }
        }
        System.out.println("return");
        return;
    }
}
