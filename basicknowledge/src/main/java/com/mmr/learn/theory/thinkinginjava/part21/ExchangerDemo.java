package com.mmr.learn.theory.thinkinginjava.part21;

import com.mmr.learn.theory.thinkinginjava.part15.BasicGenerator;
import net.mindview.util.Generator;

import java.util.List;
import java.util.concurrent.*;

/**
 * Exchanger演示
 * @author mamr
 * @date 2020/1/30 6:34 下午
 */
public class ExchangerDemo {
    static int size = 10;
    static int delay = 5;

    public static void main(String[] args) throws Exception{
        if(args.length > 0) {
            size = Integer.parseInt(args[0]);
        }
        if(args.length > 1) {
            delay = Integer.parseInt(args[1]);
        }
        ExecutorService exec = Executors.newCachedThreadPool();
        Exchanger<List<Fat>> xc = new Exchanger<>();
        List<Fat> producerList = new CopyOnWriteArrayList<>();
        List<Fat> consumerList = new CopyOnWriteArrayList<>();
        exec.execute(new ExchangerProducer<>(xc, BasicGenerator.create(Fat.class), producerList));
        exec.execute(new ExchangerConsumer<>(xc, consumerList));
        TimeUnit.SECONDS.sleep(delay);
        exec.shutdownNow();
    }
}

/**
 * 生产者
 */
class ExchangerProducer<T> implements Runnable {
    private Generator<T> generator;
    private Exchanger<List<T>> exchanger;
    private List<T> holder;

    public ExchangerProducer(Exchanger<List<T>> exchanger, Generator<T> gen, List<T> holder) {
        this.exchanger = exchanger;
        this.generator = gen;
        this.holder = holder;
    }
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                for(int i = 0; i < ExchangerDemo.size; i++) {
                    holder.add(generator.next());
                }
                holder = exchanger.exchange(holder);
            }
        } catch (InterruptedException e) {
            System.out.println("OK to terminate this way");
        }
    }
}

/**
 * 消费者
 */
class ExchangerConsumer<T> implements Runnable {
    private Exchanger<List<T>> exchanger;
    private List<T> holder;
    private volatile T value;
    public ExchangerConsumer(Exchanger<List<T>> exchanger, List<T> holder) {
        this.exchanger = exchanger;
        this.holder = holder;
    }
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                holder = exchanger.exchange(holder);
                for (T x : holder) {
                    // 此处在遍历列表时，移除了元素。为了不报错ConcurrentModificationException，
                    // 所以使用CopyOnWriteArrayList
                    value = x;
                    holder.remove(x);
                }
            }
        }catch (InterruptedException e) {
            System.out.println("OK to terminate this way");
        }
        System.out.println("Final value: " + value);
    }
}