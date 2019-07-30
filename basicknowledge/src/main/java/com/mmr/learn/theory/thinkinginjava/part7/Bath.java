package com.mmr.learn.theory.thinkinginjava.part7;

public class Bath {
    private String
            s1 = "Happy",
            s2 = "Happy",
            s3,s4;
    private Soap soap;
    private int i;
    private float toy;
    public Bath(){
        System.out.println("Inside Bath()");
        s3 = "Joy";
        toy = 3.14f;
        soap = new Soap();
    }

    @Override
    public String toString() {
        return "Bath{" +
                "s1='" + s1 + '\'' +
                ", s2='" + s2 + '\'' +
                ", s3='" + s3 + '\'' +
                ", s4='" + s4 + '\'' +
                ", soap=" + soap +
                ", i=" + i +
                ", toy=" + toy +
                '}';
    }

    public static void main(String[] args) {
        Bath b = new Bath();
        System.out.println(b);
    }
}


class Soap{
    private String s;
    Soap(){
        System.out.println("Soap()");
        s = "Constructed";
    }
}
