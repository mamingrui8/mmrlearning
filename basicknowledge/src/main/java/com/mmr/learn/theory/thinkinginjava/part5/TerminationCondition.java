package com.mmr.learn.theory.thinkinginjava.part5;

public class TerminationCondition {
    public static void main(String[] args) {
        Book novel = new Book(true);
        novel.checkIn();
        //此时放弃引用，忘记清理对象。

        new Book(true);
        //强制进行垃圾回收并且进行终结
        System.gc();

        //本例的目的在于检查是否有书本在不再使用的情况下，没有被签入。
        //不再使用 ==== finalize()
        //也即，利用finalize()函数帮助我们检查问题。  为的是暴露出内存泄漏的问题！
    }
}
