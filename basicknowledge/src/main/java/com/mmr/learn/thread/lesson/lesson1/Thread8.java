package com.mmr.learn.thread.lesson.lesson1;

/**
 * Description: 守护线程
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月14日 14:18
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Thread8 {
    /**
     * 当且仅当进程中含有非守护线程时，守护线程才有存在的必要。当JVM实例中最后一个非守护线程结束时，守护线程会随jvm一同消亡。
     * 守护线程最典型的应用就是GC
     *
     * main Thread不是守护线程
     */
    public static void main(String[] args){
        System.out.println("主线程是守护线程吗? 答: " + Thread.currentThread().isDaemon());
        Thread8 thread8 = new Thread8();
        thread8.test1();
    }

    /**
     * 守护进行小例子
     *
     * 如果thread是一个普通的线程，那么即便是main线程执行完毕，thread也丝毫不会受到影响。
     * 但加上了thread.setDeamon(true) 就完全不一样了。
     * thread作为守护线程，只为照顾main线程而存在，因此当main线程结束后，thread也会随之结束工作。
     */
    public void test1(){
        try{
            Thread8_1 thread = new Thread8_1();
            thread.setDaemon(true);
            thread.start();
            Thread.sleep(5000);
            System.out.println("主线程结束");
        } catch (Exception e){
           e.printStackTrace();
        }
    }
}

class Thread8_1 extends Thread{
    private int i = 0;
    @Override
    public void run(){
        try{
            while(true){
                System.out.println("i=" + (i++));
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}


