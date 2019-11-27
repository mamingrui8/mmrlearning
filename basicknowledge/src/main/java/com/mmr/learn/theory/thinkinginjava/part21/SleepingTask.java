package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 休眠
 * @author Charles Wesley
 * @date 2019/11/27 10:49
 */
public class SleepingTask extends LifeOff{
    /**
     * 当前run()会覆盖掉LifeOff中的run()
     */
    @Override
    public void run(){
        while(countDown-- > 0){
            System.out.println(status());
            //之前我们使用的是:
            //Thread.sleep(100);
            //在Java SE5/6中，使用如下新特性:
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                //特别注意: 异常不能跨线程传播，所以必须在本地处理所有在任务内部中产生的异常
                System.out.println("Interrupted");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++){
            exec.execute(new SleepingTask());
        }
        exec.shutdown();
    }

    /*
        执行结果
        #2(9).
        #3(9).
        #4(9).
        #1(9).
        #0(9).
        #0(8).
        #3(8).
        #4(8).
        #2(8).
        #1(8).
        疑问: 有5个线程A，B,C,D,E  A通过sleep()停止时，由B,C,D,E抢夺线程调度的权利，很好理解。但B执行完毕后，为什么A没有参与抢夺执行权呢？
        答: 这是因为B线程遇到sleep()进行休眠时，A线程也处于休眠状态！！(操作系统的执行速度太快了)，以至于只能由C,D,E抢夺线程调度的权利。
     */
}
