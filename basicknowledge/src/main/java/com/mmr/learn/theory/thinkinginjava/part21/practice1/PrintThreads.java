package com.mmr.learn.theory.thinkinginjava.part21.practice1;

/**
 * @author Charles Wesley
 * @date 2019/11/24 23:38
 */
public class PrintThreads {
    public static void main(String[] args) {
        for(int i=0; i<10; i++){
            new Thread(new Printer()).start();
        }
    }
}
