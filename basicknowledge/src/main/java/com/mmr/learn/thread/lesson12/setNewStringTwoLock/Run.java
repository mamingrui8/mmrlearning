package com.mmr.learn.thread.lesson12.setNewStringTwoLock;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月18日 22:40
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class Run {
    public static void main(String[] args) throws InterruptedException{
        MyService myService = new MyService();
        ThreadA a = new ThreadA(myService);
        a.setName("A");
        ThreadB b = new ThreadB(myService);
        b.setName("B");
        a.start();
        Thread.sleep(50);
        b.start();

        /**
         * 是否加上Thread.sleep(50)会导致极大地差距。
         *
         * 情况1: 不加Thread.sleep(50)
         *        那么线程A和B都会启动，由于传入的MyService是同一个对象，所以此时threadA和threadB的lock都等于"123"。
         *        接着，A先分配到CPU，运行run()方法。即便是运行到lock = "456"，也不会改变B线程持有的"钥匙(123)"。
         *        因此，线程B只能等线程A释放lock="456"的锁后，自己才能被执行。
         * 情况2: 加Thread.sleep(50)
         *        Thread.sleep(50)的功能: 停止当前执行的线程，也就是Thread.currentThread().sleep(50) 主线程停止50毫秒
         *        这就导致线程A执行完了
         *              lock = "456";
         *        并进入Thread.sleep(2000);的等待环节。
         *        【注意】可不要弄错了，ThreadA的锁仍然是"123"，只不过针对变量myService的私有成员变量lock的值变成了"456"
         *        因此，主线程休眠完毕后，执行b.start()使B线程成为可运行态时，B线程所持有的myService对象的lock值是"456"，和ThreadA锁定的"123"不是一把锁，当然就没必要争抢和排队了。
         *
         * 综上所述:
         *       在thread.start()之前，线程所持有的能力(如来自入参的成员变量myService等)是可以被修改的。
         *       但是当thread.start()执行完的那一刻起，线程所持有的能力会被冻结，也即不能发生改变。
         *       Thread.start()进入可执行状态的那一刻起，线程所持有的能力会被冻结(类似中断概念中的保存现场，是一份快照),绝对不能发生任何改变，
         *       因此如果没有加上Thread.sleep(50)，虽然传入的是同一个myService对象作为入参，即便ThreadA改变了lock的值，ThreadB的lock值仍然不受任何影响
         *       问题: 在什么时候会确定待执行的线程到底抢夺的是哪吧锁？
         *       猜测: Thread.start()，并且不会发生改变。
         */
    }
}
