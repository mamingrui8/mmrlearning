package com.mmr.learn.io.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description: BIO编程小案例---3   服务端升级
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月01日 22:22
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 * ThreadPool仅仅能让服务端的处理效率提高，本质上还是BIO
 */
public class ServerPool {
    public static void main(String[] args) {
        int port = genPort(args);

        ServerSocket server = null;
        ExecutorService service = Executors.newFixedThreadPool(50); //TODO 在创建线程池时引入了SecurityManager的概念。

        try{
            server = new ServerSocket(port);
            System.out.println("Server started!");
            while (true) {
                Socket socket = server.accept();
                service.execute(new Handler(socket));//只需要把携带有socket的任务丢给线程池即可，至于线程池如何创建线程，如何启动线程，根本不用操心。
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
        @SuppressWarnings("Duplicates")
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
