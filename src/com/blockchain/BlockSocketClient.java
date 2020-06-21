package com.blockchain;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BlockSocketClient extends Thread {
    public BlockSocketClient(String targetIP, int targetPort, int threadId,Block block){
        this.threadId =threadId;
        this.targetIP = targetIP;
        this.targetPort = targetPort;
        this.threadId = threadId;
        this.block = block;
    }
    Block block;

    private int threadId;
    boolean stop = false;

    String targetIP;
    int targetPort;

    public static final int PORT = 1234;
    InetAddress addr = null;
    Socket socket = null;

    int dataId = 1;
    public void run() {
        InputStream in = null;
        ObjectOutputStream out = null;
        try{
            socket = new Socket();
            addr = InetAddress.getByName(targetIP);
            socket.connect(new InetSocketAddress(addr, PORT), 30000);

            OutputStream outputStream = socket.getOutputStream();
            out = new ObjectOutputStream(outputStream);

            out.writeObject(block);
            out.flush();

            in = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int len = in.read(buffer, 0, 1024);
            String inData = new String(buffer);
            System.out.println(inData);
            dataId++;
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
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
    void terminate(){
        stop = true;
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}