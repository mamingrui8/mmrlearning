package com.mmr.learn.thread.lesson.lesson14.t10;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月26日 11:06
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class MyRun {
    private String lock = new String("");
    private boolean isFirstRun = false;
    private Runnable runnableA = new Runnable() {
        @Override
        public void run() {
            try{
                synchronized (lock){
                    while(!isFirstRun){
                        System.out.println("begin lock");
                        lock.wait();
                        System.out.println("end lock");
                    }
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    };

    private Runnable runnableB = new Runnable(){
        @Override
        public void run(){
            synchronized (lock){
                System.out.println("begin notify");
                lock.notify();
                System.out.println("end notify");
                isFirstRun = true;
            }
        }
    };

    public static void main(String[] args){
//        MyRun run = new MyRun();
//        Thread a = new Thread(run.runnableA);
//        a.start();
//        Thread b = new Thread(run.runnableB);
//        b.start();

        MyRun run = new MyRun();
        Thread b = new Thread(run.runnableB);
        b.start();

        Thread a = new Thread(run.runnableA);
        a.start();

    }
}
