package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 调用shutdown()命令后，处于悬挂队列中的任务还会得到执行吗？
 * @author Charles Wesley
 * @date 2019/11/27 9:18
 */
public class ThreadShutDown {
    public static void main(String[] args) {
        ThreadShutDown bootstrap = new ThreadShutDown();
        //直接使用Thread
        //bootstrap.threadTest();

        //使用FixedThreadPool
        //bootstrap.fixedThreadPoolTest();

        //使用SingThreadPool
        bootstrap.singThreadPoolTest();

        /*
            事实证明，即便执行了Executor.shutdown()方法，线程调度器也不会放弃之前提交的所有任务，无论是否处于悬挂任务队列中。
         */
    }

    private void threadTest(){
        new Thread(new LongTask()).start();
    }

    private void fixedThreadPoolTest(){
        ExecutorService es = Executors.newFixedThreadPool(3);
        for(int i =0; i < 5; i++){
            es.execute(new LongTask());
        }
        es.shutdown();
    }

    private void singThreadPoolTest(){
        ExecutorService es = Executors.newSingleThreadExecutor();
        for(int i =0; i < 5; i++){
            es.execute(new LongTask());
        }
        es.shutdown();
    }
}

class LongTask implements Runnable{
    private static Integer counter = 0;
    private Integer id = counter++;

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("id: " + id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}