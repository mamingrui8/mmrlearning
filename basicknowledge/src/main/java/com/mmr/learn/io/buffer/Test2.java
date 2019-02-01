package com.mmr.learn.io.buffer;

import java.nio.ByteBuffer;

/**
 * Description: 本例说明: buffer.clear()本质上是清空了pos和lim，并没有对数据本身做相应的清除
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月06日 17:21
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Test2 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put((byte)3);
        buffer.put((byte)2);

        buffer.clear();

        System.out.println(buffer.get(0));

    }
}
