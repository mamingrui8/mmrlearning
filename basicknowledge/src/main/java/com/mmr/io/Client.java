package com.mmr.io;

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
                message = scanner.nextLine();
                if(message.equals("exit")){
                    break;
                }
                pw.println(message);
                pw.flush();
                System.out.println("Server: " + br.readLine());
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
