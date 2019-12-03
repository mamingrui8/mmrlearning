package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * 不用实现Runnable接口，也成为任务并被执行
 * @author Charles Wesley
 * @date 2019/12/3 23:49
 */
public class SimpleThread extends Thread {
    private int countDown = 5;
    private static int threadCount = 0;

    public SimpleThread() {
        //存储/设置线程的名称
        //通过调用适当的Thread构造器，为Thread对象赋予具体的名称
        super(Integer.toString(++threadCount));
        start();
    }

    public String toString() {
        return "#" + getName() + "(" + countDown + "). ";
    }

    @Override
    public void run(){
        for(;;) {
            System.out.println(this);
            if(--countDown ==0) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 5; i++) {
            new SimpleThread();
        }
    }
}

