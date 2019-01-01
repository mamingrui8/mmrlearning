package com.mmr.io.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Description: BIO编程小案例---2
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2018年12月24日 22:42
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 * 开发时遇到的问题:
 * 1. 为什么服务端在接收到客户端请求后，答复信息没能发回至客户端。
 *     答: 起初，服务端返回数据时，使用了: writer.print(""), 而客户端却使用了BufferedReader.readline()去读取数据。
 *     readLine()的特性是按行读取，并且方法是阻塞的，当且仅当读到了一行数据时才会返回。换行符便成为了"一行数据"的重要指标。
 *     很遗憾，print()方法输出字符至流中并不会包含换行符，因此造成了"互相等待"的恶果。
 *     总结: 此问题遇到的原因在于不了解readLine()的原理。
 */
public class Server {
    public static void main(String[] args) {
        int port = genPort(args);

        ServerSocket server = null;

        try{
            server = new ServerSocket(port);
            System.out.println("Server started!");
            while (true) {
                Socket socket = server.accept();
                new Thread(new Handler(socket)).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            if(null != server){
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Handler implements Runnable{
        Socket socket = null;
        public Handler(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
                String readMessage = null;
                while(true){
                    System.out.println("Server reading...");
                    if((readMessage = reader.readLine()) == null){ //若接收到不到任何数据，则跳出循环
                        System.out.println("没有接受到任何数据，故跳出循环！");
                        break;
                    }
                    System.out.println("我是服务端, 接收到的数据如下: " + readMessage);
                    writer.println("Server received: " + readMessage);
                    writer.flush();
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally{
                if(null != socket){
                    try{
                        socket.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static int genPort(String[] args){
        if(args.length > 0){
            try{
                return Integer.parseInt(args[0]);
            }catch(NumberFormatException e){
                return 9999;
            }
        }else{
            return 9999;
        }
    }
}
