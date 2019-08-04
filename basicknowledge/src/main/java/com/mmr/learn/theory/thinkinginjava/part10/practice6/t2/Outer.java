package com.mmr.learn.theory.thinkinginjava.part10.practice6.t2;

import com.mmr.learn.theory.thinkinginjava.part10.practice6.t1.Inter;

public class Outer {
    protected class Inner implements Inter {

        public Inner(){

        }
        @Override
        public void v1() {
            System.out.println("v1()");
        }
    }

    protected int i = 10;
}
