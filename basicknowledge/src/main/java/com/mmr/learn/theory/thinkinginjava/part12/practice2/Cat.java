package com.mmr.learn.theory.thinkinginjava.part12.practice2;

public class Cat {
    public static void main(String[] args) {
        Object obj = null;
        try {
            int code = obj.hashCode();
        }catch (NullPointerException e){
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
