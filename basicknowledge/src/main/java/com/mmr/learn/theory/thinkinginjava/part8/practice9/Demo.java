package com.mmr.learn.theory.thinkinginjava.part8.practice9;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Demo {
    public static void main(String[] args) {
        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
