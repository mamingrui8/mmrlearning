package com.mmr.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2018年12月24日 22:42
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Client {
    public static void main(String[] args) {
        String host = null;
        int port = 0;
        if(args.length > 2){
            host = args[0];
            port = Integer.parseInt(args[1]);
        }else{
            host = "127.0.0.1";
            port = 9999;
        }

        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        PrintWriter pw = null;
        BufferedReader br = null;
        try{
            socket = new Socket(host, port);
            String message = null;
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)); //把字节流转成字符流  socket中流通的是字节流
            pw = new PrintWriter(socket.getOutputStream(), true);

            while(true){
                System.out.println("进入本次循环");
                message = scanner.nextLine();
                if(message.equals("exit")){
                    System.out.println("客户端输入了exit，因此断开了连接");
                    break;
                }
                System.out.println("准备进入下次循环");
                pw.println(message);
                pw.flush();
                System.out.println("我是客户端，接收到的数据如下: " + br.readLine()); //readLine()是一个阻塞函数，当没有数据读取时，就会一直阻塞在这里

            }

        }catch (IOException e){
            e.printStackTrace();
        }finally{
            if(null != br){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != pw){
                pw.close();
            }
            if(null != socket){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
