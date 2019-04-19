package com.mmr.learn.theory.thinkinginjava.part4;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 斐波那契数列
 */
public class Demo1 {
    public static void main(String[] args) {
        Demo1 demo = new Demo1();
        System.out.println(demo.fibonacci(10));
    }

    /**
     * 斐波那契数列
     * @param i 生成个数
     */
    private String fibonacci(int i){
        List<Integer> list = Lists.newArrayList();
        int k =1;
        while(k <= i){
            if(k > 2){
                list.add(list.get(k-3) + list.get(k-2));
            }else{
                list.add(1);
            }
            k++;
        }
        return StringUtils.join(list, "、");
    }

    public static  List<String> listCommon = Lists.newArrayList();

}
