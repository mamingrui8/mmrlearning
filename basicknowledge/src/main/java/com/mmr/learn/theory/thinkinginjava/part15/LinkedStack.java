package com.mmr.learn.theory.thinkinginjava.part15;

/**
 * 内部链式存储机制
 * @author Charles Wesley
 * @date 2019/11/17 20:48
 */
public class LinkedStack<T>{
    private static class Node<U> {
        U item;
        Node<U> next;
        Node(){item = null; next=null;}
        Node(U item, Node<U> next){
            this.item = item;
            this.next = next;
        }
        boolean end(){return item == null && next == null;}
    }

    /**
     * 末端哨兵
     */
    private Node<T> top = new Node<>();

    public void push(T item){
        top = new Node<>(item, top);
    }

    public T pop(){
        T result = top.item;
        if(!top.end()){
            top = top.next;
        }
        return result;
    }

    public static void main(String[] args) {
        LinkedStack<String> lss = new LinkedStack<>();
        for(String s : "1 2 3".split(" ")){
            lss.push(s);
        }
        String s;
        while((s = lss.pop()) != null){
            System.out.println(s);
        }
    }
}
