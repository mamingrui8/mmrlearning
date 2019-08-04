package com.mmr.learn.theory.thinkinginjava.part10.practice12;

public class A {
    public static void main(String[] args) {
        B b_1 = new B();
        b_1.say();

        B b_2 = B.getInstance();
        b_2.say();
    }

}

class B {
    public void say(){
        System.out.println("test()");
    }

    public static B getInstance(){
        return new B(){
            {}
            {}
            {}
            public void say(){
                System.out.println("modify()");
            }
        };
    }
}
