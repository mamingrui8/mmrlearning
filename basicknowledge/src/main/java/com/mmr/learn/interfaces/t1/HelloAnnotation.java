package com.mmr.learn.interfaces.t1;

/**
 * 使用注解的测试类
 */
public class HelloAnnotation {

    @TestAnnotation(msg = "这是一个sayHello方法")
    public void sayHello(String name, String value){
        System.out.println("hello" + name);
        System.out.println(value);
    }
}
