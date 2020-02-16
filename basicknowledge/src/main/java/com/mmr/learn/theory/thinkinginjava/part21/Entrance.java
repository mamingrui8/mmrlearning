package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * @author Charles Wesley
 * @date 2019/11/24 22:16
 */
public class Entrance {
    /*
        书本:
        1. LifeOff                  定义一个简单的任务(实现Runnable接口)
        2. MainThread               测试LifeOff
        3. BasicThreads Thread类    将Runnable对象转变为工作任务
        4. MoreBasicThreads         添加更多的线程去驱动更多的任务
        5. CacheThreadPool          使用Executor来代替在MoreBasicThread中显示地创建Thread对象。
        6. FixedThreadPool          使用FixedThreadPool代替CacheThreadPool来帮助我们管理和运行任务
        7. SingleThreadPool         可以看做是"线程数量为1的FixedThreadPool"
        8. TaskWithResult           定义一个实现了Callable接口的简单任务
        9. CallableDemo             介绍如何驱动实现了Callable接口的任务
        10.SleepTask                影响任务行为的方法——sleep() 休眠
        11.SimplePriorities         线程的优先级
        12.SimpleDaemon             后台线程 (创建的是显式的后台线程，以便设置它们的后台标志)
        13.DaemonThreadFactory      定制的ThreadFactory (可以用它来定制由Executor创建出的线程的各项属性！如"是否为后台线程"、"线程的名称"、""线程的优先级)
        14.DaemonFromFactory        通过ThreadFactory来初始化的Executors
        15.DaemonThreadPoolExecutor 专门用于创建后台线程的线程池
        16.DaemonsDontRunFinally    用于说明后台进程在不执行finally子句的情况下就会终止其run方法

        17.SimpleThread             [编码的变体]不用实现Runnable接口，也成为任务并被执行
        18.SelfManaged              [编码的变体]自管理的Runnable接口  (可能导致对象不稳定)

        19.InnerThread1              解决18中对象不稳定的方法——通过内部类将线程代码隐藏在类中  使用的是有名字的Thread
        20.InnerThread2              同上 使用的是匿名内部类 - Thread
        21.InnerRunnable1            同上 使用的是有名字的Runnable
        22.InnerRunnable2            同上 使用的是匿名内部类 - Runnable
        23.ThreadMethod              同上 用一个单独的方法来将线程代码隐藏在类中

        24.Joining                   join()的使用方式和案例
        25.ResponsiveUI              创建一个有响应的用户界面
        26.ExceptionThread           run()中没有捕获的异常，在线程调用处无法被捕获到。
        27.CaptureUncaughtException  捕获从run()中逃逸的异常
        28.SettingDefaultHandler     设置全局线程异常处理器

        -- 21.3 共享受限资源
        29.IntGenerator              包含了EvenChecker必须了解的不可缺少的方法:1. next() 2.可以执行的撤销方法
        30.evenChecker               偶数检查 任何IntGenerator都可以使用当前案例来测试、
        31.EvenGenerator             第一个IntGenerator实现方案——出现了共享资源竞争问题
        32.SynchronizedEvenGenerator 解决共享资源竞争问题——synchronized
        33.MutexEvenGenerator        解决共享资源竞争问题——使用显示的Lock对象
        34.AttemptLocking            Lock.lock()的使用场景
        35.AtomicityTest             滥用原子性概念的下场 【重点】
        36.SerialNumberGenerator     滥用原子性概念的下场  一个产生序列数字的类 (在非同步方法中使用volatile属性)
        37.SerialNumberChecker       测试SerialNumberGenerator，看看不加Synchronized，仅使用volatile，在多线程下，会产生怎样的后果。 (看得到，但取不到最新值)

        -- 21.3.4 原子类
        38.AtomicIntegerTest        使用AtomicInteger原子类来重写AtomicityTest类 (使用了模板方法模式)【重点】

        -- 21.3.5 临界区
        39.CriticalSection          1.比较将synchronized加载方法上与加载某一个代码块上的区别。2.如何把一个非保护类型的类，在其它类的控制和保护之下，应用于多线程的环境中。
        40.ExplicitCriticalSection     补充CriticalSection，使用显示的Lock对象来创建临界区

        -- 21.3.7 线程本地存储
        41.ThreadLocalVariableHolder

        -- 21.4 终止任务
        42.OrnamentalGarden         直接终止正在运行的任务
        43.

        -- 21.4 终结任务
        38.Interrupting              Executor基本的interrupt()方法
        39.CloseResource             解决因等待I/O操作导致系统锁死，但又因I/O操作不能中断的问题
        40.NIOInterruption           使用nio类来解决中断I/O的问题
        41.MultiLock                 同一个对象锁被任务多次获得
        42.Interrupting2             使用ReentrantLock代替synchronized，中断那些在synchronized上看似不能中断的操作！
        43.InterruptingIdiom         检查中断状态的惯用手法(interrupted)

        -- 21.5 线程之间的协作
        44.WaxOMatic
        45.NotifyVsNotifyAll         notify()和notifyAll()的区别
        46.Restaurant                "厨师与服务员"建模代码
        47.WaxOMatic2                重写WaxOMatic，使用Condition来挂起和唤醒任务
        wait()和notifyAll()有一个致命的问题，就是每次交互都需要握手(通知另一个任务是挂起,还是唤醒)
        48.TestBlockingQueues        BlockingQueue 同步队列更轻松的解决wait()和notify()面临的问题
        49.ToastOMatic               吐司BlockingQueue
        50.PipedIO                   任务之间通过管道进行通信(输入/输出)

        -- 哲学家问题
        51.Chopstick                 筷子
        52.Philosopher               哲学家
        53.DeadlockingDiningPhilosopher 哲学家问题-死锁版本
        54.FixedDiningPhilosophers   哲学家问题-防止死锁的版本

        -- 21.7 新类库中的构件
        55. CountDownLatchDemo       演示CountDownLatch   A任务执行完毕后才能执行B任务，否则B处于阻塞状态
        56. HorseRace                演示CyclicBarrier    A任务与B任务并行执行，A需要运行10秒，B需要运行15秒，使用CyclicBarrier
                                                         后，当时间过了10秒时，A任务进行阻塞状态，直到B执行完毕后，才一同结束。
        57. DelayQueueDemo           演示了DelayQueue延迟队列的用法 包括DelayQueue take() 和poll()
        58. PriorityBlockingQueueDemo
        59. GreenhouseScheduler      演示了SchedulerExecutor
        60. Pool,Fat,SemaphoreDemo   演示了Semaphore
        61. ExchangerDemo            演示了Exchanger

        -- 21.9 性能调优
        62.SimpleMicroBenchmark      微基准测试 (不准确、有缺陷)
        63.SynchronizationComparisons 并发性能测试 [重点]

        补充:
        1. ThreadShutDown            调用shutdown()命令后，处于悬挂队列中的任务还会得到执行吗？
        2. ThreadIOLock              任务获取到锁后，由于等待I/O操作被阻塞，那么锁会被释放吗？  答: 不会！
     */

    /*
        疑问:
        1. SingleThreadPool是如何维护它自己的悬挂任务队列(任务等待队列)？
        2. 若任务已经执行完毕了，那么驱动任务的线程还会存在吗？经过多久会被回收呢？
     */
    public static void main(String[] args) {

    }
}
