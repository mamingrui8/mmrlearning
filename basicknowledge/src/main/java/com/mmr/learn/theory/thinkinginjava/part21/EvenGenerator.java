package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * 实现IntGenerator的案例1
 * Tips:
 * 1. 在当前代码中标注了Danger point here.这是因为多线程运行多个任务的场景下，一个任务有可能在另一个任务对第一个
 *    currentEvenValue做了递增操作后，但没有执行第二个递增操作之前，执行了return currentEvenValue;语句
 * 2. 递增程序自身也需要多个步骤，并且在递增的过程中任务可能被挂起——也就是说，在Java中，递增不是原子性操作。
 *    因此，如果不保护任务，即便是单一的递增也不是安全的。
 *
 * @author Charles Wesley
 * @date 2019/12/8 21:04
 */
public class EvenGenerator extends IntGenerator{
    private int currentEvenValue = 0;
    /**
     * 生成偶数数值
     */
    @Override
    public int next() {
        ++currentEvenValue; //Danger point here!
        //Thread.yield();
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
    }
}
