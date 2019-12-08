package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * 自管理的Runnable任务
 *
 * 这个类从结果上看起来与继承Thread没有什么差异，但实现接口意味着预留出了一个可以继承的空位
 * 但这种做法有一个重大的问题: 通过构造函数来启动线程。
 * 很有可能导致构造函数未退出，线程却已经开始运行了，但实际上run()方法内使用的变量很可能尚未初始化完毕。
 * 比如当前类中的countDown,如果在t.start()后接上countDown=6; 执行时有可能出现:  countDown=5，run()循环一次后，countDown被重新赋值成了6，结果导致最终执行了1+6=7次
 *
 * @author Charles Wesley
 * @date 2019/12/4 23:24
 */
public class SelfManaged implements Runnable{
    private int countDown = 5;
    /**
     * 任务本身拥有Thread对象，直接通过Thread来启动任务
     */
    private Thread t = new Thread(this);

    public SelfManaged() {
        t.start();
       // countDown=6;
    }

    public String toString() {
        return Thread.currentThread().getName() + "(" + countDown + ")";
    }

    @Override
    public void run() {
        while(true) {
            System.out.println(this);
            if(--countDown == 0){
                return;
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 5; i++) {
            SelfManaged selfManaged = new SelfManaged();
        }
    }
}
