package com.mmr.learn.thread.lesson4;

import java.util.concurrent.TimeUnit;

/**
 * 脏读
 */
public class Test {
    /**
     * Thread.sleep()有两种情况:
     * 1. 非常短
     *        即便sleep的时间再短，只要调用了Thread.sleep，那么main线程的CPU资源就一定会被释放，也即threadA的run()方法一定会被执行。
     *        因此username = B
     *         getValue method thread name=main username=B password=AA
     *         setValue method thread name=Thread-0 username=B passwordBB
     * 2. 非常长
     *         setValue method thread name= Thread-0username=B passwordBB
     *         getValue method thread name=main username=B password=BB
     *
     *  虽然在赋值时使用了同步，但取值时可能会出现意想不到的情况。如上方情况1所示，虽然setValue()方法优先执行，但由于sleep()方法使当前main 线程暂停执行，
     *  再加上getValue()方法并没有同步，导致getValue()异步执行，取出的是不完全执行的setValue()方法的结果值。
     *
     *  出现脏读的关键在于多线程操作了实例变量，比如上述的对象publicVar，这就是多线程"争抢"实例变量的结果。
     *
     */
    public static void main(String[] args){
        try{
            PublicVar publicVar = new PublicVar();
            ThreadA threadA = new ThreadA(publicVar);
            threadA.start();
            TimeUnit.SECONDS.sleep(10);
            //TimeUnit.MILLISECONDS.sleep(200);
            publicVar.getValue();
        } catch (Exception e){
           e.printStackTrace();
        }
    }
}
