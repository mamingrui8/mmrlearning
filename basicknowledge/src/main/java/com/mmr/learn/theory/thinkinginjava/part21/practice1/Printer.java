package com.mmr.learn.theory.thinkinginjava.part21.practice1;

/**
 * @author Charles Wesley
 * @date 2019/11/24 23:36
 */
public class Printer implements Runnable{
    private static Integer count = 0;
    private final Integer id = count++;

    @Override
    public void run() {
        for(int i=0; i< 3; i++){
            System.out.print("Thread id:" + id + " i:" + i + ";   ");
            Thread.yield();
        }
        System.out.println();
        System.out.println("Printer end. id->" + id);
        System.out.println();
    }

    public Printer(){
        System.out.println("Printer start. id->" + id);
    }
}
