package com.mmr.learn.thread.lesson13.synchronizedUpdateNewValue;

/**
 * Description: synchronized代码块具有volatile同步的功能
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月22日 16:06
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args){
        try {
            Service service = new Service();
            ThreadA a = new ThreadA(service);
            a.start();
            Thread.sleep(1000);
            ThreadB b = new ThreadB(service);
            b.start();
            System.out.println("已经发起停止的命令了！");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
