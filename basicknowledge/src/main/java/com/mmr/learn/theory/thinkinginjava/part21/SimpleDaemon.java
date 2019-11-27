package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.TimeUnit;

/**
 * 后台线程
 * @author Charles Wesley
 * @date 2019/11/27 23:31
 */
public class SimpleDaemon implements Runnable{
    @Override
    public void run() {
        try {
            while(true){
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(Thread.currentThread() + " " + this);
            }
        }catch (InterruptedException e){
            System.out.println("sleep interrupted");
        }
    }

    public static void main(String[] args) throws Exception{
        for(int i = 0; i < 10; i++){
            Thread daemon = new Thread(new SimpleDaemon());
            //必须在启动之前，将线程设置成"后台线程"
            daemon.setDaemon(true);
            daemon.start();
        }
        System.out.println("All deamons started");

        TimeUnit.MILLISECONDS.sleep(175);
    }
    /*
        本案例中，当main函数执行完毕后，SimpleDaemon(后台线程)将会被杀死，不会被执行。
     */
}
