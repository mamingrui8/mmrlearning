package com.mmr.learn.thread.lesson9;

/**
 * Description: JVM字符串常量池与synchronized同步代码块的应用
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 19:49
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
     * 通过下述代码不难发现，JVM中具体String常量池的缓存功能。
     * 正是这种缓存功能导致在使用synchronized(string)同步块时，要注意常量池带来的一些例外！
     *
     * 如stringandsync项目  由于ThreadA和ThreadB调用Service类的print方法时，传入的参数都是"AAAAAAAAAAAAAAA"。根据String常量池的缓存原理，二者内存地址相同，且本例的锁在param身上，因此JVM判定ThreadA和ThreadB持有同一把锁，
     *                      显然，在ThreadA执行完成之前，不会调用ThreadB的方法，也即ThreadA和ThreadB同步执行
     *                      因此，在大多数情况下，同步synchronized代码块都不会使用String作为锁对象，而改用其他，比如new Object()实例化一个Object对象。
     */
    public static void main(String[] args) {
        String a = "aa";
        String b = "aa";
        System.out.println(a == b);
    }
}
