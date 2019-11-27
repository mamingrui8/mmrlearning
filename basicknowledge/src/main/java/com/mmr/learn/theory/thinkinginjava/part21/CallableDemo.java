package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author Charles Wesley
 * @date 2019/11/27 9:51
 */
public class CallableDemo {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<String>> results = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            results.add(exec.submit(new TaskWithResult(i)));
        }
        for(Future<String> future : results){
            try {
                while(true){
                    if(future.isDone()){
                        System.out.println(future.get());
                        break;
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally{
                exec.shutdown();
            }
        }

//        for(int i = 0; i<10; i++){
//            try {
//                if(i % 2 == 0){
//                    TimeUnit.SECONDS.sleep(10);
//                    System.out.println(results.get(i).get());
//                }else{
//                    System.out.println(i);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }finally {
//                exec.shutdown();
//            }
//        }
    }
}
