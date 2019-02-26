package com.mmr.learn.annotation;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月14日 23:15
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class StructionAnnotation {

    /**
     *                                      +---------------------------+
     *                                      | Completed successfully    |
     *                                      +---------------------------+
     *                                 +---->      isDone() = true      |
     * +--------------------------+    |    |   isSuccess() = true      |
     * |        Uncompleted       |    |    +===========================+
     * +--------------------------+    |    | Completed with failure    |
     * |      isDone() = false    |    |    +---------------------------+
     * |   isSuccess() = false    |----+---->      isDone() = true      |
     * | isCancelled() = false    |    |    |       cause() = non-null  |
     * |       cause() = null     |    |    +===========================+
     * +--------------------------+    |    | Completed by cancellation |
     *                                 |    +---------------------------+
     *                                 +---->      isDone() = true      |
     *                                      | isCancelled() = true      |
     *                                      +---------------------------+
     * @param args
     */
    public static void main(String[] args) {

    }
}
