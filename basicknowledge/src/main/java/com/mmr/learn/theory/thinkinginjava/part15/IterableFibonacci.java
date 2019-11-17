package com.mmr.learn.theory.thinkinginjava.part15;

import java.util.Iterator;

/**
 * 在Fibonacci的基础上实现Iterable
 * 实现Iterable进行迭代时，最重要的一点是: 定义一个临界值，这样在hasNext()时才知道什么时候该停下来
 * 使用了适配器模式(类模式)
 * @author Charles Wesley
 * @date 2019/11/17 22:38
 */
public class IterableFibonacci extends Fibonacci implements Iterable<Integer>{
    //末端哨兵
    private int n;
    public IterableFibonacci(int count){
        n = count;
    }
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return n > 0;
            }

            @Override
            public Integer next() {
                n--;
                return IterableFibonacci.this.next();
            }
            @Override
            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
    }

    public static void main(String[] args) {
        //把Fibonacci队列中的元素迭代的遍历出来
        for(int i:new IterableFibonacci(18)){
            System.out.println(i + " ");
        }
    }
}
