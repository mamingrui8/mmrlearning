package com.mmr.learn.theory.thinkinginjava.part12.practice18;

/**
 * 工厂模式与接口
 */
public class CycleFactory {
    public static void main(String[] args) {
        Cycles.handle(new UnicycleFactory());
        Cycles.handle(new BicycleFactory());
        Cycles.handle(new TricycleFactory());
    }
}

class Cycles{
    public static void handle(Factory factory){
        Cycle cycle = factory.getCycle();
        cycle.ride();
    }
}

interface Factory{
    Cycle getCycle();
}

class UnicycleFactory implements Factory{
    public Cycle getCycle(){
        return new Unicycle();
    }
}

class BicycleFactory implements Factory{
    public Cycle getCycle(){
        return new Bicycle();
    }
}

class TricycleFactory implements Factory{
    public Cycle getCycle(){
        return new Tricycle();
    }
}

interface Cycle{
    void ride();
}

class Unicycle implements Cycle{
    @Override
    public void ride(){
        System.out.println("Unicycle ride()...");
    }
}

class Bicycle implements Cycle{
    @Override
    public void ride(){
        System.out.println("Bicycle ride()...");
    }
}

class Tricycle implements Cycle{
    @Override
    public void ride(){
        System.out.println("Tricycle ride()...");
    }
}
