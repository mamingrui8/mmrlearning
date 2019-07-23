package com.mmr.learn.theory.thinkinginjava.part3.practice5;

public class Dog {
    private String name;

    private String says;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSays() {
        return says;
    }

    public void setSays(String says) {
        this.says = says;
    }

    public Dog(String name, String says) {
        this.name = name;
        this.says = says;
    }
}
