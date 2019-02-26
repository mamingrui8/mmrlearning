package com.mmr.learn.aop.example3;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2018年12月16日 18:29
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class PersonImpl implements IPerson{

    @Override
    public void doSomething() {
        System.out.println("我要买apple");
    }

    public void saySomething() {
        System.out.println("不错哦");
    }
}
