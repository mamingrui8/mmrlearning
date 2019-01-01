package com.mmr.aop.example1;

/**
 * Description: 横切关注点
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2018年12月16日 16:25
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class TimeHandler {
    public void printStartTime(){
        System.out.println("前置增强 StartTime ---> " + System.currentTimeMillis());
    }
    public void printEndTime(){
        System.out.println("后置增强 EndTime ---> " + System.currentTimeMillis());
    }
}
