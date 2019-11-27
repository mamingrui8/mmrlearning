package com.mmr.learn.theory.thinkinginjava.part21;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * 定制的ThreadFactory (可以用它来定制由Executor创建出的线程的各项属性！如"是否为后台线程"、"线程的名称"、""线程的优先级)
 * @author Charles Wesley
 * @date 2019/11/27 23:45
 */
public class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread t = new Thread(r);
        //把DaemonThreadFactory当做变量来设置的Executors创建出来的线程全部设置成后台线程
        t.setDaemon(true);
        t.setPriority(Thread.MAX_PRIORITY);
        return t;
    }
}
