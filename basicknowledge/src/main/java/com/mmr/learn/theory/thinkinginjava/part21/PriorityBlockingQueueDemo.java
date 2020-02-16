package com.mmr.learn.theory.thinkinginjava.part21;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 介绍PriorityBlockingQueue
 * @author mamr
 * @date 2020/1/27 8:22 下午
 */
public class PriorityBlockingQueueDemo {
    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();

        PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<>();
        exec.execute(new PrioritizedTaskProducer(queue, exec));
        exec.execute(new PrioritizedTaskConsumer(queue));
    }
}

/**
 * 优先级任务对象
 */
class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
    private Random rand = new Random(47);
    private static int counter = 0;
    private final int id = counter++;
    // 这里的priority只不过是一个形式而已，真正排序的是compareTo()，这个变量叫什么名字都可以
    private final int priority;
    protected static List<PrioritizedTask> sequence = new ArrayList<>();

    public PrioritizedTask(int priority) {
        this.priority = priority;
        // 序列中对象根据创建顺序排列
        sequence.add(this);
    }

    @Override
    public int compareTo(@NotNull PrioritizedTask arg) {
        // 任务按照优先级priority由大到小排序
        return Integer.compare(arg.priority, priority);
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(250));
        } catch (InterruptedException e) {
            System.out.println("Acceptable way to exit");
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return String.format("[%1$-3d]", priority) + " Task " + id;
    }

    public String summary() {
        return "(" + id + ":" + priority + ")";
    }

    public static class EndSentinel extends PrioritizedTask {
        private ExecutorService exec;
        public EndSentinel(ExecutorService e) {
            // lowest priority in this program
            super(-1);
            exec = e;
        }
        @Override
        public void run() {
            int count = 0;
            for (PrioritizedTask pt : sequence) {
                System.out.println(pt.summary());
                if(++count % 5 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            exec.shutdownNow();
        }
    }
}

/**
 * 优先级任务生产者
 */
class PrioritizedTaskProducer implements Runnable {
    private Random rand = new Random(47);
    private Queue<Runnable> queue;
    private ExecutorService exec;
    public PrioritizedTaskProducer(Queue<Runnable> q, ExecutorService e) {
        queue = q;
        exec = e;
    }

    @Override
    public void run() {
        // 为了便于测试，任务分成了三批

        // 第一批 优先级随机指定
        // Unbounded queue; never blocks.
        // Fill it up fast with random priorities
        for (int i= 0; i < 10; i++) {
            queue.add(new PrioritizedTask(rand.nextInt(10)));
            Thread.yield();
        }
        // 第二批 优先级写死，且保持一致
        // Trickle in highest-priority jobs:
        try {
            for(int i = 0; i < 10; i++) {
                TimeUnit.MILLISECONDS.sleep(250);
                queue.add(new PrioritizedTask(10));
            }
            // 第三批 优先级有小到大指定
            // Add jobs. lowest priority first:
            for (int i = 0; i < 10; i++) {
                queue.add(new PrioritizedTask(i));
            }
            // A sentinel to stop all the tasks:
            queue.add(new PrioritizedTask.EndSentinel(exec));
        } catch (InterruptedException e) {
            System.out.println("Acceptable way to exit");
        }
        System.out.println("Finished PrioritizedTaskProducer");
    }
}

class PrioritizedTaskConsumer implements Runnable {
    private PriorityBlockingQueue<Runnable> q;
    public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> q) {
        this.q = q;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // Use current thread to run the task:
                // 取数时，不必考虑队列中是否有元素存在，因为队列在没有元素时，将直接阻塞读取者
                q.take().run();
            }
        } catch (InterruptedException e) {
            System.out.println("Acceptable way to exit");
        }
        System.out.println("Finished PrioritizedTaskConsumer");
    }
}

