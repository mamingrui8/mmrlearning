package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * 验证自增操作是否是原子性操作
 * 请查看jvm指令
 * @author mamr
 * @date 2019/12/10 12:28
 */
public class Atomicity {
    int i;
    void f1() {
        i++;
    }

    void f2() {
        i += 3;
    }

    void f3(){
        i = 4;
    }
}
