package com.mmr.learn.theory.thinkinginjava.part11.practice.practice29;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 怀疑因为easyClass没有实现Comparator接口
 * 但实际上即便是实现了Comparator接口，一样会报错
 */
public class Main {
    public static void main(String[] args) {
        PriorityQueue<Object> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new EasyClass());
        priorityQueue.offer(new EasyClass());
        priorityQueue.offer(new EasyClass());
    }
}


class EasyClass {

}