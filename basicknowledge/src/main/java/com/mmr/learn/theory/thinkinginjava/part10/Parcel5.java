package com.mmr.learn.theory.thinkinginjava.part10;

public class Parcel5 {
    public Destination destination(String s){
        class PDestination implements Destination{
            private String label;
            private PDestination(String whereTo){
                label = whereTo;
            }

            public String readLabel(){
                return label;
            }
        }
        return new PDestination(s);
    }
}
