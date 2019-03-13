package com.mmr.learn.jvm;

public class Entrance {
    /**
     *  t1  查看栈的深度。 对应问题——什么是Java虚拟机栈的深度？
     *      执行结果:
     *                 第一次执行  19956
     *                 第二次执行  20175
     *                 第三次执行  18820
     *      思考: 不难发现，Java虚拟机栈的深度随时都在发生变化，栈的深度受栈帧大小影响
     *            如果想在Idea中运行代码时设置较大的栈帧，我们可以在VM options中写入:  -Xss10m
     *            再次运行的结果: 624631
     *
     *   t2 TODO 详细的描述了一个常见的类中的元素在内存中的加载机制
     *
     *   t3 TODO 直接内存与堆内存的比较
     *      https://blog.csdn.net/leaf_0303/article/details/78961936
     *
     *   疑惑:
     *   1. 在方法内部通过new关键字创建的对象放在堆中还是java虚拟机栈
     *   2. 已经被虚拟机加载的类信息有哪些？
     */
    public static void main(String[] args){
        String s = "123";
        System.out.println(s.intern());
    }

}
