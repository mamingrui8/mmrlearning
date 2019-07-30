package com.mmr.learn.theory.thinkinginjava.part7;

class WaterSource {
    private String s;
    WaterSource() {
        System.out.println("WaterSource()");
        s = "Constructed";
    }

    public String toString(){
        return s;
    }
}


public class SprinklerSystem{
    private String value1, value2, value3;
    private WaterSource waterSource;

}