package com.mmr.learn.thread.lesson11;

/**
 * Description: 多线程死锁
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月18日 16:20
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
     * 不同的线程等待根本不可能被释放的锁，就会造成死锁。
     *
     * deadLockTest项目演示了死锁
     *     Step1: 执行deadLockTest项目
     *     进入cmd,执行jps，找出当前正在运行的java项目编号(pid值)
     *             14416
     *             2848 Launcher
     *             336
     *             20820 KotlinCompileDaemon
     *             20456 Run
     *             11692 Jps
     *      如上所示，当前项目的编号为20456
     *     Step2: 查看堆栈信息
     *            jstack -l 20456
     *            这时会打印一大堆信息，拖动到最下方
     *     Java stack information for the threads listed above:
     * ===================================================
     * "Thread-1":
     *         at com.mmr.learn.thread.lesson11.deadLockTest.DeadThread.run(DeadThread.java:43)
     *         - waiting to lock <0x000000076b7f6ca8> (a java.lang.Object)
     *         - locked <0x000000076b7f6cb8> (a java.lang.Object)
     *         at java.lang.Thread.run(Thread.java:745)
     * "Thread-0":
     *         at com.mmr.learn.thread.lesson11.deadLockTest.DeadThread.run(DeadThread.java:30)
     *         - waiting to lock <0x000000076b7f6cb8> (a java.lang.Object)
     *         - locked <0x000000076b7f6ca8> (a java.lang.Object)
     *         at java.lang.Thread.run(Thread.java:745)
     *
     * Found 1 deadlock.
     *
     *
     *  死锁是可以也是必须避免的程序bug，与嵌套不嵌套没有任何关系。只要相互等待对方释放锁就有可能出现死锁。
     */
}
