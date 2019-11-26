package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Charles Wesley
 * @date 2019/11/26 23:31
 */
public class SingleThreadPool {
    /*
        SingleThreadPool可以看作是线程数量为1的FixedThreadPool
        这对于我们希望在另一个线程中长期运行的任何事物(长期存活的任务)来说，非常有用。并且对于那些希望在线程中
        运行的短任务也同样很方便
        比如:
        1. 监听进入的套接字连接的任务
        2. 更新本地或远程日志
        3. 事件分发
     */

    public static void main(String[] args) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        for(int i = 0; i < 2; i++){
            es.execute(new LifeOff());
        }
        es.shutdown();
        for(int i = 0; i < 5; i++){
            es.execute(new LifeOff());
        }
    }
}
