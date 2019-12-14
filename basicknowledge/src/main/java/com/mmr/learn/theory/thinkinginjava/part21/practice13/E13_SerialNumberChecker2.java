package com.mmr.learn.theory.thinkinginjava.part21.practice13;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 这个例子告诉我们:
 * 如果待操作的域出现的写操作方法全部都是同步的，则没有必要将变量(比如本例中的serialNumber)使用volatile来修饰。
 * 这是因为同步代码块会刷新主存，线程从CPU缓存中取出的域的值一定是最新的，没有可视性的问题，因此没有必要使用volatile。
 */
class SerialNumberGenerator2 {
    private static int serialNumber;
    public synchronized static int nextSerialNumber() {
        return serialNumber++;
    }
}

public class E13_SerialNumberChecker2 {
    private static final int SIZE = 10;
    private static CircularSet serials =
            new CircularSet(1000);
    private static ExecutorService exec =
            Executors.newCachedThreadPool();
    static class SerialChecker implements Runnable {
        public void run() {
            while(true) {
                int serial =
                        SerialNumberGenerator2.nextSerialNumber();
                if(serials.contains(serial)) {
                    System.out.println("Duplicate: " + serial);
                    System.exit(0);
                }
                serials.add(serial);
            }
        }
    }
    public static void main(String[] args) throws Exception {
        for(int i = 0; i < SIZE; i++)
            exec.execute(new SerialChecker());
        if(args.length > 0) {
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
            System.out.println("No duplicates detected");
            System.exit(0);
        } else {
            System.err.println("Provide a sleep time in sec.");
            System.exit(1);
        }
    }
}

class CircularSet {
    private int[] array;
    private int len;
    private int index = 0;
    public CircularSet(int size) {
        array = new int[size];
        len = size;
        //不使用SerialNumberGenerator，对array数组赋初值
        for (int i = 0;i < size; i++) {
            array[i] = -1;
        }
    }

    public synchronized void add(int i) {
        array[index] = i;
        //对index进行自增，接着覆盖旧值
        index = index++ % len;
    }

    public synchronized boolean contains(int val) {
        for(int i = 0; i < len; i++) {
            if(array[i] == val) {
                return true;
            }
        }
        return false;
    }
}