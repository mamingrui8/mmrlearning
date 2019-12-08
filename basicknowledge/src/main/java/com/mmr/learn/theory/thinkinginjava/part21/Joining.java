package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * 本例演示了join() 如果某一个线程在另一个线程t上调用t.join()，则当前线程将被挂起，直到目标线程t结束时才会继续执行。
 *
 * 注意: 这里有4个线程，执行grumpy.interrupt()后，doc中grumy.join()方法随即执行完毕，此时虽然场上仍有3个线程存活(sleeper，dopey，doc),但与grumpy关系最为紧密的线程是doc
 *       因此线程调度器会绝对优先的将当前CPU时间片交给doc，执行完doc的run()中余下代码，而不会让其它两个线程(sleeper和dopey)执行！
 *       哪怕我把new Sleeper("Sleeper", 15) 线程暂停的时间改成15毫秒，上述的结论也一定成立。
 *
 *       java.util.concurrent类库中还包含如CyclicBarrier这样的工具，它们可能比最初线程类库中的join()更为合适。
 * @author Charles Wesley
 * @date 2019/12/8 17:30
 */
public class Joining {
    public static void main(String[] args) {
        //定义准备join()其它线程的线程 Sleeper
        Sleeper sleeper = new Sleeper("Sleeper", 1500),
                grumpy = new Sleeper("Grumy", 1500);

        Joiner dopey = new Joiner("Dopey", sleeper),
                doc = new Joiner("Doc", grumpy);

        //在除Grumpy以外的线程中调用interrupt()时，线程调度器会给线程设定一个标志，表明当前线程已经被中断了。
        grumpy.interrupt();
    }
}

class Sleeper extends Thread{
    private int duration;
    public Sleeper(String name, int sleepTime) {
        super(name);
        duration = sleepTime;
        start();
    }
    @Override
    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            //isInterrupted()返回false的原因是: 当线程被中断异常捕获时，线程调度器将清理这个线程身上的中断标志。
            //除了在异常中使用外,isInterrupted()还可以用于其他情况，比如线程可能会检查其中断状态。
            System.out.println(this.getName() + " was interrupted. " + "isInterrupted(): " + this.isInterrupted());
            return;
        }
        System.out.println(this.getName() + " has awakened");
    }
}

class Joiner extends Thread {
    private Sleeper sleeper;
    public Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }
    public void run() {
        try {
            //当前线程Joiner执行任务的过程中，调用另一个线程Sleeper的join()方法
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println(this.getName() + " join completed");
    }
}