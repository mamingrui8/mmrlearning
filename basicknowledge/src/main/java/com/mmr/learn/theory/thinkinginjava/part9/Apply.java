package com.mmr.learn.theory.thinkinginjava.part9;

import java.util.Arrays;

public class Apply {
    public static void process(Processor p, Object s){
        System.out.println("Using Processor " + p.name());
        System.out.println();
    }
    public static void main(String[] args) {
        String s = "Disagreement";
        process(new UpCase(), s);
        process(new DownCase(), s);
        process(new Splitter(), s);
    }
}

class Processor{
    public String name(){
        return getClass().getSimpleName();
    }

    Object process(Object input){
        return input;
    }
}

class UpCase extends Processor{
    String process(Object input){
        return ((String)input).toUpperCase();
    }
}

class DownCase extends Processor{
    String process(Object input){
        return ((String)input).toLowerCase();
    }
}

class Splitter extends Processor{
    String process(Object input){
        return Arrays.toString(((String)input).split(" "));
    }
}