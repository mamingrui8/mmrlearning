package com.mmr.learn.theory.thinkinginjava.part15;

import net.mindview.util.Generator;

/**
 * 银行柜员模型 使用Generator接口 实现泛型的匿名内部类
 * 所用设计模式: 工厂设计模式
 * @author mamr
 * @date 2019/11/25 15:40
 */
public class BankTeller {

}

class Customer{
    /**
     * 类型计数器
     */
    private static long counter = 1;

    /**
     * 对象标记
     */
    private final long id = counter++;

    /**
     * 构造函数定义成私有，强制性的使用generator方法生成对象
     */
    private Customer(){}

    @Override
    public String toString(){
        return "Customer " + id;
    }

    public static Generator<Customer> generator = Customer::new;
}

class Teller{
    /**
     * 类型计数器
     */
    private static long counter = 1;

    /**
     * 对象标记
     */
    private final long id = counter++;

    /**
     *
     */
}