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
     *
     */
}
