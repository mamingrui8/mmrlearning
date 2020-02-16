package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.TimeUnit;

/**
 * 检查中断状态的惯用手法——Thread.interrupted()
 */
public class InterruptingIdiom {
    public static void main(String[] args) throws Exception{
        if (args.length != 1) {
            System.out.println("usage: java InterruptingIdiom delay-in-ms");
            System.exit(1);
        }
        Thread t = new Thread(new Block3());
        t.start();
        TimeUnit.MILLISECONDS.sleep(new Integer(args[0]));
        t.interrupt();
    }
}

class NeedsCleanup{
    private final int id;
    public NeedsCleanup(int id) {
        this.id = id;
        System.out.println("NeedsCleanup " + id);
    }
    public void cleanup() {
        System.out.println("Cleaning up " + id);
    }
}

/**
 * 本类强调了清理资源的重要性。每一个被创建出来的NeedsCleanup对象都必须在后面紧跟着try/finally子句，
 * 以确保cleanup()方法总是被调用
 */
class Block3 implements Runnable {
    private volatile double d = 0.0;

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                // point1
                NeedsCleanup n1 = new NeedsCleanup(1);
                try {
                    System.out.println("Sleeping");
                    TimeUnit.SECONDS.sleep(1);
                    // point2
                    NeedsCleanup n2 = new NeedsCleanup(2);
                    try {
                        System.out.println("Calculating");
                        //A time-consuming, non-blocking operation:
                        for (int i = 1; i < 2500000; i++) {
                            d = d + (Math.PI + Math.E) / d;
                        }
                        System.out.println("Finished time-consuming operation");
                    }finally {
                        n2.cleanup();
                    }
                }finally {
                    n1.cleanup();
                }
            }
            System.out.println("Exiting via while() test");
        }catch (InterruptedException e){
            System.out.println("Exiting via InterruptedException");
        }
    }
}