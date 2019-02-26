package com.mmr.learn.thread.lesson14.t3;

public class Run {
    public static void main(String[] args){
        try{
            String lock = new String();
            System.out.println("syn上面的代码");
            synchronized (lock){
                System.out.println("syn 第一行");
                lock.wait();
                System.out.println("wait 下的代码");
            }
            System.out.println("syn下面的代码");
        } catch (Exception e){
           e.printStackTrace();
        }
    }
}
