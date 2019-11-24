package com.mmr.learn.theory.thinkinginjava.part15;

import java.util.List;
import java.util.Map;

/**
 * 类型推断的限制(jdk8目前没有发现写法1有限制)
 * @author Charles Wesley
 * @date 2019/11/20 23:51
 */
public class LimitsOfInference {
    static void f(Map<Person, List<? extends Pet>> petPeople){

    }
    public static void main(String[] args) {
        //写法1: 不显示的指明类型，让编译器来推断到底使用哪种类型
        f(New.map());

        //写法2: 使用泛型方法并显示的指明类型
        f(New.<Person, List<? extends Pet>>map());
    }
}

class Person{}
class Pet{}