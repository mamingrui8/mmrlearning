package com.mmr.learn.theory.thinkinginjava.part21.practice3;

import com.mmr.learn.theory.thinkinginjava.part21.practice1.Printer;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用不同的线程池来驱动Print任务
 * @author Charles Wesley
 * @date 2019/11/27 9:03
 */
public class PrintThreadPools {
    @Test
    public void cacheThreadPoolTest(){
        ExecutorService es = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++){
            es.execute(new Printer());
        }
    }

    @Test
    public void fixedThreadPoolTest(){
        ExecutorService es = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 10; i++){
            es.execute(new Printer());
        }
    }

    @Test
    public void singleThreadPoolTest(){
        ExecutorService es = Executors.newSingleThreadExecutor();
        for(int i = 0; i < 5; i++){
            es.execute(new Printer());
        }
    }
}
