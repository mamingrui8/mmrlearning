package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1.比较将synchronized加载方法上与加载某一个代码块上的区别。
 * 2.如何把一个非保护类型的类，在其它类的控制和保护之下，应用于多线程的环境中。
 *
 * 使用的设计模式: 模板方法模式。
 * 原因: 可以看到PairManager类的结构，它的一部分功能在基类中定义，并且其一个或多个抽象方法在派生类中定义。
 *       设计模式使得我们得以把变化封装到代码里；在此，变化的部分是模板方法increment()
 *
 * 一次执行结果: (每次的执行结果都可能不同)
 * pm1: Pair: x=512, y=512 checkCounter = 3
 * pm2: Pair: x=513, y=513 checkCounter = 1909359390
 *
 * 从结果上来看，虽然多次执行后，每次的结果都不一样，但总的来说，pm2的PairChecker执行次数比pm1的要多的多。因为前者采用同步控制块进行同步，
 * 所以对象不加锁的时间更长。这也是宁愿使用同步控制块，而不是对整个方法进行同步控制的典型原因: 使得其他线程能更多的访问。
 * @author Charles Wesley
 * @date 2019/12/14 22:13
 */
public class CriticalSection{
    /**
     * 测试两种不同的同步处理方法
     */
    static void testApproaches(PairManager pman1, PairManager pman2) {
        ExecutorService exec = Executors.newCachedThreadPool();
        //拼命的加数据
        PairManipulator pm1 = new PairManipulator(pman1),
                pm2 = new PairManipulator(pman2);
        //拼命的检查x和y是否相等
        PairChecker pcheck1 = new PairChecker(pman1),
                pcheck2 = new PairChecker(pman2);
        exec.execute(pm1);
        exec.execute(pm2);
        exec.execute(pcheck1);
        exec.execute(pcheck2);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("pm1: " + pm1 + "\npm2: " + pm2);
        System.exit(0);
    }

    public static void main(String[] args) {
        PairManager pman1 = new PairManager1(),
                pman2 = new PairManager2();
        testApproaches(pman1, pman2);
    }
}

/**
 * 此类并不是线程安全的
 * 不安全的原因:
 * 自增操作不是线程安全的，并且没有任何一个方法被标记成synchronized，所以不能保证
 * 一个Pair对象在多线程程序中不被破坏
 *
 * 虽然Pair不是线程安全的，但通过创建PairManager就可以实现在线程安全的环境下使用这个非线程安全的类！
 * PairManager类持有一个Pair对象并控制对它的一切访问。
 * 注意，PairManager中只有一个public方法，那就是getPair()，即便Pair对象再怎么非线程安全，如果同一时刻只有一个线程访问Pair对象的方法，那么这个操作就一定是线程安全的。
 * 而increment()方法则使用了两种不同的实现方式: 1.在方法上加synchronized 2.在代码块中加synchronized
 */
class Pair {
    private int x,y;
    public Pair() {
        this(0, 0);
    }
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void incrementX() {x++;}
    public void incrementY() {y++;}
    public int getX() {return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    @Override
    public String toString() {
        return  "x=" + x + ", y=" + y ;
    }

    public class PairValuesNotEqualException extends RuntimeException{
        private static final long serialVersionUID = 4821839673977177193L;
        public PairValuesNotEqualException() {
            super("Pair values not equal: " + Pair.this);
        }
    }
    public void checkStatus() {
        if(x != y) {
            throw new PairValuesNotEqualException();
        }
    }
}

/**
 * PairManager是一个线程安全的类，它用来保护一个Pair对象
 */
abstract class PairManager {
    AtomicInteger checkCounter = new AtomicInteger(0);
    protected Pair p = new Pair();
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<>());
    /**
     * synchronized直接加载方法上
     */
    public synchronized Pair getPair() {
        //Make a copy to keep the original safe:
        return new Pair(p.getX(), p.getY());
    }
    //本方法模拟一系列操作动作
    protected void store(Pair p) {
        storage.add(p);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public abstract void increment();
}

class PairManager1 extends PairManager{
    @Override
    public synchronized void increment() {
        p.incrementX();
        p.incrementY();
        store(getPair());
    }
}

class PairManager2 extends PairManager {
    @Override
    public void increment() {
        Pair temp;
        synchronized(this) {
            p.incrementX();
            p.incrementY();
            temp = getPair();
        }
        //此处store()这个操作是线程安全的，不需要放在synchronized(this)同步块中
        //因为store()中的storage.add(p);是 在向一个实现了synchronized的ArrayList中添加对象
        //而在PairManager1中之所以把store(getPair())放在同步中，是因为没有选择啊，毕竟synchronized直接写在了方法上...
        store(temp);
    }
}

class PairManipulator implements Runnable {
    private PairManager pm;
    public PairManipulator(PairManager pm) {
        this.pm = pm;
    }
    @Override
    public void run() {
        while(true) {
            pm.increment();
        }
    }
    public String toString() {
        return "Pair: " + pm.getPair() +  " checkCounter = " + pm.checkCounter.get();
    }
}

class PairChecker implements Runnable{
    private PairManager pm;
    public PairChecker(PairManager pm) {
        this.pm = pm;
    }
    @Override
    public void run() {
        while(true) {
            //每次成功执行checkStatus()后，都递增checkCounter
            //System.out.println("执行检测...");
            pm.checkCounter.incrementAndGet();
            pm.getPair().checkStatus();
        }
    }
}