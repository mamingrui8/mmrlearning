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
     *      思考: 观察发现，无论是ThreadA还是ThreadB,它们的ThreadLocal的值在第一次调用时皆返回null  (无论为ThreadLocal添加的泛型是Date还是String)
     *            那么问题来了，如何才能给本线程的Threadlocal赋初值呢？   请看t4
     *
     *  t4 给ThreadLocal赋初值
     *      思考: 当我们继承了ThreadLocal并实现了initialValue()方法后，就够能为使用该ThreadLocal变量的线程赋予初值了。
     *
     *  t5 引入新概念---"值继承"
     *      思考: 从结果上来看，子线程中获取到的ThreadLocal的值是由父线程(main线程)初始化而来。
     *
     * ==========如果现在需要在父线程中创建子线程，但是子线程需要有自己的ThreadLocal(换句话说，需要覆盖父类的值了)，我们该怎么做==========
     *  t6 值继承再修改
     *      思考: 只需要覆盖InheritableThreadLocal中的childValue()方法就能实现值继承再修改的能力了。
     */
}
