package com.mmr.learn.theory.thinkinginjava.part12.practice4;

public class MMRException extends Throwable{
    /**
     * 接收字符串参数的构造器
     */
    MMRException(String message){
        super(message);
    }

    MMRException(){
        super();
    }

//    public void printStackTrace() {
//        System.out.println("出错了，我是自定义异常！");
//    }
}
