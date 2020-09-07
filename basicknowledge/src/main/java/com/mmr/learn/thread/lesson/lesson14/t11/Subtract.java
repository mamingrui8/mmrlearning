package com.mmr.learn.thread.lesson.lesson14.t11;

public class Subtract {
    private String lock;
    public Subtract(String lock){
        super();
        this.lock = lock;
    }
    public void subtract(){
        try {
            synchronized (lock){
                if(ValueObject.list.size() == 0){
                    System.out.println("wait begin ThreadName=" + Thread.currentThread().getName());
                    lock.wait();
                    System.out.println("wait end ThreadName=" + Thread.currentThread().getName());
                }
                ValueObject.list.remove(0);
                System.out.println("list size=" + ValueObject.list.size());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
