package com.mmr.learn.thread.lesson12.innerTest2;
import com.mmr.learn.thread.lesson12.innerTest2.OutClass.*;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月18日 18:02
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args){
        final InnerClass1 in1 = new InnerClass1();
        final InnerClass2 in2 =new InnerClass2();
        Thread t1 = new Thread(() -> {
            in1.method1(in2);
        }, "T1");
        Thread t2 = new Thread(()->{
            in1.method2();
        }, "T2");
        Thread t3 = new Thread(()->{
            in2.method1();
        },"T3");

        t1.start();
        t2.start();
        t3.start();
    }
}
