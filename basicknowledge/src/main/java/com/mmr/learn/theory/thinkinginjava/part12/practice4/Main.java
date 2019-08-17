package com.mmr.learn.theory.thinkinginjava.part12.practice4;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            throw new MMRException("我是自定义异常！");
        }catch(MMRException mmr){
            System.out.println(mmr.getMessage());
            System.out.println(mmr.toString());
            mmr.printStackTrace();
            mmr.fillInStackTrace();
            System.out.println(mmr.getClass().getName());
            System.out.println(mmr.getClass().getSimpleName());
            mmr.printStackTrace(System.out);

        }
    }
}
