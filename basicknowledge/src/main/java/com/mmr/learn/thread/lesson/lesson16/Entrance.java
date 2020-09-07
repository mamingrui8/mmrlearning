package com.mmr.learn.thread.lesson.lesson16;

/**
 * Description: 方法join()与异常
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年03月01日 9:43
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
     * t1 在join()的过程中，如何当前线程被中断，则当前线程会出现异常
     *    思考: 当join()遇见interrupt()时，会报错java.lang.InterruptedException
     *          但t1并不会停止，因为线程1仍然在执行。
     *
     * t2 方法join(long)的使用   long参数时设定等待时间
     *    思考: 原本MyThread要在5秒后才会执行完毕，但join(2000)意味着当前线程只有等待2秒的耐心，2秒后不再等待，继续执行。
     *
     *    sleep()和join()方法有什么区别呢？
     *    猜想: sleep()不会释放锁，但join()源码的本质是wait()，会释放锁
     * t3 口说无凭，让我们来看看sleep()到底会不会释放锁
     *    思考: 执行后，确实发现在ThreadA持有锁的时候，sleep()时并不会释放锁。
     * t4
     *    有以下三种可能产生的结果:
     *   【结果1】
     *    begin A ThreadName=Thread-1  1551425887430
     *    end A ThreadName=Thread-1  1551425892432
     *    main end 1551425887430
     *    begin B ThreadName=Thread-0  1551425892432
     *    end B ThreadName=Thread-0  1551425897433
     *
     *    Step1: b.join(2000)抢到ThreadB线程锁  注意: 抢到了锁只是有了执行synchronized public void run()的能力，真正执行run()的时机还是CPU说了算。
     *           在这短暂的2秒钟内，main线程是阻塞的，b线程的run()没有执行。2秒后，ThreadB的锁被释放
     *    Step2: ThreadA抢到锁，并开始运行它自己的run()，打印ThreadA begin 并且sleep(5000)  在这5秒内，ThreadB肯定是没办法执行了(锁都没了)
     *    Step3: 这时b.join(2000)和ThreadB开始争抢锁，而join()再一次抢到了锁，但发现long delay = millis - now == 0，因此只得放弃锁，释放后main线程打印main end。
     *    Step4: ThreadB抢到锁并打印Thread begin
     *    Step5: 5秒后，ThreadB打印Thread end
     *
     *   【结果2】
     *    begin A ThreadName=Thread-1  1551425887430
     *    end A ThreadName=Thread-1  1551425892432
     *    begin B ThreadName=Thread-0  1551425892432
     *    end B ThreadName=Thread-0  1551425897433
     *    main end 1551425887430
     *
     *    Step1: b.join(2000)方法先抢到B锁，然后将B锁释放
     *    Step2: ThreadA抢到锁，打印ThreadA begin 并且sleep(5000)
     *    Step3: ThreadA打印ThreadA end并释放锁
     *    Step4: 这时b.join(2000)和ThreadB开始争抢锁， 而ThreadB抢到了锁，执行完sleep(5000)后释放锁
     *    Step5: main end最后输出
     *
     *    【结果3】
     *    begin A ThreadName=Thread-1  1551425887430
     *    end A ThreadName=Thread-1  1551425892432
     *    begin B ThreadName=Thread-0  1551425892432
     *    main end 1551425887430
     *    end B ThreadName=Thread-0  1551425897433
     *
     *    Step1: b.join(2000)方法先抢到B锁，然后将B锁释放
     *    Step2: ThreadA抢到锁，打印ThreadA begin 并且sleep(5000)
     *    Step3: ThreadA打印ThreadA end并释放锁
     *    Step4: 这时b.join(2000)和ThreadB开始争抢锁， 而b.join(2000)抢到了锁，发现时间已经过去，立刻释放锁
     *    Step5: ThreadB拿到锁并执行run()方法，打印ThreadB begin
     *    Step6: main线程异步输出main end
     *    Step7: ThreadB打印ThreadB end
     *
     *    疑问:
     *    //TODO main线程和ThreadA以及ThreadB都是异步执行的，除了b.join()外，我想不出任何能让main线程等待的理由。那为什么main线程不是除b.join()以外最先执行完毕的线程呢？？？
     *    //     难道多个子线程之间的调度需要依赖父线程，因此子线程执行结果不明朗之前，父线程不能退出？
     *
     */
}
