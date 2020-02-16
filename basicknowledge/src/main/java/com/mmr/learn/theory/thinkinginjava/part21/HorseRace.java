package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 演示CyclicBarrier
 *
 * 本例的场景是"赛马"
 */
public class HorseRace {
    static final int FINISH_LINE = 75;
    private List<Horse> horses = new ArrayList<>();
    private ExecutorService exec = Executors.newCachedThreadPool();
    private CyclicBarrier barrier;
    public HorseRace(int nHorses, final int pause) {
        // nHorses的数量就是"计数器"，每一个线程都必须等待其它的线程本轮执行完毕(监控的手段是检查barrier的计数器的值是否为0)，每次执
        // 行barrier.await()都会释放一次计数器(数值减一)
        // 当且仅当计数器的值为0时，才会执行Runnable(把它看做"栅栏动作")，栅栏动作中，绘画出了绘画出每匹马在本轮的赛跑足迹
        barrier = new CyclicBarrier(nHorses, new Runnable() {
            @Override
            public void run() {
                StringBuilder s = new StringBuilder();
                //分隔符 绘画出赛马的跑道
                for(int i = 0; i < FINISH_LINE; i++) {
                    s.append("=");
                }
                System.out.println(s);

                // 绘画出每匹马在本轮的赛跑足迹
                for (Horse horse : horses) {
                    System.out.println(horse.tracks());
                }

                // 当马儿行走的步长超过或等于跑道总长度时，证明该马已经获得比赛的胜利
                for(Horse horse : horses) {
                    if (horse.getStrides() >= FINISH_LINE) {
                        System.out.println(horse + " won!");
                        exec.shutdownNow();
                        return;
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(pause);
                }catch (InterruptedException e) {
                    System.out.println("barrier-action sleep interrupted");
                }
            }
        });
        for (int i = 0; i < nHorses; i++) {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            exec.execute(horse);
        }
    }

    public static void main(String[] args) {
        int nHorse = 7;
        int pause = 200;
        if (args.length > 0) {
            int n = new Integer(args[0]);
            nHorse = n > 0 ? n : nHorse;
        }
        if (args.length > 1) {
            int p = new Integer(args[1]);
            pause = p > -1 ? p : pause;
        }
        new HorseRace(nHorse, pause);
    }
}


class Horse implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    /**
     * 步幅
     */
    private int strides = 0;
    /**
     * 之所以每匹马(对应着不同的线程)每次运行时获得的步长不同，是因为在Random上使用了static的缘故
     * 多个线程多次运行rand.nextInt()实际上针对的是同一个Random对象 由于每次执行nextInt()都会修改seed，因此得到的值显然不同。
     * 如果我们将static去掉，则所有的马每次奔跑的步长一定是相同的，最终一起到达终点。
     */
    private static Random rand = new Random(47);

    // 多个任务共用同一个CyclicBarrier
    private static CyclicBarrier barrier;
    public Horse(CyclicBarrier b) {
        barrier = b;
    }
    public synchronized int getStrides() {
        return strides;
    }
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                synchronized (this) {
                    // 每匹马每次向前行走0~3步长 由于本例中对随机数给与了种子 因此步长可以预测
                    int temp = rand.nextInt(3);
                    System.out.println("temp: " + temp);
                    strides += temp;
                }
                // 每一匹马都不会抢跑，当跑完一轮栅栏后，马儿会停下来，直到其它所有的马都跑完这轮栅栏后，才会一起向下一个栅栏移动。
                barrier.await();
            }
        } catch (InterruptedException e) {
            // 使用合适的方式退出
            System.out.println("Horse interrupt");
        } catch (BrokenBarrierException e) {
            // 我们需要关注的异常
            throw new RuntimeException(e);
        }
    }
    @Override
    public String toString() {
        return "Horse " + id + " ";
    }

    /**
     * 足迹
     */
    public String tracks() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < getStrides(); i++) {
            s.append("*");
        }
        s.append(id);
        return s.toString();
    }
}