package com.mmr.learn.thread.lesson.lesson15;

/**
 * Description: 方法join的使用
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月26日 17:39
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
     * 为什么要用join?
     * 答: 主线程中创建子线程后，如果子线程中需要进行大量的运算，主线程往往将早于子线程结束。这时，如果主线程想等待子线程结束之前结束，
     *     比如子线程处理一个数据，主线程要取得子线程的数据，这个时候就要用到join()方法了。
     *     join()方法的作用是等待线程对象销毁。
     *
     *  <t1> 抛砖引玉  让我们来看看join的使用场景
     *       思考: 由于MyThread的执行时间不确定，导致main线程执行完毕后仍然获取不到MyThread run()内的值
     *  <t2> join()方法可以解决t1的问题。
     *       思考: x.join()相当于一个阻塞函数，使当前线程无限期阻塞，等待线程x执行完毕并销毁后再继续执行线程x后面的代码。
     *
     *       重大区别: join()和synchronized()之间的区别
     *       join()内部实现原理是使用wait()方法进行等待，而synchronized()则是用"对象监视器"的原理进行同步。
     *
     */
}
