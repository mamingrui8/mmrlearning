package com.mmr.learn.thread.lesson.lesson6;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月14日 22:36
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
     * 用关键字Synchronized声明的方法在某些情况下是有弊端的，比如A线程需要调用一个执行时间极长的任务，那么线程B由于得不到锁，则需要等待非常长的时间才能得到执行。
     * 在这种情况下，我们就可以使用synchronized同步语句块来解决。
     *
     * t5项目用于证明Synchronized声明方法的弊端。
     * t6项目用于解决Synchronized同步代码块的弊端。
     * 解决之道:
     * 锁定真正需要同步的代码，t5项目中，getData1和getData2这两个实例变量会出现同步问题，因此只需要锁定对它们的处理即可。
     *
     * 当一个线程访问object的一个同步代码块时，另一个线程仍然能够访问该object对象中的非synchronized(this)同步代码块。这就十分方便了。
     * 显然，改造后的doLongTimeTask()方法中，没有被synchronized囊括的代码异步执行，被synchronized能囊括的代码同步执行。
     * 也即，一半异步，一半同步。
     *
     *
     *public class Task {
     *     private String getData1;
     *     private String getData2;
     *
     *     public synchronized void doLongTimeTask() {
     *         try{
     *             System.out.println("begin task");
     *             TimeUnit.SECONDS.sleep(3);
     *             getData1 = "长时间处理任务后从远程返回的值1 threadName=" + Thread.currentThread().getName();
     *             getData2 = "长时间处理任务后从远程返回的值2 threadName=" + Thread.currentThread().getName();
     *             System.out.println(getData1);
     *             System.out.println(getData2);
     *             System.out.println("end task");
     *         } catch (InterruptedException e){
     *            e.printStackTrace();
     *         }
     *     }
     * }
     *
     *
     *public class Task {
     *     private String getData1;
     *     private String getData2;
     *
     *     public synchronized void doLongTimeTask() {
     *         try{
     *             System.out.println("begin task");
     *             TimeUnit.SECONDS.sleep(3);
     *             String privateGetData1 = "长时间处理任务后从远程返回的值1 threadName=" + Thread.currentThread().getName();
     *             String privateGetData2 = "长时间处理任务后从远程返回的值2 threadName=" + Thread.currentThread().getName();
     *             synchronized(this){ //把长时间处理的任务从同步代码块中分离出来
     *                 getData1 = privateGetData1;
     *                 getData2 = privateGetData2;
     *             }
     *             System.out.println(getData1);
     *             System.out.println(getData2);
     *             System.out.println("end task");
     *         } catch (InterruptedException e){
     *            e.printStackTrace();
     *         }
     *     }
     * }
     *
     *
     *
     */

}
