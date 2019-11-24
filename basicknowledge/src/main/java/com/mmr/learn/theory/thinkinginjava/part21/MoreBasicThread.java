package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * 添加更多的线程去驱动更多的任务
 * @author Charles Wesley
 * @date 2019/11/24 23:18
 */
public class MoreBasicThread {
    public static void main(String[] args) {
        for(int i=0; i< 5; i++){
            new Thread(new LifeOff()).start();
        }
    }

    /*
        上述代码有一个非常有意思的地方: for循环的外层没有对Thread的任何引用，也就是说，当一轮循环完毕后，本轮中创建的Thread对象不会有任何的引用指向它。
        那么问题来了，GC是否会回收这些Thread对象呢？
        答案会，但必须等到Thread对象的run()方法执行完毕。每一个Thread都"注册"了它自己，因此确实有一个对它自己的引用，在run()方法执行完毕前，GC无论如何都没办法回收Thread对象。

        一个线程会创建一个单独的执行线程，在对start()方法调用完毕后，它仍旧会继续存在。
     */
}
