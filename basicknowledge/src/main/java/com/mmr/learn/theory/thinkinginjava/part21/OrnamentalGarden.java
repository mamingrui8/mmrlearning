package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 1.演示终止问题
 * 2.是一个资源共享的示例
 * 场景: 这里有一个花园，委员会希望统计每天通过花园大门的人数。大门会有多扇。
 * 感悟: 此例使用的终止手法实际上就是通过在run()中使用变量控制程序是否执行，若不满足条件则退出run()，由于没有了Task，线程自然而然就终止了。
 *
 * @author Charles Wesley
 * @date 2019/12/15 20:42
 */
public class OrnamentalGarden {
    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new GardenEntrance(i));
        }
        //让线程跑一会儿，接着停止整个线程池并统计数据
        TimeUnit.SECONDS.sleep(3);
        GardenEntrance.cancel();
        exec.shutdown();
        //ExecutorService.awaitTermination()会等待每个任务结束，如果所有的任务在超时时间到达之前就全部结束了，则返回true，否则返回false。
        if (!exec.awaitTermination(250, TimeUnit.MILLISECONDS)) {
            System.out.println("Some tasks were not terminated!");
        }

        /*
         * 执行cancel()会导致每个Task都会退出run()，并因此终止。为了阻止gc回收GardenEntrance对象，我们在GardenEntrance构造器中将对象加入到了静态队列中
         */
        System.out.println("Total: " + GardenEntrance.getTotalCount());
        System.out.println("Sum of Entrances: " + GardenEntrance.sumEntrances());
    }
}

/**
 * 计数器
 */
class Count {
    private int count = 0;
    private Random rand = new Random(47);
    public synchronized int increment() {
        int temp = count;
        //Random nextBoolean() 将返回一个伪随机数，它取自此随机数生成器序列的均匀分布的boolean值
        //这种伪随机理论上是均分的，也就是说，执行increment()方法时，大约有一半的时间将产生让步
        if(rand.nextBoolean()) {
            //yield()是为了在多线程环境下同时访问count时，使资源竞争的问题更快的暴露出来
            Thread.yield();
        }
        return (count = ++temp);
    }
    public synchronized int value() {
        return count;
    }
}

/**
 * 公园的大门
 */
class GardenEntrance implements Runnable{
    private static Count count = new Count();
    private static List<GardenEntrance> entrances = new ArrayList<>();
    /**
     * 每一个GardenEntrance对象都维护着自己的一个本地值number，它包含了通过某个特定入口进入的参观者的数量
     */
    private int number = 0;
    private final int id;
    private static volatile boolean canceled = false;
    public static void cancel() {
        //在volatile boolean属性上进行的原子性操作，因此没必要使用synchronized将方法转变成同步
        canceled = true;
    }
    public GardenEntrance(int id) {
        this.id = id;
        //将任务保持在队列中。阻止gc回收已死亡的任务
        entrances.add(this);
    }
    @Override
    public void run() {
        while(!canceled) {
            synchronized (this){
                ++number;
            }
            System.out.println(this + " Total: " + count.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            }catch (InterruptedException e) {
                System.out.println("sleep interrupted");
            }
        }
        System.out.println("Stopping " + this);
    }

    public synchronized int getValue() {
        return number;
    }

    public String toString() {
        return "Entrance " + id + ": " + getValue();
    }
    public static int getTotalCount() {
        return count.value();
    }
    public static int sumEntrances() {
        int sum = 0;
        for(GardenEntrance entrance : entrances) {
            sum += entrance.getValue();
        }
        return sum;
    }
}