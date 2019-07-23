package com.mmr.learn.theory.thinkinginjava.part11.practice;

import java.util.*;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        System.out.println("什么都不做，默认的排序将使用对象在队列中的自然顺序");
        Random random = new Random(47);
        for(int i=0; i< 10; i++){
            priorityQueue.offer(random.nextInt(10 + i));
        }
        QueueDemo.printQ(priorityQueue);
        System.out.println();

        System.out.println("队列允许存储重复元素");
        List<Integer> integers = Arrays.asList(25, 22, 20, 18, 14, 9, 3, 1, 1, 2, 3);
        priorityQueue = new PriorityQueue<>(integers);
        QueueDemo.printQ(priorityQueue);
        System.out.println();

        System.out.println("可以自行提供Comparator对象来改变顺序");
        priorityQueue = new PriorityQueue<>(integers.size(), Collections.reverseOrder());
        priorityQueue.addAll(integers);
        QueueDemo.printQ(priorityQueue);

        System.out.println();
        String fact = "EDUCTION SHOULD ESCHEW OBFUSCATION";
        List<String> strings = Arrays.asList(fact.split(" "));
        PriorityQueue<String> stringPQ = new PriorityQueue<>(strings);
        QueueDemo.printQ(priorityQueue);

        System.out.println();
        stringPQ = new PriorityQueue<>(strings.size(), Collections.reverseOrder());
        stringPQ.addAll(strings);
        QueueDemo.printQ(priorityQueue);
        System.out.println();

        System.out.println("使用set去掉重复数据，再通过set初始化Queue");
        Set<Character> characterSet = new HashSet<>();
        fact.chars().mapToObj(c -> (char)c).forEachOrdered(characterSet::add);
        PriorityQueue<Character> characterPriorityQueue = new PriorityQueue<>(characterSet);
        QueueDemo.printQ(characterPriorityQueue);
    }
}
