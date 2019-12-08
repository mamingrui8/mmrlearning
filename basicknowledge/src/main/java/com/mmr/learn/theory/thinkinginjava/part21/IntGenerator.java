package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * int生成器
 * @author Charles Wesley
 * @date 2019/12/8 20:31
 */
public abstract class IntGenerator {
    //由于canceled标志是boolean类型的，因此它是原子性的，即诸如赋值和返回值这样的简单操作在发生时绝对没有被中断的可能。
    private volatile boolean canceled = false;
    public abstract int next();
    public void cancel() {canceled = true;}
    public boolean isCanceled() {return canceled;}
}
