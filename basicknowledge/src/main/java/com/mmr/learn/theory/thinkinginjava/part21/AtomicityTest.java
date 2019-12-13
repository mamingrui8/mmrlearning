package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 本例介绍了滥用原子性概念的下场 原子性并非不能使用，只不过需要考虑的地方太多，稍不留神就可能掉进陷阱
 * 通过在getValue()方法上添加或去掉synchronized修饰符，来探讨原子性的问题。
 * @author mamr
 * @date 2019/12/10 12:38
 */
public class AtomicityTest implements Runnable{
    private volatile int i = 0;

    /**
     * 如果getValue()不加synchronized,那么分析如下:
     *   虽然"return 1"是一个原子性操作，但是在何时执行却是不确定的。
     *   比如程序在执行完evenIncrement()后，准备调用getValue()，但在执行"return i"方法之前，又被调用了一次evenIncrement()，
     *   结果导致i值处于不稳定的中间状态
     *   除此之外，由于i不是volatile的，evenIncrement()虽然将值新增至主存中，但在调用getValue()时，很有可能是从本地处理器缓存中取值，
     *   也就是说，还存在可视性问题。
     */
    public synchronized int getValue() {
        return i;
    }
    private synchronized void evenIncrement() {
        i++;
        i++;
    }
    @Override
    public void run() {
        int count = 0;
        while(true) {
            evenIncrement();
            System.out.println(++count);
        }
    }

    /**
     * 本案例中有两个线程:1.main线程 -- 线程A 2.由Executors线程池提供的线程 -- 线程B
     * 有两个任务:1.main函数中的代码 -- 任务A 2.AtomicityTest中的run()代码  --任务B
     *
     * 实验结果:
     * 1. 运行第一遍int val = at.getValue();后，控制台没有任何输出。
     * 2. 运行第二遍int val = at.getValue();后，控制台可能输出一串数字，也可能没有任何的输出
     *
     * 推测:
     * 1. AtomicityTest中的run()只能通过线程B来驱动 经过实际测试发现，即便在main线程中打断点，丝毫不影响线程B任务的运行
     * 2. main()函数中的while(true)并不是一个原子性操作，这个循环块中每一条语句执行前后，都有可能进行线程的上下文切换，也即，其它线程都可能随时被线程调度器分配CPU时间片并运行任务。
     *    现在有一个疑点——为什么运行第一遍main()函数中的while()循环不会导致线程切换，但后续循环会切换？
     * 3. [重点]我猜测run()中的循环时一直运行的，只不过evenIncrement()并不会时时刻刻允许被调用，当且仅当线程B拿到了AtomicityTest的对象锁后，evenIncrement()才会实际被调用，而此时
     *    与之相配合的count会随evenIncrement()一同被保存下来(暂停?或者是镜像?)
     *    在debug模式下，每走一步main线程，线程B可能会运行若干毫秒，在这个时间片下evenIncrement();和System.out.println(++count);得到执行，而控制台在同一时刻只能由一个线程进行输出，
     *    因此只会打印这个时间片下的count(这也是线程B一直在运行，但如果main线程断点后不单步运行，控制台就没有任何输出的原因)
     * 4. 由于getValue()和evenIncrement()都是同步方法，因此即便i++不是原子性操作，但上升到evenIncrement()方法层面后，方法之间同步了。getValue()不可能在evenIncrement()只执行了一次
     *    i++时就执行return i; 因此main()永远不会得到一个奇数！
     *
     */
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicityTest at = new AtomicityTest();
        exec.execute(at);
        while(true) {
            int val = at.getValue();
            if (val % 2 != 0) {
                System.out.println(val);
                System.exit(0);
            }
        }
    }
}
