package com.mmr.learn.jdk8.t12;

public class Test implements MyFun1,MyFun2{
    @Override
    public String getName() {
        //必须指明到底调用哪个接口的getName()方法
        return MyFun1.super.getName();
    }
}
