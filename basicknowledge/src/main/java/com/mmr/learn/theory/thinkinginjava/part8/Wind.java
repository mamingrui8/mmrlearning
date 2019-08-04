package com.mmr.learn.theory.thinkinginjava.part8;

public class Wind extends Instrument{
    public void play(Note n){
        System.out.println("Wind.play() " + n);
    }
}

class Instrument{
    public void play(Note n){
        System.out.println("Instrument.play()");
    }
}