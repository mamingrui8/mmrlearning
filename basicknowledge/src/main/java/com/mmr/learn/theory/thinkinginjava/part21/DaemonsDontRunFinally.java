package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.TimeUnit;

/**
 * 通过本案例可以可看，非后台线程执行完毕后(这里并不带代表该非后台线程)，进程并不会立刻被杀死，而是在一个不可预知的未来会被杀死(可能是0.5秒后，也可能是0.8秒钟)
 * 由非后台线程创建出的守护线程将得以运行，但在进程被杀死后，这些守护线程也会随之消亡
 * @author Charles Wesley
 * @date 2019/12/2 23:44
 */
public class DaemonsDontRunFinally {
    public static void main(String[] args) {
        Thread t = new Thread(new ADaemon());
        t.setDaemon(true);
        t.start();
        System.out.println("end");
    }
}

class ADaemon implements Runnable{
    @Override
    public void run() {
        System.out.println("Starting ADaemon");
        int i = 0;
        while(true){
            System.out.println(++i);
        }

//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            System.out.println("This should always run?");
//        }
    }
}

