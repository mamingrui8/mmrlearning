package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * 演示Semaphore
 * 本对象是一个在创建时会浪费大量时间的对象 该对象将受到Pool的管理
 * @author mamr
 * @date 2020/1/29 8:02 下午
 */
public class Fat {
    // 使用volatile是为了避免产生字撕裂
    private volatile double d;
    private static int counter = 0;
    private final int id = counter++;
    public Fat() {
        // 创建代价高昂的可以被打断的操作
        for (int i = 0; i < 10000; i++) {
            d += (Math.PI + Math.E) / (double) i;
        }
    }
    public void operation() {  System.out.println(this); }
    public String toString() { return "Fat id:" + id;}
}
