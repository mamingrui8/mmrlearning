package com.mmr.genericity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-09-10
 * Time: 23:40
 * 泛型类型擦除
 * https://www.cnblogs.com/drizzlewithwind/p/6101081.html
 */
public class Test10 {
    /*
        疑问:
             1. 文章指出，编译后，类型参数都会被擦除，那么为什么我这边反编译后Demo1中仍然存在List<Integer>呢?
     */
}

class Demo1{
    /*
        'listMethod(List<String>)' clashes with 'listMethod(List<Integer>)'; both methods have same erasure
        分析: 擦除后，二者理论上都是List<E>   【注意: 其实并非如此， 】
     */
    //public void listMethod(List<String> list1){}
    public void listMethod(List<Integer> list2){}

}