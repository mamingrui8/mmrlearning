package com.mmr.learn.theory.thinkinginjava.part21;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * DelayQueue 演示 又叫做JDK延迟队列
 * 1. 队列中的对象只有到期时才能从队列中取走。
 * 2. 并不是到期了就立马被取走。所以说，延迟到期时间最长的对象(头对象)优先被取出。
 * 3. 任务的创建顺序与任务的执行顺序无关,任务是按照所期望的延迟顺序执行的。
 *
 * 疑问:
 * 1. DelayQueue中每一个元素的延迟时间都是相同的吗？
 *    答: 不是的，每一个元素的延迟时间都可以不同。
 * 2. 如果head头元素的延迟到期时间并不是最长的，该怎么办呢？
 *    答: 队列内部自动调整了元素位置，即便没有位于头结点上，已经延迟到期的元素，一定会排在尚未延迟到期的元素的前面出队。
 *
 * [555 ] Task 1
 * [961 ] Task 4
 * [1693] Task 2
 * [1861] Task 3
 * [4258] Task 0
 * (0:4258)
 * (1:555)
 * (2:1693)
 * (3:1861)
 * (4:961)
 * (5:5000)
 *
 * 其中， () 表示的是任务的创建    [] 表示的是任务的执行
 *
 * 本例中，不需要创建其他的线程来运行DelayedTask任务，这是因为DelayedTaskConsumer作为消费者，
 * 通过构造函数就能够获取到过期队列中的所有任务，接着主动调用它们的run()方法即可。
 * 所以，即便是不让DelayedTask视线Runnable接口，本例的执行结果也不会发生改变！！！
 *
 * 适用场景:
 * 1. 任务的种类多，不同种类的任务执行的优先级不同。轻量的任务优先执行，重量的任务后执行。
 *    这样做的好处在于，轻量的任务即便是后插入的，也能被消费者优先执行，提高了系统单位时间的吞吐量。
 *
 * @author mamr
 * @date 2020/1/16 11:11 下午
 */
public class DelayQueueDemo {
    public static void main(String[] args) {
        test2();
    }

    public static void test1() {
        Random random = new Random(47);
        ExecutorService exec = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> queue = new DelayQueue<>();
        // Fill with tasks that have random delays:
        for(int i = 0; i < 5; i++) {
            queue.put(new DelayedTask(random.nextInt(5000)));
        }

        //使用DelayQueue take()的方式获取任务
        queue.add(new DelayedTask.EndSentinel(5000, exec));
        exec.execute(new DelayedTaskConsumer(queue));
    }

    public static void test2() {
        Random random = new Random(47);
        ExecutorService exec = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> queue = new DelayQueue<>();
        // Fill with tasks that have random delays:
        for(int i = 0; i < 5; i++) {
            queue.put(new DelayedTask(random.nextInt(5000)));
        }

        //使用DelayQueue poll()方式获取任务
        while(queue.size() != 0) {
            // 每执行一次DelayQueue poll()且返回的元素不是null，则DelayQueue等待队列的元素个数会减一
            DelayedTask task = queue.poll();
            if(task != null) {
                System.out.println(LocalDateTime.now(ZoneId.of("+08:00")));
            }
        }
    }
}

class DelayedTask implements Runnable, Delayed {
    private static int counter = 0;
    private final int id = counter++;
    /**
     * 延迟的时间(单位: 毫秒)
     */
    private final int delta;
    /**
     * 任务准备执行的时间点(单位: 纳秒)
     */
    private final long trigger;
    protected static List<DelayedTask> sequence = new ArrayList<>();

    public DelayedTask(int delayInMilliseconds) {
        delta = delayInMilliseconds;
        trigger = System.nanoTime() + NANOSECONDS.convert(delta, MILLISECONDS);
        sequence.add(this);
    }

    @Override
    public long getDelay(@NotNull TimeUnit unit) {
        // 过期时间
        return unit.convert(trigger - System.nanoTime(), NANOSECONDS);
    }

    /**
     * 用于在过期任务队列中，任务之间执行顺序的排序
     */
    @Override
    public int compareTo(@NotNull Delayed arg) {
        DelayedTask that = (DelayedTask)arg;
        if(trigger < that.trigger) return -1;
        if(trigger > that.trigger) return 1;
        return 0;
    }

    @Override
    public void run() {
        System.out.println(this + " ");
    }

    @Override
    public String toString() {
        // 语法规则： %[argument_index$][flags][width][.precision]conversion
        // %后面的1$指的是第一个参数，也就是delta
        // $后面的-4d指的是如果数据总长度不够4位，则由右向左补足空格
        return String.format("[%1$-4d]", delta) + " Task " + id;
    }

    public String summary() {
        return "(" + id + ":" + delta + ")";
    }

    public static class EndSentinel extends DelayedTask {
        private ExecutorService exec;
        public EndSentinel(int delay, ExecutorService e) {
            super(delay);
            exec = e;
        }
        @Override
        public void run() {
            for(DelayedTask pt : sequence) {
                System.out.println(pt.summary() + " ");
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            exec.shutdownNow();
        }
    }
}

/**
 * 过期队列的消费者
 */
class DelayedTaskConsumer implements Runnable {
    private DelayQueue<DelayedTask> q;
    public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
        this.q = q;
    }
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                // DelayQueue take()
                // 取出队列中的head元素，若队列中没有任何延迟到期的元素存在，则该方法将会被阻塞
                // 此时有两种可能: 1. 队列中没有元素 2. 队列中有元素，但都尚未过期
                // 直到队列中有延迟到期的元素为止

                // 即便是不让DelayedTask实现Runnable接口，本例的执行结果也不会发生改变！！！
                q.take().run();
            }
        } catch (InterruptedException e) {
            // Acceptable way to exit
            System.out.println("Acceptable way to exit");
        }
        System.out.println("Finished DelayedTaskConsumer");
    }
}
