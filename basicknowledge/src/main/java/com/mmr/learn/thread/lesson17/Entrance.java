package com.mmr.learn.thread.lesson17;

/**
 * Description: 类ThreadLocal的使用
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年03月01日 16:33
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
     *  [引入问题] 为什么要用ThreadLocal?
     *             答: 众所周知，类中的变量值可以通过public static的方式实现共享，所有的线程得以共享同一个变量的值。
     *                 但如果某个线程需要有自己的共享变量该怎么办呢？没关系，JDK提供了ThreadLocal！
     *                 ThreadLocal主要解决的是每个线程绑定自己的值，可以将ThreadLocal类比喻成全局存放数据的盒子，盒子中
     *                 可以存储每个线程的私有数据。
     *
     *  t1  方法get()与null()
     *      思考: t1演示了ThreadLocal存值和取值的过程
     *
     *  ==========ThreadLocal解决的是变量在不同线程之间的隔离性，也就是说，不同的线程会拥有自己的值。==========
     *
     *  t2 验证线程变量的隔离性
     *      思考：事实证明，虽然ThreadA,ThreadB以及main线程都在向Tools.t1中填入值，但是它们仍然可以取出自己的那部分数据。
     *
     *  t3 再次验证数据的隔离性
     *      思考:
     */
}
