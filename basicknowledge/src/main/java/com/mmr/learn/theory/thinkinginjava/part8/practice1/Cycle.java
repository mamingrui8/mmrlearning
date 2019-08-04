package com.mmr.learn.theory.thinkinginjava.part8.practice1;

public class Cycle {
    protected void ride(Cycle cycle){
        System.out.println("Cycle()");
    }

    public static void main(String[] args) {
        Cycle cycle = new Cycle();
        cycle.ride(new Unicycle());
        cycle.ride(new Bicycle());
        cycle.ride(new Tricycle());
    }
}

class Unicycle extends Cycle{

}

class Bicycle extends Cycle{

}

class Tricycle extends Cycle{

}
