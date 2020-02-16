package com.mmr.learn.theory.thinkinginjava.part21.practice18;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class E18_practice {
    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        Future<?> submit = exec.submit(new Task(new NonTask()));
        TimeUnit.MILLISECONDS.sleep(100);
        //向Task发送一条interrupt()命令
        submit.cancel(true);
        exec.shutdown();
    }
}

class NonTask {
    public void t() throws InterruptedException{
        TimeUnit.SECONDS.sleep(60);
    }
}

class Task implements Runnable{
    private NonTask nonTask;
    public Task(NonTask nonTask) {
        this.nonTask = nonTask;
    }

    @Override
    public void run() {
        try {
            nonTask.t();
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        } finally {
            System.out.println("any cleanup code here");
        }
    }
}