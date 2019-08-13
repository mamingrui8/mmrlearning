package com.mmr.learn.theory.thinkinginjava.part8.practice17;

public class Cycle {
    protected void ride(Cycle cycle){
        System.out.println("Cycle()");
    }

    public static void main(String[] args) {
        Cycle[] cycles = new Cycle[]{
                new Unicycle(),
                new Bicycle(),
                new Tricycle()
        };

        //cycles[0].balance(); //can not resolve method balance()
        //cycles[1].balance();
        //cycles[2].balance();

        ((Unicycle)cycles[0]).balance();
        ((Bicycle)cycles[1]).balance();
        //((Tricycle)cycles[2]).balance(); //can not resolve method balance()
        ((Unicycle)cycles[2]).balance();
    }
}

class Unicycle extends Cycle {
    public void balance(){
        System.out.println("Unicycle balance()");
    }
}

class Bicycle extends Cycle {
    public void balance(){
        System.out.println("Bicycle balance()");
    }
}

class Tricycle extends Cycle {

}
