package com.mmr.learn.first;

/**
 * Description: 多线程基础学习
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月16日 10:49
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class BasicThread {
    /**
     * 参考:
     * 1. https://www.cnblogs.com/wxd0108/p/5479442.html  (多线程详解)
     * 2. https://blog.csdn.net/weixin_41101173/article/details/79679300 (线程阻塞讲解)
     *
     * <上下文的概念>
     * 一段程序需要正常执行，除了CPU以外的所有需求构成了这段程序的上下文环境。
     *
     * <进程和线程的概念>
     * 进程和线程都是一个【时间片段】的概念，他们都是CPU工作时间段的描述，只不过:
     * 1. 进程: 包含上下文切换的程序执行时间的总和
     * 2. 线程: 由于进程的颗粒度太大，每次执行都会有上下文切换、CPU调入、CPU保存以及CPU调出。
     *          因此，线程是颗粒度更小，将进程切分成多段，共享进程上下文环境的CPU时间片段。
     *    特别的:
     *          1. 进程是资源分配的最小单位，线程是资源调度的最小单位。 ===》 线程不配拥有资源，它们只能访问隶属进程的资源。
     *          2. 线程有自己的堆和栈以及局部变量。(堆栈的讲解放到了 com.mmr.jvm中)
     *
     * <并行和并发的概念>
     *  1. 并行(parallel programming): 多个CPU实例(多核嘛)或者多台机器同时执行一段处理逻辑。  真正意义上的同时处理。
     *  2. 并发(concurrent programming): 通过CPU调度算法，让用户看上去同时执行。实际上从cpu操作层面不是真正的同时。
     *                                   并发往往在场景中有公用的资源，那么针对这个公用的资源往往产生瓶颈，我们会用TPS或者QPS来反应这个系统的处理能力。
     *                                   QPS(TPS): 每秒钟请求/事务的数量
     * <线程的状态>
     *  new，runnable,blocked,time_waiting,terminated
     *  1. New -> Runnable: start()
     *  2. Runnable -> Running : 获取CPU
     *  3. Running -> Runnable:  yield()
     *  4. yield(): 温柔的暂停当前执行的线程，但并没有立即释放CPU，让CPU不再为其分配时间片段。
     *              特别的: yield()充其量只是个建议而已，它的作用是告诉JVM线程调度器当前线程的工作做的差不多了，可以让CPU考虑为其它相同优先级的线程分片CPU时间片段了。
     *                      并非强制释放CPU资源！  我个人把其称作 "gracefullyStop"
     *  5. Running -> Blocked: 释放锁  之所以分配CPU执行该线程，是因为当前线程拿到了锁。如果释放掉锁，线程自然从Running -> Blocked
     *
     *
     * <实现线程阻塞的方法>
     *  前提: 为什么要引入线程阻塞机制?
     *        答:  共享存储区只有一块，线程有多个，极有可能引发抢夺资源、资源尚未准备好线程就想来使用等问题。
     *  概念: 暂停一个正在运行的线程以等待某个条件的发生(比如某个资源就绪)
     *  实现方法:
     *  1. 线程睡眠
     *     Thread.sleep(long mills) <===> TimeUnit.SECONDS.sleep(long timeout)
     *     此方法会抛出一个java.lang.InterruptedException
     *     睡眠结束后，线程将被转为Runnable(就绪态)
     *  2. 线程等待
     *     Object类的wait()方法  当且仅当其他线程(包含主线程)调用此对象的notify()方法才能唤醒该线程。
     *     这里的wait()有两种形式:
     *                           1. wait(long mills)  超出等待时间后，将自动转换成Runnable(就绪态)
     *                           2. wait()            很可惜，此方法不能设置等待时间，只能默默等待有其它线程调用此对象的notify()方法唤醒该线程。
     *  3. 线程礼让(经典)
     *     Thread.yield()
     *     (请求)暂停当前正在执行的线程对象，很绅士的把执行的机会让给相同或者更高优先级的线程。
     *     yield()虽然会使线程放弃当前分得的CPU时间，但是不会使线程阻塞，也即被执行yield()的线程会进入Runnable()状态，随时可以再次被调用
     *
     *  4. 线程自闭
     *     Thread.join()
     *     等待其他线程终止。在当前线程中调用另一个线程的join()方法时，当前线程立刻进入block，直至另一个线程执行完毕并放弃持有CPU，当前线程才会再由block转换成Runnable()
     *
     *  5. [jdk1.5后已废除] suspend()和resume()方法。
     *     成对使用
     *     suspend()使线程进入阻塞状态，并且绝不会自动恢复，必须其对应的resume()被调用，才可以使线程重新进入Runnable。一般用于等待另一个线程产生结果时使用。比如线程A需要等待线程B产生结果，因此
     *     A先进行suspend()，接着等到B产生结果后，再对A调用resume()。
     *     但致命问题在于: 如果B一直不产生结果，则A永远也无法被唤醒。
     *
     *     Why Are Thread.stop, Thread.suspend, Thread.resume and Runtime.runFinalizersOnExit Deprecated?
     *     Because it is inherently unsafe. Stopping a thread causes it to unlock all the monitors that it has locked.
     *     (The monitors are unlocked as the ThreadDeath exception propagates up the stack.)
     *     If any of the objects previously protected by these monitors were in an inconsistent state, other threads may now view these objects in an inconsistent state.
     *     Such objects are said to be damaged. When threads operate on damaged objects, arbitrary behavior can result.
     *     This behavior may be subtle and difficult to detect, or it may be pronounced.
     *     Unlike other unchecked exceptions, ThreadDeath kills threads silently; thus, the user has no warning that his program may be corrupted.
     *     The corruption can manifest itself at any time after the actual damage occurs, even hours or days in the future.
     *
     */

    public static void main(String[] args){

    }
}
