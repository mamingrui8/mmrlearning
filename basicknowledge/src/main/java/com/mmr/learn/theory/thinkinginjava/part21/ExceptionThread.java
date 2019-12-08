package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 如果在run()中没有正确的捕获异常，那么这个逃逸的异常将不会被捕获到，即便我们把线程执行的语句exec.exeute()放在try/catch块中也无济于事。
 * @author Charles Wesley
 * @date 2019/12/8 18:32
 */
public class ExceptionThread implements Runnable{
    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        try {
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new ExceptionThread());
        }catch (RuntimeException e){
            System.out.println("exception has been handled");
        }
    }
}
