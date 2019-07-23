package com.mmr.learn.theory.source.java.util.question;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * c.toArray might (incorrectly) not return Object[] (see 6260652)官方Bug
 */
public class Demo1 {
    /**
     * 测试：c.toArray might (incorrectly) not return Object[] (see 6260652) 这个官方 bug
     * collection.toArray()是否返回Object[]由其子类的具体实现而定
     *
     * ArrayList
     *     public Object[] toArray() {
     *         return Arrays.copyOf(elementData, size);
     *     }
     *
     * LinkedList
     *     public Object[] toArray() {
     *         Object[] result = new Object[size];
     *         int i = 0;
     *         for (Node<E> x = first; x != null; x = x.next)
     *             result[i++] = x.item;
     *         return result;
     *     }
     *
     * LinkedList中，首先会创建一个空的Object数组，接着把数据依次存储到数组中(T t -> Object obj)。由于Object是所有类型的父类，
     * 因此类型自动向上转型(小转大)，从而在数组中的每一个元素的类型一定是Object。
     *
     * ArrayList中，虽然看起来方法的返回值仍然是Object[]，但由于使用了Arrays.copyOf(elementData, size)
     *              public static <T> T[] copyOf(T[] original, int newLength) {
     *                     return (T[]) copyOf(original, newLength, original.getClass());
     *              }
     * 带有泛型，因此copyOf()返回的数组元素类型是由使用者决定的
     */
    @Test
    public void test1() {
        List<String> ss = new LinkedList<String>();             // LinkedList toArray() 返回的本身就是 Object[]
        ss.add("123");
        Object[] objs = ss.toArray();
        objs[0] = new Object();

        // 此处说明了：c.toArray might (incorrectly) not return Object[] (see 6260652)
        ss = new MyList<String>();
        objs = ss.toArray();
        System.out.println(objs.getClass());        // class [Ljava.lang.String;
        objs[0] = new Object();                         // java.lang.ArrayStoreException: java.lang.Object
    }

    @Test
    public void test2(){
        System.out.println(t("小").getClass());
    }

    //父类引用指向子类对象
    //Father obj = new Children();
    private Object t(String s){
        System.out.println(((Object)s).getClass());
        return (Object)s;
    }

    @Test
    public void test3(){
        Father father = new Children();
        System.out.println(father.getClass());
        father.test1();
    }

}


class MyList<E> extends java.util.ArrayList<E> {
    // toArray() 的同名方法
    public String[] toArray() {
        return new String[]{"1", "2", "3"};
    }
}

class Children extends Father{
    public void test2(){

    }
}

class Father{
    public void test1(){

    }
}