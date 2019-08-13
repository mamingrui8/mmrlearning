package com.mmr.learn.theory.thinkinginjava.part9.practice3;

public class Main {
    public static void main(String[] args) {
        Sun sun = new Sun();
        sun.print();

        /*
         * 输出
         * 0
         * 10
         *
         * 首先调用父类构造函数，执行print()，由于子类重写了print()，因此此时调用的是子类的print()方法。
         * 遗憾的是，当前子类尚未初始化完毕，尚未向成员变量赋值，因此number=0 (毕竟已经申请了内存空间且赋予了二进制0值)
         */
    }
}
