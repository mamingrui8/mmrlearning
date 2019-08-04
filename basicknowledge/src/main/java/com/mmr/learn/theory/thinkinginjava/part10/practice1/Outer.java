package com.mmr.learn.theory.thinkinginjava.part10.practice1;

public class Outer {
    private String name;

    public Outer(String name){
        this.name = name;
    }
    public class Inner{
        public String toString(){
            return Outer.this.name;
        }
    }

    public Inner getInner(){
        return new Inner();
    }

    public static void main(String[] args) {
        Outer outer = new Outer("马明瑞");
        Outer.Inner innerObj = outer.getInner();
        System.out.println(innerObj);

        Outer.Inner innerObj2 = outer.new Inner();
    }
}
