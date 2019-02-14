package com.mmr.learn.thread.lesson1;

/**
 * Description: yield方法
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月14日 10:48
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Thread6 {
    /*
        yield方法的作用是放弃CPU资源，将它让给其它的任务去占用CPU运行时间。
        但放弃的时间不确定，有可能刚放弃CPU又立刻获取了CPU时间片。

        经过测试得知:
        1. 直接运耗时41毫秒
        2. 加上Thread.yield()线程礼让后运行了52191毫秒
        发现，将CPU让给其它资源(任务)后会导致执行速度变慢，而且执行速度每次都不一样(第二次执行运行了50184毫秒)
        说明让出CPU资源后，线程重新获得CPU资源的时间不确定。
     */
    public static void main(String[] args){
        Thread6_1 thread6_1 = new Thread6_1();
        thread6_1.start();
    }
}

class Thread6_1 extends Thread{
    @Override
    public void run(){
        long beginTime = System.currentTimeMillis();
        int count = 0;
        for(int i=0; i< 50000000; i++){
            Thread.yield();
            count = count + i;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("用时：" + (endTime-beginTime) + "毫秒");
    }
}
