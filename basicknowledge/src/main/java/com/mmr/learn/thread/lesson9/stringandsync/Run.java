package com.mmr.learn.thread.lesson9.stringandsync;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 20:01
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args) {
        ThreadA threadA = new ThreadA();
        threadA.start();
        ThreadB threadB = new ThreadB();
        threadB.start();
    }
}
