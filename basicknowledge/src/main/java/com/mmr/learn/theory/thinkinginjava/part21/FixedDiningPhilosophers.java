package com.mmr.learn.theory.thinkinginjava.part21;

import com.mmr.learn.theory.thinkinginjava.part10.practice9.Inter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 哲学家问题 防止死锁
 * 核心: 破坏了死锁的组成因素——循环等待
 */
public class FixedDiningPhilosophers {
    public static void main(String[] args) throws Exception{
        int ponder = 5;
        if (args.length > 0) {
            ponder = Integer.parseInt(args[0]);
        }
        int size = 5;
        if (args.length > 1) {
            size = Integer.parseInt(args[1]);
        }
        ExecutorService exec = Executors.newCachedThreadPool();
        Chopstick[] chopsticks = new Chopstick[size];
        for(int i = 0; i < size; i++) {
            chopsticks[i] = new Chopstick();
        }
        for(int i = 0; i < size; i++) {
            if (i < (size -1)) {
                exec.execute(new Philosopher(chopsticks[i], chopsticks[i+1], i, ponder));
            } else {
                //强行把最后一个哲学家的筷子摆反 这就会导致 不是所有的哲学家都先拿左筷子再拿有筷子。
                exec.execute(new Philosopher(chopsticks[0], chopsticks[size-1], i, ponder));
            }
        }
        if (args.length == 3 && args[2].equals("timeout")) {
            TimeUnit.SECONDS.sleep(5);
        } else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
}
