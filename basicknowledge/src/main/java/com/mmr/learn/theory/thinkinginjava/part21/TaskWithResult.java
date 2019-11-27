package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 定义一个实现了Callable接口的简单任务
 * @author Charles Wesley
 * @date 2019/11/27 9:39
 */
public class TaskWithResult implements Callable<String> {
    private int id;
    public TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "result of TaskWithResult " + id;
    }
}
