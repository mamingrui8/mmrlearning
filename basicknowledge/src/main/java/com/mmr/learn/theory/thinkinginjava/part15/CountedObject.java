package com.mmr.learn.theory.thinkinginjava.part15;

/**
 * @author Charles Wesley
 * @date 2019/11/24 17:26
 */
public class CountedObject {
    private static long counter = 0;
    private final long id = counter++;
    public long id(){return id;}
    public String toString(){return "CountedObject " + id;}
}
