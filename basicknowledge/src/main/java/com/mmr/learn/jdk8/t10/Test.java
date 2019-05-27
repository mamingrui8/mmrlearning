package com.mmr.learn.jdk8.t10;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

/**
 * 并行流与顺序流
 * Fork/join框架
 */
public class Test {

    /**
     * 普通的循环求和
     */
    @org.junit.Test
    public void test1(){
        long sum = 0;
        for(long i =0; i< 1000000000L; i++){
            sum += i;
        }
        System.out.println(sum);
    }

    /**
     * 使用Fork/join求和
     */
    @org.junit.Test
    public void test2(){
        Instant start = Instant.now();

        //需要ForkJoin线程池的支持
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinCalculate fork = new ForkJoinCalculate(0, 1000000000L);
        Long sum = pool.invoke(fork);
        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println(Duration.between(start, end).toMillis());
    }

    /**
     * 使用jdk1.8求和
     */
    public void test3(){
        Instant start = Instant.now();

        Long sum = LongStream.rangeClosed(0, 1000000000L)
                .parallel()
                .reduce(0, Long::sum);

        Instant end = Instant.now();

        System.out.println(Duration.between(start, end).toMillis());
    }

}
