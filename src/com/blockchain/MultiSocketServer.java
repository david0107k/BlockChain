package com.blockchain;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiSocketServer extends Thread {
    volatile boolean stop = false;

    public static final int LISTEN_PORT = 1234;
    Socket socket = null;
    ArrayList<String> dataList;

    public MultiSocketServer(Socket socket) {
        this.socket = socket;
    }
    public MultiSocketServer(Socket socket,ArrayList<String> dataList) {
        this.socket = socket;
        this.dataList = dataList;
    }
    public void run() {
        if(stop) return;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            byte[] buffer = new byte[1024];

            int len = in.read(buffer, 0, 1024);
            String inputData = new String(buffer);

            String response = recieverDataAction(inputData,false);
            dataList.add(inputData);

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
