package com.mmr.learn.string.lesson1;

/**
 * Description: String intern()
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年03月12日 17:31
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
     *  String.intern() 方法的作用是什么？
     *  答: intern()是用来返回常量池中字符串的。若常量池中已经存在该字符串，则直接返回常量池中该对象的引用，否则在常量池中加入该对象，然后再返回引用。
     *
     *  来看看官方对intern()的
     *      Now lets understand how Java handles these strings. When you create two string literals:
     *      String name1 = "Ram";
     *      String name2 = "Ram";
     *      In this case, JVM searches String constant pool for value "Ram", and if it does not find it there then it allocates a new memory space and store value "Ram"
     *      and return its reference to name1. Similarly, for name2 it checks String constant pool for value "Ram" but this time it find "Ram" there so it does nothing simply
     *      return the reference to name2 variable. The way how java handles only one copy of distinct string is called String interning.
     *
     */
    public static void main(String[] args){

    }
}
