package com.mmr.learn.theory.thinkinginjava.part21;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 管道通信
 */
public class PipedIO {
    public static void main(String[] args) throws Exception{
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(sender);
        exec.execute(receiver);
        TimeUnit.SECONDS.sleep(4);
        exec.shutdownNow();
    }
}

class Sender implements Runnable {
    private Random rand = new Random(47);
    private PipedWriter out = new PipedWriter();
    public PipedWriter getPipedWriter() {
        return out;
    }
    @Override
    public void run() {
        try {
            while(true) {
                for (char c = 'A'; c <= 'z'; c++) {
                    out.write(c);
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                }
            }
        }catch (IOException e) {
            System.out.println(e + " Sender write interrupted");
        }catch (InterruptedException e) {
            System.out.println(e + " Sender sleep interrupted");
        }
    }
}

class Receiver implements Runnable {
    private PipedReader in;
    public Receiver(Sender sender) throws IOException{
        in = new PipedReader(sender.getPipedWriter());
    }
    @Override
    public void run() {
        try {
            while(true) {
                // 如果管道中没有更多的数据 则read()方法将阻塞
                // 注意: 与普通IO存在一个重要的差异: PipedReader是可以被打断的，而比如System.in.read()这种方法是不能被
                //      interrupt() 打断的
                System.out.println("Read: " + (char)in.read() + ". ");
            }
        }catch (IOException e) {
            System.out.println(e + " Receiver read exception");
        }
    }
}