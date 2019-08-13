package com.mmr.learn.theory.thinkinginjava.part9.practice1;

public class Animal {
    public static void main(String[] args) {
        Rodent[] rodents = new Rodent[]{
                new Mouse(),
                new Gerbil(),
                new Hamster()
        };

        rodents[0].scream();
        rodents[1].scream();
        rodents[2].scream();
    }
}

abstract class Rodent{
    protected abstract void scream();
}

class Mouse extends Rodent {
    public void scream(){
        System.out.println("Mouse 尖叫");
    }
}

class Gerbil extends Rodent {
    public void scream(){
        System.out.println("Gerbil 尖叫");
    }
}

class Hamster extends Rodent {
    public void scream(){
        System.out.println("Hamster 尖叫");
    }
}
