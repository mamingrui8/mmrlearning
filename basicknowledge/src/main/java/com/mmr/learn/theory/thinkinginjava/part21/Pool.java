package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 用于演示Semaphore 允许在同一时刻，多个任务访问同一项资源
 * @author mamr
 * @date 2020/1/29 7:43 下午
 */
public class Pool<T> {
    private int size;
    private List<T> items = new ArrayList<>();
    private volatile boolean[] checkedOut;
    private Semaphore available;
    public Pool(Class<T> classObject, int size){
        this.size = size;
        checkedOut = new boolean[size];
        available = new Semaphore(size, true);
        // Load pool with objects that can be checked out
        for (int i = 0; i < size; i++) {
            try {
                // Assumes a default constructor:
                items.add(classObject.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 签出对象
     */
    public T checkOut() throws InterruptedException{
        available.acquire();
        return getItem();
    }

    /**
     * 签入对象
     */
    public void checkIn(T x) {
        if (releaseItem(x)) {
            available.release();
        }
    }
    private synchronized T getItem() {
        for (int i = 0; i < size; ++i) {
            if(!checkedOut[i]) {
                checkedOut[i] = true;
                return items.get(i);
            }
        }
        // Semaphore prevents reaching here
        return null;
    }
    private synchronized boolean releaseItem(T item) {
        int index = items.indexOf(item);
        if (index == -1) {return false;}
        if (checkedOut[index]) {
            checkedOut[index] = false;
            return true;
        }
        // Wasn't checked out
        return false;
    }
}
