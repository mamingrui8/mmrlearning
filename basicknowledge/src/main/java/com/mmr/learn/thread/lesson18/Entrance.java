package com.mmr.learn.thread.lesson18;

public class Entrance {
    /**
     *  前面谈到，synchronized可以实现线程之间的同步与互斥，但在JDK1.5中新增加了ReentrantLock类也能达到同样的效果，并且在扩展功能上也更加强大，
     *  比如有"嗅探锁定"、"多路分支通知"等功能。
     *
     *  t1 ReentrantLock入门
     *     思考:
     *          1. 当线程获取到锁时，其它线程无法获取锁，当且仅当线程释放锁时，其它线程才得以执行，继续打印数据。
     *          2. 数据是分组打印的，因为当前线程已经持有锁。但线程与线程之间的执行是随机的。
     *  t2 入门案例2
     *     思考: 调用了lock.lock()方法的线程就持有了该对象的监视器 ，其它线程只有等待锁被释放时再次争抢。
     *
     *  =====关键字synchronized与wait()和notify()/notifyAll()方法相结合可以实现等待/通知模式，那么RenntrantLock也可以实现同样的功能吗？=====
     *       当然是可以的！但是要借助Condition对象
     *       分析:
     *             在使用notify/notifyAll()方法进行通知时，被通知的线程是由JVM随机选择的。但使用ReentrantLock结合Condition类是可以实现"选择性通知"的。(这个功能非常重要，而且在Condition中是默认提供的)
     *             synchronized就相当于整个Lock对象中只有一个单一的Condition对象，所有的线程都注册在它这一个对象上。这就造成线程开始notifyAll()时，需要通知所有处于waiting状态的线程，没有选择权，会出现相当大效率问题。
     *
     *  t3 Condition的初步使用方式
     *     思考: 在lock.lock()拿到对象监视器后，由于调用了Condition.await()方法，当前线程(ThreadA)进入了WAITING状态。
     *
     *  t4 使用单个Condition实现等待/通知
     *     思考: await()和signal()中的lock.lock();针对的是同一把锁，也即同一个对象监视器，因此当signal()执行后，处于await()状态下的线程将得以唤醒、
     *           总结:
     *                1. Object类中的wait()方法 <=======> Condition类中的await()
     *                2. Object类中的wait(long times)方法 <=======> Condition类中的await(long time, TimeUnit unit)
     *                3. Object类中的notify()方法 <=======> Condition类中的signal()
     *                4. Object类中的notifyAll()方法 <=======> Condition类 中的signalAll()
     *
     *  t5 使用多个Condition实现通知部分线程
     *     思考: 从编程角度来看，一把lock可以对应多个Condition。 多个Condition之间相互独立
     *
     *  t6 实现生产者/消费者模式    一对一交替打印数据
     *     思考: 通过这个例子后，我得出以下推断:
     *           1. lock本身不是一把this锁，它就好像一把非this对象的同步锁。 若写成private Lock lock 作为类的成员变量，则这把锁定位在对象层面上，同一个对象享有同一把lock。
     *
     * ==========公平锁与非公平锁==========
     *     锁Lock被分为"公平锁"和"非公平锁"
     *     1. 公平锁:   线程获取锁的顺序是按照线程加锁的顺序来分配的，也即先来先得(FTFO)、先进先出顺序。
     *     2. 非公平锁: 一种锁的抢占机制，随机获得锁。先加锁的不一定先获取到锁。这个方式可能造成某些线程一直拿不到锁，结果也就是不公平的了。
     *
     *  t8 公平锁
     *     思考: 观察执行结果发现，线程都会陆续执行，并且基本呈有序状态状态。(如线程0先被CPU调用了run(),那么线程0一定优先获得锁)
     *              ☆线程: Thread-0 运行了
     *              ☆线程: Thread-2 运行了
     *              ☆线程: Thread-9 运行了
     *              ☆线程: Thread-1 运行了
     *              ☆线程: Thread-8 运行了
     *              ☆线程: Thread-5 运行了
     *              ☆线程: Thread-3 运行了
     *              ☆线程: Thread-4 运行了
     *              ☆线程: Thread-7 运行了
     *              ThreadName=Thread-0 获得锁定
     *              ☆线程: Thread-6 运行了
     *              ThreadName=Thread-2 获得锁定
     *              ThreadName=Thread-9 获得锁定
     *              ThreadName=Thread-1 获得锁定
     *              ThreadName=Thread-8 获得锁定
     *              ThreadName=Thread-5 获得锁定
     *              ThreadName=Thread-3 获得锁定
     *              ThreadName=Thread-4 获得锁定
     *              ThreadName=Thread-7 获得锁定
     *              ThreadName=Thread-6 获得锁定
     *
     *     非公平锁 并非是先被CPU调用run()的线程就先获得锁。
     *     测试时只需在t8项目RunFair中，向Service构造函数传入false即可。
     *     实际上就是 ReentrantLock lock = new ReentrantLock(false);
     *
     *  t9 方法getHoldCount()、getQueueLength()以及getWaitQueueLength()
     *     lock.getHoldCount()
     *          概念: 线程针对同一把锁尝试获取锁的次数，也即调用lock.lock();的次数
     *     lock.getQueueLength()
     *          概念: 返回等待获取某把锁的线程个数。
     *     lock.getWaitQueueLength()
     *          概念: 返回被某个Condition wait()阻塞的线程个数
     *
     *  t10 方法hasQueuedThread()、hasQueuedThreads()以及hasWaiters()
     *     lock.hasQueuedThread(Thread thread)
     *          概念: 查询指定的线程是否正在等待获取此锁定
     *     lock.hasQueuedThreads(Thread thread)
     *          概念: 查询是否有线程正在等待获取此锁定
     *     lock.hasWaiters(Thread thread)
     *          概念: 是否有线程正在等待与此锁定有关的Condition条件
     *
     *  t11 方法isFair()、isHeldByCurrentThread()以及isLocked()
     *     boolean isFair()
     *          概念: 判断该锁是不是公平锁
     *     boolean isHeldByCurrentThread()
     *          概念: 判断当前线程是否保持此锁定
     *     boolean isLocked()
     *          概念: 判断该锁是否被任意线程持有
     *
     *  t12 方法lockInterruptibly()、tryLock()以及tryLock(long timeout, TImeUnit unit)
     *     void lockInterruptibly()
     *          概念: 如果当前线程未被中断，则获取锁定，如果已经被中断，则出现异常。
     *          思考: 请注意，lock.lock()并不会使线程中断，哪怕当前线程被执行了interrupt()
     *               实际上，thread.interrupt()并没有使线程立即停止下来的能力，甚至根本就没有停止线程的能力，只不过lock.lockInterruptibly()在执行时会去检查线程的中断标志位，而lock.lock()没有检查而已。
     */
    public static void main(String[] args) {

    }
}
