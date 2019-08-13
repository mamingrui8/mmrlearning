package com.mmr.learn.theory.thinkinginjava.part9;

public class Filter {
    public String name(){
        return getClass().getSimpleName();
    }

    public Waveform process(Waveform input) {return input;}
}
