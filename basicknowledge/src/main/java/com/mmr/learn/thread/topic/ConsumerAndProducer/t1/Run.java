package com.mmr.learn.thread.topic.ConsumerAndProducer.t1;

import java.util.LinkedList;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年03月03日 17:41
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + ",");
        }
        list.removeLast();

        list.forEach(System.out::print);
    }
}
