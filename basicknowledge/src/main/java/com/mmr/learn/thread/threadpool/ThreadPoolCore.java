package com.mmr.learn.thread.threadpool;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mamr
 * @date 2020/9/7 2:26 下午
 */
public class ThreadPoolCore {
    public static void main(String[] args) {
        // 创建自定义线程池
        ExecutorService pool = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024),new MyThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        pool.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        pool.shutdown();

        System.out.println("123");
    }
}

class MyThreadFactory implements ThreadFactory {
    AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread t = new Thread(r);
        t.setName("my-thread-" + counter.addAndGet(1));
        return t;
    }
}
