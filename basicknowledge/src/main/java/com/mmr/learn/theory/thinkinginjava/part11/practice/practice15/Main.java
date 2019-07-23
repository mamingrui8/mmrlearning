package com.mmr.learn.theory.thinkinginjava.part11.practice.practice15;

import net.mindview.util.Stack;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String str = "+U+n+c---+e+r+t---+a-+i-+n";

        Stack<Character> stringStack = new Stack<>();
        List<Character> chars = new ArrayList<>();
        Collections.addAll(chars, ArrayUtils.toObject(str.toCharArray()));
        //或使用以下方式
        //Collections.addAll(chars, str.chars().mapToObj(c->(char)c).toArray(Character[]::new));
        Iterator<Character> it = chars.iterator();
        operator(it, stringStack);
    }

    private static void operator(Iterator<Character> it, Stack<Character> stringStack){
        while(it.hasNext()){
            Character value = it.next();
            if(Integer.valueOf(value) == 43) { // +
                value = it.next();
                stringStack.push(value);
            }
            if(Integer.valueOf(value) == 45) { // -
                Character c = stringStack.pop();
                System.out.print(c + " ");
            }
        }
    }
}
