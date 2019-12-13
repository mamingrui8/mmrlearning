package com.mmr.learn.theory.thinkinginjava.part21;

/**
 *
 * @author mamr
 * @date 2019/12/13 14:06
 */
public class SerialNumberChecker {
    private static final int SIZE = 10;

}


class CircularSet {
    private int[] array;
    private int len;
    private int index = 0;
    public CircularSet(int size) {
        array = new int[size];
        len = size;
        //不使用SerialNumberGenerator，对array数组赋初值
        for (int i = 0;i < size; i++) {
            array[i] = -1;
        }
    }

    public synchronized void add(int i) {
        array[index] = i;
        //对index进行自增，接着覆盖旧值
        index = index++ % len;
    }

    public synchronized boolean contains(int val) {
        for(int i = 0; i < len; i++) {
            if(array[i] == val) {
                return true;
            }
        }
        return false;
    }
}