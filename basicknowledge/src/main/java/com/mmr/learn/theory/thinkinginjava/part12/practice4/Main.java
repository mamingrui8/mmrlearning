package com.mmr.learn.theory.thinkinginjava.part12.practice4;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            throw new MMRException("我是自定义异常！");
        }catch(MMRException mmr){
            mmr.printStackTrace();
        }
    }
}
