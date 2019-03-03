package com.mmr.learn.thread.topic.ConsumerAndProducer.t1;

import java.util.LinkedList;

/**
 * Description: 订单和库存管理系统
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年03月03日 17:30
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Ordering {
    volatile public static LinkedList<String> orderList = new LinkedList<>();
    public static final Integer MAX_VOLUME = 5;
}
