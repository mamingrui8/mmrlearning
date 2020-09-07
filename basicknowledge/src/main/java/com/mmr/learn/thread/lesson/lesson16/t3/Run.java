package com.mmr.learn.thread.lesson.lesson16.t3;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args){
        try {
            ThreadB b = new ThreadB();
            ThreadA a = new ThreadA(b);
            a.start();//此处会进入sleep(6) ，那么在这6秒内ThreadA到底会不会释放锁呢？我们拭目以待
            TimeUnit.SECONDS.sleep(1);
            ThreadC c = new ThreadC(b);
            c.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
