package com.mmr.learn.thread.lesson.lesson14.t2;

public class Run {
    public static void main(String[] args){
        try{
            String newString = new String();
            newString.wait();
        } catch (Exception e){
           e.printStackTrace();
        }
    }
}
