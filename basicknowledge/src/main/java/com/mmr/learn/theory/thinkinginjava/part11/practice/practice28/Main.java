package com.mmr.learn.theory.thinkinginjava.part11.practice.practice28;

import java.util.PriorityQueue;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random(47);
        PriorityQueue<Double> priorityQueue = new PriorityQueue<>();

        for(int i =0; i< 10; i++){
            priorityQueue.offer(rand.nextDouble());
        }

        consume(priorityQueue);
    }

    /**
     * 使用poll()移除并显示元素
     */
    private static void consume(PriorityQueue<Double> priorityQueue){
        while(priorityQueue.peek() != null){
            Double number = priorityQueue.poll();
            System.out.println(number + " ");
        }
        System.out.println();
    }
}
