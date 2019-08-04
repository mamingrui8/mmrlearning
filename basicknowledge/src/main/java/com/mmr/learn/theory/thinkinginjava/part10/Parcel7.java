package com.mmr.learn.theory.thinkinginjava.part10;

public class Parcel7 {
    public Contents contents(){
        return new Contents(){
            public int value(){
                return 10;
            }

            public void t2(){
                System.out.println("t2()");
            }
        };
    }

    public static void main(String[] args) {
        Parcel7 parcel7 = new Parcel7();
        Contents contents = parcel7.contents();
    }
}
