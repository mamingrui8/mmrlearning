package com.mmr.learn.thread.lesson16.t4;

public class RunFirst {
    public static void main(String[] args){
        ThreadB b = new ThreadB();
        ThreadA a = new ThreadA(b);
        //a的run()中持有ThreadB的对象锁，由于sleep(5)导致a会一直持有锁，直到5秒后才会释放
        //那么问题来了，b的run()中被加上了同步锁，b能否正常运行呢？
        a.start();
        b.start();
        System.out.println("main end " + System.currentTimeMillis());
    }
}
