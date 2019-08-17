package com.mmr.learn.theory.thinkinginjava.part12.practice10;

public class Demo {
    private void g() throws MyException1{
        throw new MyException1();
    }
    void f() throws MyException2{
        try {
            g();
        }catch (MyException1 e){
            throw new MyException2();
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