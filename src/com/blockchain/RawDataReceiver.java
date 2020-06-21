package com.blockchain;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class RawDataReceiver extends Thread{

    Blockchain blockchain;
    int nodeId;

    private ServerSocket server = null;
    Socket socket = null;
    int RAWDATA_LISTEN_PORT;
    int BLOCK_LISTEN_PORT;
    String serverIp;

    ArrayList<MultiSocketServer> threadList;
    ArrayList<String> edgeIPList;
    boolean stop = false;
    String nodeName = "node" + nodeId;
    public RawDataReceiver(Blockchain blockchain, String serverIp, int listen_port , ArrayList<String> edgeIPList,int nodeId){
        this.RAWDATA_LISTEN_PORT = listen_port;
        this.blockchain = blockchain;
        this.serverIp = serverIp;
        this.edgeIPList = edgeIPList;
        this.nodeId = nodeId;
        threadList = new ArrayList<MultiSocketServer>();
    }
    public void run(){
        ArrayList<String> data = new ArrayList<String>();
        try {
            server = new ServerSocket(RAWDATA_LISTEN_PORT, 0, InetAddress.getByName(serverIp));
            System.out.println("started: " + server);
            while (!stop) {
                // listen request
                socket = server.accept();

                // new thread execution services
                synchronized (data){
                    MultiSocketServer thread = new MultiSocketServer(socket,data);
                    thread.start();
                    if(data.size() >= 256){
                        thread.join();
                        Block newBlock = new Block(data,blockchain.getLastBlock().hash, nodeName,blockchain.getBlockChainLength() + 1);
                        synchronized (blockchain)
                        {
                            blockchain.addBlock(newBlock);
                            syncBlockToOther(newBlock);
                            if(blockchain.getBlockChainLength() % 100 == 0)
                            {
                                //System.out.println("blockchain length is : " + blockchain.getBlockChainLength());
                            }
                        }
                        data.clear();;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void syncBlockToOther(Block block){
        for(String ip:getOtherIPList()){
            BlockSocketClient socketClient = new BlockSocketClient(ip,BLOCK_LISTEN_PORT,1 , block);
            socketClient.start();
        }
    }
    public ArrayList<String> getOtherIPList(){
        ArrayList<String> result = new ArrayList<>(edgeIPList);
        result.remove(serverIp);
        return result;
    }

    public void terminate(){
        try {
            System.out.println("terminate");
            server.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            stop = true;
            for (MultiSocketServer thread: threadList) {
                thread.stop = true;
            }
        }
    }

}
