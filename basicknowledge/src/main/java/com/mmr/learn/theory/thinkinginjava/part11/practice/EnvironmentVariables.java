package com.mmr.learn.theory.thinkinginjava.part11.practice;

import java.util.Map;
import java.util.Set;

public class EnvironmentVariables {
    public static void main(String[] args) {
        for(Map.Entry<String, String> entry : System.getenv().entrySet()){
            System.out.println(entry.getKey() + ":" + entry.getValue());
       }
    }
}
