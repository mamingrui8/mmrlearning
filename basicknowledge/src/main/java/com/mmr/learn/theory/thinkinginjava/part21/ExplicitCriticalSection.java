package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用显示的Lock对象来实现临界区
 * 书上代码写的有问题: ExplicitPairManager2运行后可能会报错，因为lock和加在PairManager getPair()方法上的锁很明显不是同一把锁(一个是synchronized产生的锁，另一个是ReentrantLock产生的锁)，
 *       也就是说，在ExplicitPairManager2执行完p.incrementX();但尚未执行p.incrementY();之前，另一个线程完全有可能执行PairManager getPair()方法
 *       由于y尚未自增，因此会报错x!=y。很奇怪书上是怎么执行成功的
 *
 * @author Charles Wesley
 * @date 2019/12/14 23:26
 */
public class ExplicitCriticalSection {
    public static void main(String[] args) throws Exception{
        PairManager pman2 = new ExplicitPairManager2();
        CriticalSection.testApproaches2(pman2);
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
    @Override
    public void increment() {
        Pair temp;
        boolean captured = lock.tryLock();
        try {
            if(captured) {
                p.incrementX();
                p.incrementY();
            }
            temp = getPair();
        }finally {
            if(captured) {
                lock.unlock();
            }
        }
        if(temp != null) {
            store(temp);
        }
    }
}