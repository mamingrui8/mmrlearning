package com.mmr.learn.theory.thinkinginjava.part12.practice25;

/**
 * 异常匹配
 */
public class Demo {
    public static void main(String[] args) {
        A a = new C();
        try {
            a.t1();
        } catch (FirstException e) {
            e.printStackTrace();
        }
    }
}

class FirstException extends Exception{ }

class SecondException extends FirstException{ }

class ThirdException extends SecondException{ }

class A {
    public void t1() throws FirstException{
        throw new FirstException();
    }
}

class B extends A{
    @Override
    public void t1() throws SecondException{
        throw new SecondException();
    }
}

class C extends B{
    @Override
    public void t1() throws ThirdException{
        throw new ThirdException();
    }
}