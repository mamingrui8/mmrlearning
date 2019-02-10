package com.mmr.learn.thread;

import java.util.concurrent.TimeUnit;

/**
 * Description: 线程停止
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月09日 13:24
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Thread5 {
    /**
     尽量避免使用Thread.stop()方法，  此方法是unsafe的
     一般采用Thread.interrupt()

     在java中，有以下方式终止正在运行的线程:
     1. 使用退出标志，使线程正常退出，也即当run()方法完成后线程终止。
     2. 使用stop方法强行终止线程，但绝不推荐使用此方法，因为stop和suspend,resume一样，都是过期作废的方法，使用它们可能会产生一些不可预知的后果。
     3. 使用interrupt方法中断线程。
     */
    public static void main(String[] args) {
        Thread5 test = new Thread5();
        test.test5();
    }

    /**
     * 停止不了的线程
     */
    public void test1(){
        Thread5_1 myThread = new Thread5_1();
        myThread.start();
        try {
            Thread.sleep(10000); //这样做没有意义， 即便写成myThread.sleep(2000), 停止的仍然是当前线程(也即main线程)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断线程是否处于停止状态
     * 方法1: this.interrupted()
     *        测试当前线程是否已经中断。   当前线程: 运行this.interrupted()方法的线程，test2()案例中即为main线程
     *        执行后，具有将中断标志位清除的功能
     * 方法2: this.isInterrupted()
     *        测试线程是否已经中断。       线程: start()方法启动的线程。test2()案例中即为Thread线程
     *        执行后，不具有将中断标志位清除的功能
     *
     * 二者源码上都调用了private native boolean isInterrupted(boolean ClearInterrupted);
     *
     * 纵观test2()，我们学习到了this.interrupted()，this.isInterrupted(),this.interrupt()，能够检查线程是否中断或对其进行中断，
     * 但遗憾的是，实际上，线程并没有停止下来。
     */
    public void test2(){
        Thread5_1 thread = new Thread5_1();
        thread.start();
        thread.interrupt();
        //Thread.currentThread().interrupt();
        System.out.println("Thread.interrupted() -> " + Thread.interrupted());  //true
        System.out.println("Thread.interrupted() -> " + Thread.interrupted());  //false
        /**
         * 这就非常奇怪了，为何同样是Thread.interrupted()方法，同样是由main主线程调用， 前者输出true但后者却输出false呢？
         * 这就要谈到Thread.interrupted()
         * 以下是官方SDK的解释
         * Tests whether the current thread has been interrupted. The interrupted status of the thread is cleared by this method. In other words,
         * if this method were to be called twice in succession, the second call would return false
         * (unless the current thread were interrupted again, after the first call had cleared its interrupted status and before the second call had examined it).
         * 解释的很详细了，interrupted()方法具有清除中断状态的功能，所以第二次调用inerrupted()方法返回的是false
         */


        System.out.println("thread.isInterrupted()->" + thread.isInterrupted());//false
        System.out.println("thread.isInterrupted()->" + thread.isInterrupted());//fasle
        /**
         *  thread.isInterrupted()方法不会清除中断状态
         */
    }

    /**
     * 到底该如何停止线程呢？ test3()给出答案
     *
     * 有了test1()和test2()的知识后，我们可以在线程中用for语句循环来判断线程是否停止的状态，如果停止，则不运行后续的代码即可
     */
    public void test3(){
        try {
            Thread5_2 thread = new Thread5_2();
            thread.start();
            TimeUnit.SECONDS.sleep(2);
            thread.interrupt();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主线程退出");
    }

    /**
     * test3()的补充。   既停止了for循环内部的代码，也停止了for循环外部的代码
     */
    public void test3_1(){
        try {
            Thread5_3 thread = new Thread5_3();
            thread.start();
            TimeUnit.SECONDS.sleep(2);
            thread.interrupt();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主线程退出");
    }

    /**
     * ============
     * 思考: test3()和test3_1()在本质上并没有停止线程，而是借助标志位(interrupted),通过逻辑控制(isInterrutped())来抛出异常或break代码。
     * ============
     */


    /**
     * 在沉睡中停止
     *
     * 如果一个线程在sleep()的状态下停止线程，该中断状态将被清除
     *
     */
    public void test4(){
        try{
            Thread5_4 thread = new Thread5_4();
            thread.start();
            Thread.sleep(200);
            thread.interrupt();
        } catch (InterruptedException e){
            System.out.println("main catch");
           e.printStackTrace();
        }
        System.out.println("end!");
    }

    /**
     * 暴力停止线程
     * stop()
     *
     * 方法stop()已经被作废，因为如果强制性使用stop停止线程，可能会导致某些清理性的工作得不到完成。
     * 此外，stop()会导致被线程锁定的对象"解锁"，导致数据得不到同步的处理，出现数据不一致的后果。
     */
    public void test5(){
        Thread5_5 thread5_5 = new Thread5_5();
        thread5_5.start();
    }

}


class Thread5_1 extends Thread{
    @Override
    public void run(){
        super.run();
        for(int i=0; i< 500000; i++){
            System.out.println("thread->i=" + (i+1));
        }
    }
}

/**
 * Thread5_2虽然能够停止for循环内的语句执行，但run()方法内部，for循环外部的语句却没能中断。 解决办法请看Thread5_3
 */
class Thread5_2 extends Thread{
    @Override
    public void run(){
        super.run();
        for(int i=0; i< 5000000; i++){
            if(this.isInterrupted()){ //若Thread5_2线程本身中断了，则不运行下述代码
                System.out.println("Thread5_2.getName()->" + this.getName() + "已经中断了，我要退出了！");
                break;
            }
            System.out.println("thread->i=" + (i+1));
        }

        System.out.println("虽然Thread5_2已经停止，但本语句仍然能够执行");
    }
}

class Thread5_3 extends Thread{
    @Override
    public void run(){
        super.run();
        try{
            for(int i=0; i< 5000000; i++){
                if(this.isInterrupted()){ //若Thread5_2线程本身中断了，则不运行下述代码
                    System.out.println("Thread5_2.getName()->" + this.getName() + "已经中断了，我要退出了！");
                    throw new InterruptedException();
                }
                System.out.println("thread->i=" + (i+1));
            }
            System.out.println("Thread5_2已经停止，本语句无法执行");
        }catch(InterruptedException e){
            System.out.println("进入了Thread5_3.java类的run方法中的catch()方法了");
            e.printStackTrace();
        }
    }
}

class Thread5_4 extends Thread{
    @Override
    public void run(){
        super.run();
        try{
            System.out.println("run begin...");
            TimeUnit.MINUTES.sleep(1);
            System.out.println("run end...");
        } catch (InterruptedException e){
            System.out.println("在沉睡中被停止！ 进入catch!" + this.isInterrupted()); //false  因为是在sleep状态下interrupt
           e.printStackTrace();
        }
    }
}

class Thread5_5 extends Thread{
    @Override
    public void run(){
        try{
            this.stop();
        }catch (ThreadDeath e){ //此异常不需要显示的捕获
            System.out.println("进入了catch()方法");
            e.printStackTrace();
        }
    }
}
