package com.mmr.genericity;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-08-29
 * Time: 16:56
 */
public class Test2 {
    public static void main(String[] args){
        //调用一个泛型方法
        String middle1 = Test2.<String>getMiddle("马", "Q.", "Public"); //<String>可以不写，编译器通过使用String[]与泛型类型T[]进行匹配，并推断出T一定是String
        //double middle2 = Test2.getMiddle(3.14, 1729, 0); //【报错】编译器非常想为double和Integer寻找共同的超类，但仅找到了两个超类:"Number"和"Integer"对象。
                                                           //可以采取的补救措施只能是将所有的参数都写成double类型 (详见middle3)
        double middle3 = Test2.getMiddle(3.14, 1729D,0D);
        //double middle4 = Test2.getMiddle("Hello",0,null); //其实有许多时候，我们可以通过"故意错误"的方式，找到将结果赋予给结果的正确类型！
    }

    /**
     * 1. <T>表示类型变量，类型变量放置在修饰(比如这里的public static)的后面,返回类型(比如这里的T)的前面。
     */
    public static <T> T getMiddle(T... a){
        return a[a.length / 2];
    }
}
