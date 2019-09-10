package com.mmr.learn.theory.thinkinginjava.part17;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Collections类的fill()方法
 */
public class FillingLists {
    public static void main(String[] args) {
        List<StringAddress> list = new ArrayList<>(
                Collections.nCopies(10, new StringAddress("Hello")) //复制10个对象，组成List集合并返回
        );
        System.out.println(list);
        Collections.fill(list, new StringAddress("World!"));
        System.out.println(list);
    }
}

class StringAddress{
    private String s;
    public StringAddress(String s){
        this.s = s;
    }
    public String toString(){
        return super.toString() + " " + s;
    }
}