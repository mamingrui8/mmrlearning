package com.mmr.learn.theory.thinkinginjava.part10.practice6.t3;

import com.mmr.learn.theory.thinkinginjava.part10.practice6.t1.Inter;
import com.mmr.learn.theory.thinkinginjava.part10.practice6.t2.Outer;

public class Out3 extends Outer {
    public Inter getInner(){
        Out3 out3 = new Out3();
        System.out.println(out3.i);
        Inter inter = new Inner();
        return inter;
    }
}
