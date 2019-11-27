package com.mmr.learn.theory.thinkinginjava.part21.practice5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Charles Wesley
 * @date 2019/11/27 10:24
 */
public class FibonacciCallableThread {
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        List<Future<String>> futureList = new ArrayList<>();
        
        for(int i = 1; i < 5; i++){
            futureList.add(es.submit(new FibonacciTask(i)));
        }

        es.shutdown();

        for(Future<String> future : futureList){
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
