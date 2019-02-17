package com.mmr.learn.thread.lesson7.syncblockstring2;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 16:36
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args) {
        Service service = new Service();
        ThreadA threadA = new ThreadA(service);
        threadA.start();
        ThreadB threadB = new ThreadB(service);
        threadB.start();
    }
}
