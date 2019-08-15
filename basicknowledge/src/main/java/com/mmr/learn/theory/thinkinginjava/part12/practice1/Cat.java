package com.mmr.learn.theory.thinkinginjava.part12.practice1;

public class Cat {
    public static void main(String[] args) {
        try{
            throw new Exception("我报错了！");
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause().getMessage());
        }finally {
            System.out.println("finally");
        }
    }
}
