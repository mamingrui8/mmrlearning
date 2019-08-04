package com.mmr.learn.theory.thinkinginjava.part10.practice7;

public class Outer {
    private String name = "123";
    private void test(){
        System.out.println("outer private test()");
    }

    class Inner{
        public void modifyOuterField(){
            Outer.this.name = "修改后的值";
            Outer.this.test();
        }

        private String ssss = "12333";
    }

    public void t2(){
        Inner innerObj = new Inner();
        innerObj.modifyOuterField();
    }

    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.t2();
        System.out.println();

    }
}
