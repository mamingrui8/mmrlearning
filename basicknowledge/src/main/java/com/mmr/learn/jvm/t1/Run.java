package com.mmr.learn.jvm.t1;

public class Run {
    private int count = 0;

    private void testAdd(){
        count ++;
        testAdd();
    }

    public void test(){
        try {
            testAdd();
        }catch (Throwable e){
            System.out.println(e);
            System.out.println("栈的最大深度: " + count);
        }
    }

    public static void main(String[] args){
        new Run().test();
    }
}
