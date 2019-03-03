package com.mmr.learn.thread.lesson17.t4;

public class Run {
    public static ThreadLocalExt threadLocalExt = new ThreadLocalExt();

    public static void main(String[] args) {
        if(null == threadLocalExt.get()){
            System.out.println("从未放过值");
            threadLocalExt.set("我是值");
        }
        System.out.println(threadLocalExt.get());
    }
}
