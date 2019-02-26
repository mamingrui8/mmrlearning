package com.mmr.learn.thread.lesson8.synctwolock;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 17:50
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class Run {
    public static void main(String[] args) {
        Service service = new Service();

        ThreadA threadA = new ThreadA(service);
        threadA.setName("A");
        threadA.start();

        ThreadB threadB = new ThreadB(service);
        threadB.setName("B");
        threadB.start();

        ThreadC threadC = new ThreadC(service);
        threadC.setName("C");
        threadC.start();
    }
}
