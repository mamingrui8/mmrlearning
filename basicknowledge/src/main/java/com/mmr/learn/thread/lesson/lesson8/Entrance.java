package com.mmr.learn.thread.lesson.lesson8;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 17:19
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
     *  synchronized可以应用在static静态方法上，如果这样写，那是对当前的*.java文件对应的Class类进行持锁。
     *
     *  案例一: 具体请看synStaticMethod
     *  运行结果:
     *      线程名称为: Thread-0 在 1550395738613 进入printA()
     *      线程名称为: Thread-0 在 1550395741615 离开printA()
     *      线程名称为: Thread-1 在 1550395741615 进入printB()
     *      线程名称为: Thread-1 在 1550395741616 离开printB()
     *  从运行的结果来看，synchronized加到static静态方法上是可以达到同步效果的。
     *  但从本质上来看，synchronized加到static静态方法上相当于给Class类上锁，而synchronized关键字加到非static静态方法上相当于给对象上锁。
     *
     *
     *  案例二: 为了验证不是同一把锁，我们创建新项目synctwolock
     *  运行结果:
     *      线程名称为: A 在 1550397103529 进入printA()
     *      线程名称为: C 在 1550397103530 进入printC()
     *      线程名称为: C 在 1550397103530 离开printC()
     *      线程名称为: A 在 1550397106532 离开printA()
     *      线程名称为: B 在 1550397106532 进入printB()
     *      线程名称为: B 在 1550397106532 离开printB()
     *  C线程异步的原因在于它持有的锁是Class锁，而Class锁可以对类的所有对象实例起作用
     *
     *  案例三: 为了验证"Class锁可以对类的所有对象实例起作用"，我们创建新项目synMoreObjectStaticOneLock
     *  运行结果:
     *      线程名称为: Thread-0 在 1550403783679 进入printA()
     *      线程名称为: Thread-0 在 1550403786679 离开printA()
     *      线程名称为: Thread-1 在 1550403786679 进入printB()
     *      线程名称为: Thread-1 在 1550403786680 离开printB()
     */
}
