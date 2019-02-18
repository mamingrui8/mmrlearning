package com.mmr.learn.thread;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
     * 3.《Java多线程编程核心技术》 P121
     *
     * [lesson1]
     * Thread7 --- 线程优先级
     * Thread8 --- 守护线程
     *
     * lesson2 多个对象多把锁问题
     * lesson3 多线程访问同一个共享资源的同步或非同步方法
     * lesson4 脏读
     * lesson5 Synchronized锁重入
     *         出现异常时，当前线程持有的锁会被自动释放掉
     *         同步不具有继承性
     * lesson6 synchronized 同步语句块
     * lesson7 将任意的对象作为对象监视器
     * lesson8 静态同步synchronized方法与synchronized(class)代码块
     * lesson9 String类型的常量池特性  (★★★)
     * lesson10 同步方法引发的"无限等待"问题
     * lesson11 多线程死锁
     * lesson12 内置类与静态内置类(★★★) [涉及"内置类"和"静态内置类"]
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
     *     不会释放锁
     *  2. 线程等待
     *     Object类的wait()方法  当且仅当其他线程(包含主线程)调用此对象的notify()方法才能唤醒该线程。
     *     这里的wait()有两种形式:
     *                           1. wait(long mills)  超出等待时间后，将自动转换成Runnable(就绪态)
     *                           2. wait()            很可惜，此方法不能设置等待时间，只能默默等待有其它线程调用此对象的notify()方法唤醒该线程。
     *  3. 线程礼让(经典)
     *     Thread.yield()  具体请看Thread6.java
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
     *     综上所示，释放掉监控器可能会导致受监控器保护的某些对象死亡，一旦其它线程继续调用这些对象，将会导致不可预知的后果。
     *
     *  常用名词:
     *  1. 主线程: JVM调用程序main()所产生的线程
     *  2. 当前线程: 通过Thread.currentThread()来获取的线程
     *  3. 后台线程：指为其他线程提供服务的线程，也称为守护线程。JVM的垃圾回收线程就是一个后台线程。用户线程和守护线程的区别在于，是否等待主线程依赖于主线程结束而结束
     *  4. 前台线程: 是指接受后台线程服务的线程，其实前台后台线程是联系在一起，就像傀儡和幕后操纵者一样的关系。傀儡是前台线程、幕后操纵者是后台线程。
     *               由前台线程创建的线程默认也是前台线程。可以通过isDaemon()和setDaemon()方法来判断和设置一个线程是否为后台线程。
     *  5. 系统空进程: System Idle Process 系统空闲进程    CPU对其发出IDLE指令，使该进程占用的CPU挂起，减少CPU使用率。  当然，一旦应用程序有所请求，该进程将立刻分配到CPU并运行
     *  6. 可见进程:  一些不在前台，但用户依然可见的进程，举例来说：各种widget、输入法等，都属于visibe。这部分进程虽然不在前台，但与我们的使用也是密切相关，我们并不希望它被系统终止。
     *
     *     "前台可见进程服务于后台空进程"
     *     我对这句话的理解: 为了使CPU总体占用率、温度降低，windows针对大型的进程进行了CPU挂起处理，也就是让其进入空进程状态。但该进程需要做的事情不能没人做，因此分配了一部分前台
     *                       可见进程去做。
     *
     *     重要性依次递减即，前台进程>可见进程>服务进程>后台进程>空进程。
     *
     * Jvm处理Java Thread 的run方法中抛出异常的流程:
     *  被调用的方法在定义的时候就存在throws关键字，这种被抛出的异常，在Thread的run方法中，只能被try-catch语句块捕获，因为run方法本身是没有throws关键字的；
     *  被调用的方法在定义的时候不存在throws关键字，但是仍然可能抛出异常，比如在Thread的run方法中，调用String的Parse系列方法对非数字的字符进行解析，就可能会抛出NumberFormatException，
     *  这种JVM是按照如下方式处理的：
     *      首先看当前的线程，是否在start之前，通过调用setUncaughtExceptionHandler(UncaughtExceptionHandler, eh)，设置了UncaughtExceptionHandler；如果已经设置，则使用此ExceptionHandler来处理；
     *      否则，查看当前Thread所在的ThreadGroup，是否设置了UncaughtExceptionHandler；如果已经设置，则使用此ExceptionHandler来处理；
     *      否则，查看Thread层面是否设置了UncaughtExceptionHandler，Thread类的静态方法setDefaultUncaughtExceptionHandler进行设置；如果已经设置，则使用此ExceptionHandler来处理；
     *      如果上述UncaughtExceptionHandler都没有找到，那么JVM会直接在console中打印Exception的StackTrace信息。
     *
     */

    public static void main(String[] args){
        BasicThread basicThread = new BasicThread();
        basicThread.test3();
    }



    /**
     * 守护线程与非守护线程
     * @Test 有别于main函数。 当父线程执行完毕后，子线程也会随之关闭。
     */
    public void test1(){
        System.out.println("当前时间："+new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);
        MyTask task = new MyTask();
        Timer timer = new Timer();
        timer.schedule(task,calendar.getTime());
    }

    /**
     * 若想捕获线程中的异常，不能使用try,catch 而是使用以下方式
     */
    public void test2(){
        Thread taskThread = new Task1();
        taskThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){ //UncaughtExceptionHandler  jdk1.5以后

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("你好哦，我捕获到了->" + t.getName() + ",他出现了如下问题: " + e.toString());
            }
        });
    }

    /**
     * getId()方法 获取线程的唯一标识
     * 此ID一定是正数(positive number)，且在线程的生命周期内不会发生改变，但当线程死亡后，该ID可能会被回收、重复利用。、
     * 猜测线程获取CPU分片的优先级与ID值有关，ID越小，优先级越高。 main线程的优先级为1
     */
    public void test3(){
        Thread thread = Thread.currentThread();
        System.out.println("getName()->" + thread.getName() + " ; getId()->" + thread.getId());
    }

    /**
     * 线程停止
     */
    public void test4(){
        //详情请看Thread5.java
    }
}

class MyTask extends TimerTask{
    @Override
    public void run() {
        System.out.println("任务执行了，时间为："+new Date());
    }
}

class Task1 extends Thread{
    public Task1(){
        super();
    }
    @Override
    public void run(){
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("name->" + Thread.currentThread().getName() + "你好");
        } catch (InterruptedException e) {
            System.out.println("name->" + Thread.currentThread().getName() + "阵亡啦，" + e.toString());
        }
    }
}


class Task2 implements Runnable{

    @Override
    public void run() {

    }
}

