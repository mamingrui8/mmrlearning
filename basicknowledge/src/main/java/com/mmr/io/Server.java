package com.mmr.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2018年12月24日 22:42
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
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
        Handler(Socket socket){
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
                    if((readMessage = reader.readLine()) == null){ //若接收道德数据不存在，则跳出循环
                        break;
                    }
                    writer.print("Server received: " + readMessage);
                    writer.flush();
                }
            }catch (IOException e){
                e.printStackTrace();
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
