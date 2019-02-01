package com.mmr.learn.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-08-25
 * Time: 20:56
 */
public class Test6 {
    public static void main(String[] args){
        Test1(-1);
    }
    /*
        1. assert默认是关闭的,若想开启，请在vm中增加-en或-enableassertions
                2. assert 条件: 表达式      条件若不成立，表达式会产生一个消息字符串并传递给AssertionError (java.lang.AssertionError)
                */

public static void Test1(int x){
        assert x>=0 : "x<0，传入的数值不符合标准";
        double y = Math.sqrt(x);
        System.out.print(y);
        }
}
