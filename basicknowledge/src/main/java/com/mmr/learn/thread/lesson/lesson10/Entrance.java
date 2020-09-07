package com.mmr.learn.thread.lesson.lesson10;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 20:12
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
        不难发现，twostep项目中，由于methodA死循环，且methodA与methodB持有相同的对象锁，因此methodB永远没有被执行的机会。

        改进的方法很简单: 把锁加在传入的对象，而不是this上。如twostepgrade所示。
     */
}
