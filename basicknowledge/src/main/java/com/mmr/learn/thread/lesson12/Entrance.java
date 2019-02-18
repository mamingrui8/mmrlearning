package com.mmr.learn.thread.lesson12;

/**
 * Description:  内置类与静态内置类
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月18日 16:41
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
     * innerClass 内置类
     *      核心: PrivateClass privateClass = publicClass.new PrivateClass();
     *      即内置类的初始化依赖外部类对象
     *      结果:
     *             usernameValue passwordValue
     *             ageValue addressValue
     * innerStaticClass 静态内置类
     *      核心: 静态内置类并不属于某个对象，而是整个类，因此
     *      PrivateClass privateClass = new PrivateClass();
     *      或
     *      PublicClass.PrivateClass privateClass = new PublicClass.PrivateClass();
     *      都可以初始化
     *      结果:
     *             usernameValue passwordValue
     *             ageValue addressValue
     *  innerTest1 内置类与同步 - 实验1
     *      由于一把锁是String类型的对象锁，还有一把锁定义在成员方法上，也就是this对象锁。
     *      持有不同的"对象监视器"，所以异步执行。
     *  innerTest2 内置类与同步 - 实验2
     *      由于InnerClass1的method1的锁是InnerClass2对象，与InnerClass2的method1产生冲突，因此后者一定在前者执行完毕才能开始运行。
     *      而InnerClass1的method2的锁是对象锁，也就是InnerClass1对象持有，与其他两把锁互不干扰，因此异步执行。
     *
     * setNewStringTwoLock 锁对象的改变
     *      现在看来，锁不一定会放在某个方法、某个类上，它还可以是某个完全不相干的对象。
     *      因此，理论上来说，任何数据类型都可以作为同步锁。但需要注意的是，是否有多个线程同时持有锁对象非常关键。
     *      有以下两种情况:
     *      1. 如果同时持有相同的锁对象，则这些线程之间就是同步的。
     *      2. 如果分别获得锁对象，则这些线程之间就是异步的。
     *
     *      此例要详细说明:
     *      具体请看com.mmr.learn.thread.lesson12.setNewStringTwoLock.Run类
     */
}
