package com.mmr.learn.theory.thinkinginjava.part10;

public class TestParcel {
    public static void main(String[] args) {
        Parcel4 p = new Parcel4();
        Contents c = p.contents();
        Destination d  = p.destination("Tasmania");
    }
}
