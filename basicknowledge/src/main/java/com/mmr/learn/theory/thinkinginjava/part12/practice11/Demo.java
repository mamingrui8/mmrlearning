package com.mmr.learn.theory.thinkinginjava.part12.practice11;

public class Demo {
    private void g() throws MyException1 {
        throw new MyException1();
    }
    void f() throws MyException2 {
        try {
            g();
        }catch (MyException1 e){
            RuntimeException re = new RuntimeException();
            re.initCause(e);
            throw re;
            //或  throw new RunTimeException(e);
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        try {
            demo.f();
        } catch (MyException2 myException2) {
            myException2.printStackTrace();
        }
    }
}

class MyException1 extends Exception{

}

class MyException2 extends Exception{

}