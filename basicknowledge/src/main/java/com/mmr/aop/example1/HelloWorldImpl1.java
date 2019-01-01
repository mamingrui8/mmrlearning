package com.mmr.aop.example1;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2018年12月16日 16:18
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class HelloWorldImpl1 implements HelloWorld {
    public void printHelloWorld()
    {
        System.out.println("Enter HelloWorldImpl1.printHelloWorld()");
    }

    public void doPrint()
    {
        System.out.println("Enter HelloWorldImpl1.doPrint()");
    }
}
