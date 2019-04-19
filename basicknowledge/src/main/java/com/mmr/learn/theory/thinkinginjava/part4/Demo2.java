package com.mmr.learn.theory.thinkinginjava.part4;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 找出4位数的所有吸血鬼数字
 * 位数为偶数的数字，可以由一堆数字相乘而得到，而这对数字各包含乘积的一半位数的数字，其中从最初的数字中选取的数字可以任意排序。以两个0结尾的数字是不允许的。
 * 1260=21*60
 * 1827=21*87
 * 2187=21*81
 */
public class Demo2 {

    private String vampire_1(){
        List<Integer> list = Lists.newArrayList();

        for(int thousand = 0; thousand<=9; thousand++){
            for(int hundred = 0; hundred<=9; hundred++){
                for(int ten =0; ten<=9; ten++){
                    for(int digit=0; ten<=9; ten++){
                        int result1 =  thousand * 1000 + hundred * 1000 + ten * 10 + digit;
                        int result2 = (thousand * 10 + hundred) * (ten * 10 + digit);
                        //总共有12种不同的排序方式  4*3*2*1 / 2 = 12 很显然，这样做不切实际
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();
        demo2.running();
    }

    /**
     * 千分位
     */
    private int a(int i){
        return i/1000;
    }

    /**
     * 百分位
     */
    private int b(int i){
        return (i%1000)/100;
    }

    /**
     * 十分位
     */
    private int c(int i){
        return (i%100)/10;
    }

    /**
     * 个位
     */
    private int d(int i){
        return i%10;
    }

    /**
     * 组合-十位数
     */
    private int com(int i, int j){
        return (i * 10) + j;
    }

    /**
     * 计算数值
     */
    private void productTest(int i, int m, int n){
        if(m * n == i) System.out.println(i + "=" + m + "*" + n);
    }

    /**
     * 枚举运算
     */
    private void running(){
        for(int i = 1001; i < 9999; i++) {
            productTest(i, com(a(i), b(i)), com(c(i), d(i)));
            productTest(i, com(a(i), b(i)), com(d(i), c(i)));
            productTest(i, com(a(i), c(i)), com(b(i), d(i)));
            productTest(i, com(a(i), c(i)), com(d(i), b(i)));
            productTest(i, com(a(i), d(i)), com(b(i), c(i)));
            productTest(i, com(a(i), d(i)), com(c(i), b(i)));
            productTest(i, com(b(i), a(i)), com(c(i), d(i)));
            productTest(i, com(b(i), a(i)), com(d(i), c(i)));
            productTest(i, com(b(i), c(i)), com(d(i), a(i)));
            productTest(i, com(b(i), d(i)), com(c(i), a(i)));
            productTest(i, com(c(i), a(i)), com(d(i), b(i)));
            productTest(i, com(c(i), b(i)), com(d(i), a(i)));
        }
    }
}
