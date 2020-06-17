package com.blockchain;

import java.net.ServerSocket;
import java.net.Socket;
public class SocketServer extends java.lang.Thread {
    private boolean OutServer = false;
    private ServerSocket server;
    private final int ServerPort = 8765;// 要監控的port
    public SocketServer() {
        try {
            server = new ServerSocket(ServerPort);
        } catch (java.io.IOException e) {
            System.out.println("Socket啟動有問題 !");
            System.out.println("IOException :" + e.toString());
        }
    }
    public void run()
    {
        Socket socket ;
        java.io.ObjectInputStream in ;
        System.out.println("伺服器已啟動 !"  );
        while(!OutServer)
        {
            socket = null;
            try
            {
                synchronized(server)
                {
                    socket = server.accept();
                }
                System.out.println("取得連線 : InetAddress = " +
                        socket.getInetAddress()  );
                socket.setSoTimeout(15000);

                in = new
                        java.io.ObjectInputStream(socket.getInputStream());

                Block data = (Block) in.readObject();

                System.out.println("我取得的值:"+data.hash);
                in.close();
                in = null ;
                socket.close();



            }
            catch(java.io.IOException e)
            {
                System.out.println("Socket連線有問題 !" );
                System.out.println("IOException :" + e.toString());
            }
            catch(java.lang.ClassNotFoundException e)
            {
                System.out.println("ClassNotFoundException :" +
                        e.toString());
            }
        }
    }
}
