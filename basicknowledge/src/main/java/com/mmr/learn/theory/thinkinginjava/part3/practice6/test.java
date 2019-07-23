package com.mmr.learn.theory.thinkinginjava.part3.practice6;

import com.mmr.learn.theory.thinkinginjava.part3.practice5.Dog;

public class test {
    public static void main(String[] args) {
        Dog dog1 = new Dog("spot", "Ruff!");
        Dog dog2 = new Dog("scruffy", "Wurf!");
        Dog dog3 = dog1;

        System.out.println(dog3 == dog1);
        System.out.println(dog3.equals(dog1));


        System.out.println("" + true);
    }
}
