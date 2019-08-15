package com.mmr.learn.theory.thinkinginjava.part12.practice3;

import java.util.Scanner;

public class Cat {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            int number = 0;
            int[] arrays = new int[]{1,2,3};
            while(scanner.hasNext()){
                number = Integer.parseInt(scanner.nextLine());
                break;
            }
            System.out.println(arrays[number]);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("捕获ArrayIndexOutOfBoundsException异常...");
            e.printStackTrace();
        }

    }
}
