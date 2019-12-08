package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * 通过使用synchronized，解决竞争共享同步资源的问题
 * @author Charles Wesley
 * @date 2019/12/8 22:07
 */
public class SynchronizedEvenGenerator extends IntGenerator{
    //currentEvenValue是一个共享资源
    private int currentEvenValue = 0;

    /**
     * 由于加上了synchronized，使得当前代码块变成了同步代码块看，也就是临界区
     * 对象锁的互斥性可以防止多个任务同时进入临界区
     * 通过这种方式，任何时刻只有一个线程可以执行由互斥量看护的代码
     */
    @Override
    public synchronized int next() {
        currentEvenValue++;
        //就算建议线程调度器，让出当前线程的CPU资源，线程调度器也不敢让其它线程运行当前next()方法，因为当前线程尚未释放锁。
        Thread.yield();
        currentEvenValue++;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new SynchronizedEvenGenerator());
    }
}
