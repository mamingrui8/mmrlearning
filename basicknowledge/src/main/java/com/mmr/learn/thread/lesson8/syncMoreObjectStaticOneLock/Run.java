package com.mmr.learn.thread.lesson8.syncMoreObjectStaticOneLock;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 17:57
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args) {
        /*
            虽然service1和service2是两个不同的对象，但它们同属于Service类，而持有锁的是Service类，因此synchronized static修饰的方法必须保持同步。
         */
        Service service1 = new Service();
        Service service2 = new Service();
        ThreadA threadA = new ThreadA(service1);
        threadA.start();
        ThreadB threadB = new ThreadB(service2);
        threadB.start();
    }
}
