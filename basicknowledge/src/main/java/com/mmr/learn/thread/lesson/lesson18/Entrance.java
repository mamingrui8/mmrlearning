package com.mmr.learn.thread.lesson.lesson18;

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
     *     condition.await(long time, TimeUnit time)   等待指定时间后继续执行后续代码。
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
     *          概念: 如果当前线程未被"中断"，则获取锁定，如果已经被"中断"，则出现异常。
     *          思考: 请注意，与lock.lockInterruptibly()不同的是，lock.lock()并不会受interrupt()的影响，哪怕线程被标记上了interrupt() = true， lock.lock()也不会抛出异常，而是像往常一样继续执行。
     *               实际上，thread.interrupt()并没有使线程立即停止下来的能力，甚至根本就没有停止线程的能力，只不过lock.lockInterruptibly()在执行时会去检查线程的中断标志位，而lock.lock()没有检查而已。
     *     void tryLock()
     *          概念: 与lock.lock()非常类似，tryLock()同样是用于获取锁定的。
     *                与lock.lock()不同的是，当该监视器对象被其他线程锁定时，tryLock()不会阻塞，而是立刻返回false 【Tips: lock.lock()在这种情况时会 阻塞，进入锁的等待队列中】
     *     void tryLock(long timeout, TimeUnit unit)
     *          概念: 如果在给定的时间内，另一个线程没有保持住，若调用tryLock()的线程尚未中断，则获取该锁定。
     *          思考: 这里使用interruput()看看 书上说的"中断"指的是真正的线程中断，还是只是个标志位而已。
     *                以下以thread.tryLock(3)为例
     *                1. 实验发现，tryLock(3)意思是在3秒钟内监控锁，若锁被其它线程释放，则立刻获得该锁。
     *                   若锁被其它线程持有，则在这3秒钟之内持续监控，3秒后则返回false
     *                2. thread.tryLock()同样会受到interrrupt()的影响，直接抛出java.lang.InterruptedException！！！
     *     总结:
     *           1. lock.tryLock()的好处显而易见，有它在，线程不再像lock.lock()那样盲目的等待，而是聪明的、在恰当的时间段退出锁的争夺。
     *              lockInterruptibly()也是lock.lock()的升级版。
     *           2. 上述三兄弟都有一个共同的特点——被interrupt()标志位打断，都会抛出java.lang.InterrupedException异常
     *           3. 关于线程的中断方式:
     *              1. 若在锁的内部，我们可以通过interrupt()标志位的方式中断线程的执行。
     *              2. 若在锁的外部，我们可以通过lockInterruptibly(),tryLock或是tryLock()的方式，不让指定线程执行run()
     *
     *  t13 方法awaitUninterruptibly()
     *          概念: 普通的condition.await() 会受到interrupt()的影响，抛出java.lang.InterruptedException异常
     *                但awaitUninterruptibly()则不会。
     *
     *  t14. 方法awaitUntil(Date date)
     *          概念:  使线程等待指定时间后自动苏醒以便继续执行代码，当然了，在等待中线程可以被主动唤醒。
     *          思考:  与await(long time, TimeUnit unit)进行比较:
     *                 相同之处:
     *                           1. 都会等待，等待结束后进入Runnable状态，如果锁未被其它线程持有，则立刻获得锁并执行后续代码。 如果不幸被其它线程持有，则抛出java.lang.InterruptedExpection异常、
     *                           2. 都能被condition.signal()或condition.signalAll()提前唤醒。
     *                 不同之处: await(long time, TimeUnit unit)等待的是指定的时长，而awaitUntil(Date date)等待的是到指定的时刻。
     *
     *  t15. 使Condition实现顺序执行
     *          概念: 使用Condition可以对多条线程执行不同业务进行排序，按照指定顺序执行。
     *          思考:
     *                1. Condition.await和signal() 能够唤醒指定系列的Condition等待，只接收指定系列的唤醒。
     *                2. ReentrantLock在线程执行时是绝对唯一的，也即，同一时刻只有一个线程持有锁。
     *                正是上述两个重要的特点，使得使用Condition对多条线程不同业务顺序执行成为了可能！
     *
     *   ========================================
     *   由于ReentrantLock具有完全互斥排他的效果，因此同一时间只会有一条线程执行ReentrantLock.lock()方法后面的任务。
     *   这样做虽然保证了实例变量的线程安全性，但执行效率非常低下。
     *   在JDK中提供了一种读写锁ReentrantReadWriteLock类，使用它可以加快运行速率，在某些不需要操作实例变量的方法中，完全可以使用
     *   读写锁ReentrantReadWriteLock来提升该方法的执行速度。
     *
     *   读写锁有表示也有两个锁:
     *   1. 读操作相关的锁，也称作共享锁。
     *   2. 写操作相关的锁，也称作排他锁。
     *
     *   多个读锁之间不互斥，读锁和写锁互斥，写锁和写锁互斥。
     *   ========================================
     *
     *   t16 类ReentrantReadWriteLock的使用: 【读-读共享】
     *       执行结果如下:
     *                  获得读锁 B 1552360638803
     *                  获得读锁 A 1552360638804
     *       思考:
     *             ThreadA和ThreadB这两个线程几乎同时获得锁，执行ReentrantReadWriteLock.lock()后方的代码，极大的提升了程序运行的效率。
     *
     *   t17 类ReentrantReadWriteLock的使用: 【写-写互斥】
     *       执行结果如下:
     *                  获得写锁 A 1552361008460
     *                  获得写锁 B 1552361018468
     *       思考:
     *             使用ReentrantReadWriteLock.lock()就是同一时间只允许一个线程执行lock()后面的代码。
     *
     *   t18 ReentrantReadWriteLock的使用: 【读-写互斥】
     *       执行结果如下:
     *                  获得读锁 A 1552361588385
     *                  获得写锁 B 1552361598391
     *       思考:
     *             1. 此实验说明读写操作互斥
     *             2. 只要出现了"写"操作，那就一定是互斥的
     */
    public static void main(String[] args) {

    }
}
