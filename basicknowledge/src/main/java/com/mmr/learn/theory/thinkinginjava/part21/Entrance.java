package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * @author Charles Wesley
 * @date 2019/11/24 22:16
 */
public class Entrance {
    /*
        书本:
        1. LifeOff                  定义一个简单的任务(实现Runnable接口)
        2. MainThread               测试LifeOff
        3. BasicThreads Thread类    将Runnable对象转变为工作任务
        4. MoreBasicThreads         添加更多的线程去驱动更多的任务
        5. CacheThreadPool          使用Executor来代替在MoreBasicThread中显示地创建Thread对象。
        6. FixedThreadPool          使用FixedThreadPool代替CacheThreadPool来帮助我们管理和运行任务
        7. SingleThreadPool         可以看做是"线程数量为1的FixedThreadPool"
        8. TaskWithResult           定义一个实现了Callable接口的简单任务
        9. CallableDemo             介绍如何驱动实现了Callable接口的任务
        10.SleepTask                影响任务行为的方法——sleep() 休眠

        补充:
        1. ThreadShutDown           调用shutdown()命令后，处于悬挂队列中的任务还会得到执行吗？
     */

    /*
        疑问:
        1. SingleThreadPool是如何维护它自己的悬挂任务队列(任务等待队列)？
     */
}
