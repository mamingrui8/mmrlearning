package com.mmr.exception;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-08-25
 * Time: 16:45
 */
public class Test4 {
    public static int factorial(int n){
        System.out.println("factorial(" + n + ")");
        Throwable t = new Throwable();
        StackTraceElement[] frames = t.getStackTrace();
        for(StackTraceElement f : frames){
            System.out.println(f);
        }
        int result;
        if(n <= 1) result = 1;
        else result = n * factorial(n-1);
        System.out.println("return " + result);
        return result;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter n: ");
        int n = scanner.nextInt();
        factorial(n);
    }
}
