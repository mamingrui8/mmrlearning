package com.mmr.learn.theory.thinkinginjava.part12;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws Exception{
        //System.out.println(getTime("2"));

//        PrintStream ps = new PrintStream("D:/log1.txt");
//        System.setOut(ps);

//        while(true){
//            LocalDateTime dateTime = LocalDateTime.now();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
//            System.out.println(dateTime.format(formatter));
//        }

//        while(true){
//            String time = new Timestamp(System.currentTimeMillis()).toString();
//            System.out.println(time.split(" ")[1]);
//        }

        int max = 10000;
        int min = (int) Math.round(Math.random() * 8000);
        long sleepTime = Math.round(Math.random() * (max - min));

        System.out.println(sleepTime);

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while(networkInterfaces.hasMoreElements()){
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if(networkInterface.getName().equals("lo")){
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while(inetAddresses.hasMoreElements()){
                    InetAddress address = inetAddresses.nextElement();
                    String ip = address.getHostAddress();
                    System.out.println(ip);
                }
            }
        }
    }

    public static String getTime(String i) {
        String time = new Timestamp(System.currentTimeMillis()).toString();
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

        System.out.println("time: " + time);
        String str = "";
        if ("1".equals(i))
            str = time.split(" ")[0];
        else if ("2".equals(i))
            //str = time.split(" ")[1];
            str = dateTime.format(formatter);
        else if ("3".equals(i))
            str = time.split(" ")[0].replace("-", "");
        else if ("4".equals(i))
            str = new Time(new Date().getTime()).toString().replace(":", "");
        else if ("5".equals(i))
        {
            str = "test-" + time.split(" ")[0].replace("-", "") + time
                    .split(" ")[
                    1].replace(":", "").replace(".", "") + "-" +
                    getRandom();
        }
        return str;
    }

    public static String getRandom() {
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++)
        {
            result = result + random.nextInt(10);
        }
        return result;
    }
}
