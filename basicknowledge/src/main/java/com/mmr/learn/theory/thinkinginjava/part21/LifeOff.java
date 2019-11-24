package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * 定义一个最基本的多线程任务
 * @author Charles Wesley
 * @date 2019/11/24 22:17
 */
public class LifeOff implements Runnable{
    protected int countDown = 10;
    private static int taskCount = 0;
    /**
     * id用于区分任务的多个实例
     */
    private final int id = taskCount++;
    public LifeOff() {}
    public LifeOff(int countDown){
        this.countDown = countDown;
    }

    public String status(){
        return "#" + id + "(" + (countDown > 0 ? countDown : "LifeOff!") + "). ";
    }

    @Override
    public void run() {
        while(countDown-- > 0){
            System.out.print(status());
            //建议线程调度器停止运行当前线程，切换线程并将CPU时间片分配给其它线程。 仅仅只是建议而已
            Thread.yield();
        }
    }
}
