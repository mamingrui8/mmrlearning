package com.mmr.learn.thread.lesson5;

public class Entrance {
    /**
     * 从本例可以看出，在一个synchronized同步块中调用本类的其它synchronized块代码，可以重复获取到本类的锁。
     *
     * 也即，自己可以再次获取到自己的内部锁
     * 并且，在父子类继承关系中，子类完全可以通过"可重入锁"调用父类的同步方法。
     */
}
