package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用显示的Lock重写SynchronizedEvenGenerator 解决竞争共享资源的问题
 * 使用Lock.lock()和unlock()方法在next()内部创建了临界资源
 * @author Charles Wesley
 * @date 2019/12/8 23:05
 */
public class MutexEvenGenerator extends IntGenerator{
    private int currentEventValue = 0;
    private Lock lock = new ReentrantLock();
    @Override
    public int next() {
        lock.lock();
        try {
            currentEventValue++;
            //更快速的暴露出竞争共享资源的问题
            Thread.yield();
            currentEventValue++;
            return currentEventValue;
        }finally {
            lock.unlock();
        }
        //必须把return放在try/finally代码块内，这是为了防止当前线程执行完try块代码，但尚未返回next()调用处时，有其它线程进入try块，修改了currentEventValue的值
        //这就会导致当前线程的currentEventValue不准确。
        //return currentEventValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new MutexEvenGenerator());
    }
}
