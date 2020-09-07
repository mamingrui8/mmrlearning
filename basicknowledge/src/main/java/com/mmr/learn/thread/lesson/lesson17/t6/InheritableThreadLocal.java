package com.mmr.learn.thread.lesson.lesson17.t6;

import java.util.Date;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年03月03日 17:15
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class InheritableThreadLocal extends java.lang.InheritableThreadLocal{
    @Override
    protected Object initialValue(){
        return new Date().getTime();
    }

    @Override
    protected Object childValue(Object parentValue){
        return parentValue + " 我在子线程加的！";
    }
}
