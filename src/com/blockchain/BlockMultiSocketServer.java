package com.blockchain;


import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class BlockMultiSocketServer extends Thread {
    volatile boolean stop = false;

    public static final int LISTEN_PORT = 1234;
    Socket socket = null;
    Blockchain blockchain;

    public BlockMultiSocketServer(Socket socket) {
        this.socket = socket;
    }
    public BlockMultiSocketServer(Socket socket,Blockchain blockchain) {
        this.socket = socket;
        this.blockchain = blockchain;
    }
    public void run() {
        if(stop) return;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();

            ObjectInputStream objectInputStream = new ObjectInputStream(in);

            Block block = (Block) objectInputStream.readObject();

            String response = recieverDataAction(Integer.toString(block.getBlockId()),false);
            blockchain.addBlock(block);

            out.write(response.getBytes());
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception closeErr) {
                System.out.println("close socket fail");
            }
        }
    }
    private String recieverDataAction(String inputData,boolean isShow){
        String response = "Server return:" + inputData;
        if(isShow){
            System.out.println("request=" + inputData);
            System.out.println("response=" + response);

        }
        return response;
    }
}

