package com.mmr.learn.thread.lesson10.twostepgrade;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 20:21
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args) {
        Service service = new Service();
        ThreadA threadA = new ThreadA(service);
        threadA.setName("a");
        threadA.start();

        ThreadB threadB = new ThreadB(service);
        threadB.setName("b");
        threadB.start();
    }
}
