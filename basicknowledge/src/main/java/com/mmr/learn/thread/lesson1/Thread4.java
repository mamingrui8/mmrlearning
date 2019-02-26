package com.mmr.learn.thread.lesson1;

/**
 * Description: isAlive()方法
 *
 * 若线程已经启动且尚未终止 (不管是否执行完毕run()方法) 则都会判定isAlive()的值为true
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月03日 22:57
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Thread4 {
    public static void main(String[] args) throws InterruptedException {
//        Thread4_1 thread4_1 = new Thread4_1();
//        System.out.println("begin===" + thread4_1.isAlive());
//        thread4_1.start();
//        //Thread4_1.sleep(1000);
//        System.out.println("end===" + thread4_1.isAlive());

        Thread4_1 thread4_1 = new Thread4_1();
        Thread thread = new Thread(thread4_1);
        thread.start();
    }


}

class Thread4_1 extends Thread{

    @Override
    public void run(){
        System.out.println("this.getName()->" + this.getName());
        System.out.println("this.isAlive(): " + this.isAlive());
    }
}