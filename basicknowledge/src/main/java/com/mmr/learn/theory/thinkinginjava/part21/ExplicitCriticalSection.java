package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用显示的Lock对象来实现临界区
 * 疑问: ExplicitPairManager2运行后可能会报错，因为lock和加在PairManager getPair()方法上的锁很明显不是同一把锁，
 *       也就是说，在ExplicitPairManager2执行完p.incrementX();但尚未执行p.incrementY();之前，另一个线程完全有可能执行PairManager getPair()方法
 *       由于y尚未自增，因此会报错x!=y。很奇怪书上是怎么执行成功的
 *
 * @author Charles Wesley
 * @date 2019/12/14 23:26
 */
public class ExplicitCriticalSection {
    public static void main(String[] args) throws Exception{
        PairManager pman1 = new ExplicitPairManager1(),
                pman2 = new ExplicitPairManager2();
        CriticalSection.testApproaches(pman1, pman2);
    }
}

class ExplicitPairManager1 extends PairManager{
    private Lock lock = new ReentrantLock();
    @Override
    public synchronized void increment() {
        lock.lock();
        try {
            p.incrementX();
            p.incrementY();
            store(getPair());
        }finally {
            lock.unlock();
        }
    }
}

class ExplicitPairManager2 extends PairManager {
    private ReentrantLock lock = new ReentrantLock();
    @Override
    public void increment() {
        Pair temp;
        boolean captured = lock.tryLock();
        try {
            if(captured){
                System.out.println("成功创建临界区...");
            }
            p.incrementX();
            p.incrementY();
            temp = getPair();
        }finally {
            lock.unlock();
            System.out.println("离开临界区...");
        }
        store(temp);
    }
}