package com.mmr.learn.thread.lesson8.synStaticMethod;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 17:28
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args) {
        Service service = new Service();
        ThreadA threadA = new ThreadA();
        threadA.start();
        ThreadB threadB = new ThreadB();
        threadB.start();
    }
}
