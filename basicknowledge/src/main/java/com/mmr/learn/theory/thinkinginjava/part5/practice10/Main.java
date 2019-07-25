package com.mmr.learn.theory.thinkinginjava.part5.practice10;

public class Main {
    public void finalize(){
        System.out.println("开始finalize()了！");
    }

    public static void main(String[] args) {
        Main main = new Main();
        try {
            main.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
class A{

}