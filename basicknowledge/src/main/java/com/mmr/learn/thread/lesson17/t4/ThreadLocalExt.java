package com.mmr.learn.thread.lesson17.t4;

public class ThreadLocalExt extends ThreadLocal<java.lang.String>{
    @Override
    public String initialValue(){
        return "我是默认值，第一次的get不再是null啦";
    }
}
