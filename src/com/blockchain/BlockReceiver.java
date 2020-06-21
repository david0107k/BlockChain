package com.blockchain;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class BlockReceiver extends Thread{

    Blockchain blockchain;
    int nodeId;

    private ServerSocket server = null;
    Socket socket = null;

    int BLOCK_LISTEN_PORT;
    String serverIp;

    ArrayList<MultiSocketServer> threadList;
    ArrayList<String> edgeIPList;
    boolean stop = false;
    String nodeName = "node" + nodeId;
    public BlockReceiver(Blockchain blockchain, String serverIp, int serverPort , ArrayList<String> edgeIPList,int nodeId){
        this.blockchain = blockchain;
        this.serverIp = serverIp;
        this.BLOCK_LISTEN_PORT = serverPort;
        this.edgeIPList = edgeIPList;
        this.nodeId = nodeId;
        threadList = new ArrayList<MultiSocketServer>();
    }
    public void run(){

        try {
            server = new ServerSocket(BLOCK_LISTEN_PORT, 0, InetAddress.getByName(serverIp));
            System.out.println("started: " + server);
            while (!stop) {
                // listen request
                socket = server.accept();

                // new thread execution services
                synchronized (blockchain){
                    BlockMultiSocketServer thread = new BlockMultiSocketServer(socket,blockchain);
                    thread.start();


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
