package com.mmr.learn.thread.lesson13;

/**
 * Description: volatile关键字
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月19日 9:58
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
     *  volatile的作用: 使变量在多个线程间可见
     *
     *  t99项目: volatile关键字与死循环
     *  t10项目: 解决t99项目中的死循环
     *
     *  书上说，t10代码运行在64bit JVM上时会出现死循环，解决方法是使用volatile关键字
     *  关键字volatile的作用是强制从公共堆中取得变量的值，而不是从线程私有的数据栈中取得变量的值。
     *
     *  t16项目 出现死循环
     *          这个问题其实就是私有栈中的值和公有堆中的值不同步造成的。解决问题的方法就是volatile关键字。
     *          volatile的主要作用就是当线程访问isRunning这个变量时，强制从公共堆中取得变量的值。
     *
     *  使用volatile最大的好处在于增加了实例变量在多个线程之间的可见性(毕竟都在主内存中读写数据、都在公共堆中操作数据)
     *  【但是】 volatile关键字最致命的缺点是不支持原子性。
     *
     *  volatile与synchronized关键字的比较:
     *  1. 关键字volatile是线程同步的轻量级实现，所以volatile性能肯定比synchronized要好，并且volatile只能修饰变量。
     *  2. 多线程访问volatile不会发生阻塞，而synchronized会出现阻塞。
     *  3. volatile能保证数据的可见性，但不能保证原子性；而synchronized可以保证数据的原子性，也可以间接保证数据的可见性。
     *  4. volatile解决的是线程间数据的可见性，synchronized解决的是多个线程之间访问同一个资源的同步性。
     *
     *  线程安全包含原子性和可见性这两个方面。Java的同步机制都是围绕着这两个方面来确保线程安全的。
     *
     *  volatileTestThread项目 讨论volatile的非原子性
     *
     * 在线程运行的过程中，volatile能够获取到公共实例变量最新的值
     *
     *
     *
     *  TODO 实际运行过程中，公共堆和私有栈中的数据到底是如何交互的？
     *  比如针对
     *  public classMyThread extends Thread{
     *      public static boolean flag1 = true;
     *      private static boolean flag2 = false;
     *
     *      public void setFlag2(boolean flag2){
     *             this.flag2 = flag2;
     *      }
     *  }
     *  flag1和flag2到底是存于公共堆还是私有栈中？亦或者有类似"副本"的概念，二者都存在？
     *  线程到底是从哪里获取这些值的？
     *
     *  猜测公共堆和私有栈中都有。
     *
     *  上述疑问请参考图---【变量在内存中的工作过程.png】
     *
     *  根据图例，推测出以下结论:
     *  Thread.start()后，线程执行时所需的数据已经由"主工作内存"(堆-公有)同步到"线程工作内存"(栈-私有)
     *  此时，无论如何修改位于"主工作内存"内的数据，由于线程已经read和load，因而不会发生任何变化，也就是私有变量和共有变量不同步。
     *  所以计算出来的结果会和预期不一样，也就出现了非线程安全问题。
     *
     *  对于用volatile修饰的变量，JVM虚拟机只是保证从主存中加载到线程工作内存中的值是最新的，也就是read和load阶段的值是最新的，
     *  但后续主存中数据再次发生改变时，被volatile修饰的变量就没办法保证数据同步了，原因在于已经经过了read和load阶段，在离开本次
     *  工作内存数据处理流程并同步至主线程之前，工作内存不会再次加载这份数据了。这就是volatile无法保证原子性的原因。
     *
     *  再上升一步讨论，volatile关键字解决的是变量读时的可见性，但无法保证后续的原子性，因此对于多个线程访问同一个实例变量仍然
     *  需要用到synchronized变量。
     *
     *
     *
     *
     *  那是不是一定要使用synchronized关键字才能实现同步呢？当然不是，还可以使用AtomicInteger原子类实现
     *  请参考AtomicIntegerTest项目
     *        此项目不用synchronized锁也能实现同步的功能，原子性保证了在
     *        主工作内存->线程工作内存->read->load->use->assign->store->write->主工作内存
     *        这一套流程中进行操作时不会受到任何影响。
     *
     *  那是不是原子性就能保证同步了呢？当然不是，原子性只能保证一定程度的同步
     *  请参考atomicIntegerNoSafe项目
     *              Thread-0 加了100之后的值是：100
     *              Thread-3 加了100之后的值是：400
     *              Thread-0 加了1之后的值是： 401
     *              Thread-2 加了100之后的值是：300
     *              Thread-2 加了1之后的值是： 403
     *              Thread-1 加了100之后的值是：200
     *              Thread-1 加了1之后的值是： 404
     *              Thread-3 加了1之后的值是： 402
     *              Thread-4 加了100之后的值是：504
     *              Thread-4 加了1之后的值是： 505
     *              505
     *              那么问题来了: 虽然执行最终的结果是正常的，但线程执行的顺序是乱序的，请问这是为什么？
     *              答: atomicInteger原子类只能保证自身执行的原子性，即atomicLong.addAndGet()这些方法是原子性的，
     *                  但并不能保证public void addNum()方法的原子性。因此addNum()方法在执行时是异步的。
     *              [解决方案]  很简单，为addNum()加上synchronized，使该方法成为同步的即可。
     *
     */
}
