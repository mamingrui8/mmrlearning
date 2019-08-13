package com.mmr.learn.theory.thinkinginjava.part8.practice9;

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

class Rodent{
    public void scream(){
        System.out.println("Rodent 尖叫");
    }
}

class Mouse extends Rodent{
    public void scream(){
        System.out.println("Mouse 尖叫");
    }
}

class Gerbil extends Rodent{
    public void scream(){
        System.out.println("Gerbil 尖叫");
    }
}

class Hamster extends Rodent{
    public void scream(){
        System.out.println("Hamster 尖叫");
    }
}
