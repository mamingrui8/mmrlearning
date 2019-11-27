package com.mmr.learn.theory.thinkinginjava.part21.practice6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Charles Wesley
 * @date 2019/11/27 22:28
 */
public class SleepThread {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        List<Future<Integer>> futures = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            futures.add(exec.submit(new SleepTask()));
        }

        for(Future<Integer> future : futures){
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally{
                exec.shutdown();
            }
        }
    }
}
