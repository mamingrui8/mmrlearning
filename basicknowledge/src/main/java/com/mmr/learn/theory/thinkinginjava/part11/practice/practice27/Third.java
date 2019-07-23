package com.mmr.learn.theory.thinkinginjava.part11.practice.practice27;

import java.util.Queue;

public class Third {
    public void operation(Queue queue){
        if(queue.peek() != null){
            Command command = (Command)queue.poll();
            command.operation();
        }
    }
}
