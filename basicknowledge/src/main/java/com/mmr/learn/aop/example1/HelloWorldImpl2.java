package com.mmr.learn.aop.example1;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2018年12月16日 16:24
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class HelloWorldImpl2 implements HelloWorld
{
    public void printHelloWorld()
    {
        System.out.println("Enter HelloWorldImpl2.printHelloWorld()");
    }

    public void doPrint()
    {
        System.out.println("Enter HelloWorldImpl2.doPrint()");
    }
}
