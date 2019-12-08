package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock.lock()的使用场景介绍
 * 在特殊场景中，才会去显示的使用Lock对象，比如:
 * 1. 尝试着获得锁直到获取锁失败。
 * 2. 尝试着获得锁一段时间，然后放弃它
 *
 * 显示的Lock对象在加锁和释放锁方面，相对于内建的Synchronized锁来说，还赋予了我们更细颗粒度的控制力。
 * 这对于实现专有同步结构是很有用的。例如用于遍历链接列表中的节点的节点传递的加锁机制(锁耦合)，这种遍历代码必须在释放当前节点的锁之前捕获下一个节点的锁。
 * @author Charles Wesley
 * @date 2019/12/8 23:27
 */
public class AttemptLocking {
    private ReentrantLock lock = new ReentrantLock();
    private int retryCount = 0;

    /**
     * 尝试着获取锁，若获取5次后
     */
    public void untimed() {
        boolean captured = lock.tryLock();
        try {
            if(captured){
                System.out.println("成功创建临界区，在while{}内的所有代码都属于临界资源");
            }else{
                System.out.println("即便是没能成功锁，我们也可以去执行一些其他的事情，而不是阻塞的，必须等待其它线程释放掉这个锁");
            }
//            while(!captured && retryCount < 5) {
//                captured = lock.tryLock();
//                retryCount++;
//            }
            System.out.println("tryLock(): " + captured);
        }finally {
            if(captured) {
                lock.unlock();
            }
        }
    }

    public void timed() {
        boolean captured = false;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("tryLock(2, TimeUnit.SECONDS): " + captured);
        } finally {
            if (captured) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        final AttemptLocking al = new AttemptLocking();
        al.untimed();
        al.timed();
        new Thread() {
            {setDaemon(true);}
            @Override
            public void run() {
                al.lock.lock();
                System.out.println("acquired");
            }
        }.start();
        //书上只写了Thread.yield()，但这语句仅仅起到建议作用，线程调度器不一定会采纳，因此我直接将当前main线程暂停，将CPU时间片强行分给另一个线程。
        //Thread.yield();
        TimeUnit.SECONDS.sleep(1);
        al.untimed();
        al.timed();
    }
}
