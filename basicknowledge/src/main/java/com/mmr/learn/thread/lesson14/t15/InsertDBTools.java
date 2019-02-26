package com.mmr.learn.thread.lesson14.t15;

public class InsertDBTools {
    private boolean isInsertB = false;

    synchronized public void backupA(){
        try {
            if(isInsertB){
                wait();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("★★★★★");
            }
            isInsertB = true;
            notifyAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    synchronized public void backupB(){
        try {
            if(!isInsertB){
                wait();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("☆☆☆☆☆");
            }
            isInsertB = false;
            notifyAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
