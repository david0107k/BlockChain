package com.blockchain;

import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.BufferedOutputStream;
public class SocketClient {
    private InetSocketAddress isa;
    public SocketClient(String targetIp,int port) {
        isa = new InetSocketAddress(targetIp, port);
    }
    void sendMessage(Block block)
    {
        try {
            Socket client = new Socket();
            client.connect(isa, 10000);
            ObjectOutputStream out = new ObjectOutputStream(client
                    .getOutputStream());
            // 送出字串

            out.writeObject(block);
            out.flush();
            out.close();
            out = null;
            client.close();
            client = null;
        } catch (java.io.IOException e) {
            System.out.println("Socket連線有問題 !");
            System.out.println("IOException :" + e.toString());
        }

    }

}
