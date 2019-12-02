package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 专门用于创建后台线程的线程池
 * @author Charles Wesley
 * @date 2019/11/28 23:31
 */
public class DaemonThreadPoolExecutor extends ThreadPoolExecutor {
    /**
     * 由于ThreadPoolExecutor没有默认的构造函数，因此必须在派生类中手动的调用其有参构造方法以便ThreadPoolExecutor初始化。
     */
    public DaemonThreadPoolExecutor(){
        super(0, Integer.MAX_VALUE, 60L,
                TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new DaemonThreadFactory());
    }
}
