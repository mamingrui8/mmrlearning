package com.mmr.learn.aop.example2;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2018年12月16日 17:29
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class LogHandler {
    public void LogBefore()
    {
        System.out.println("Log before method");
    }

    public void LogAfter()
    {
        System.out.println("Log after method");
    }
}
