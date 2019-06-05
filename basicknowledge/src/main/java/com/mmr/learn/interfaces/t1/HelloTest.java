package com.mmr.learn.interfaces.t1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloTest {
    public static void main(String[] args) {
        try {
            HelloAnnotation helloAnnotation = new HelloAnnotation();
            Class clazz = helloAnnotation.getClass();
            Method method = clazz.getMethod("sayHello", String.class, String.class);

            if(method.isAnnotationPresent(TestAnnotation.class)){
                TestAnnotation testAnnotation = method.getAnnotation(TestAnnotation.class);
                System.out.println(testAnnotation.msg());
            }

            method.invoke(helloAnnotation, "Hello", "World");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
