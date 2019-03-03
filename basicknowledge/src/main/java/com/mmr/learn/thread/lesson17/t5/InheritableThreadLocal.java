package com.mmr.learn.thread.lesson17.t5;

import java.util.Date;

public class InheritableThreadLocal extends java.lang.InheritableThreadLocal {
    @Override
    protected Object initialValue(){
        return new Date().getTime();
    }
}
