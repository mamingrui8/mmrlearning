package com.mmr.learn.io.nio;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 使用NIO时需要依赖的缓冲区
 * <p>
 * Limit只在从缓冲区读取数据的时候才会发生变化(派上用场),在向缓冲区写入数据时没什么用
 * 读取数据时，只能读取[position, Limit]的数据
 *
 * @author mamr
 * @date 2020/9/3 3:39 下午
 */
public class Buffer {
    public static void main(String[] args) {
        // 创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 << 10);

        // 缓冲区最核心的4个属性
        print(byteBuffer, "[初始]");

        // 添加一些数据到缓冲区
        byteBuffer.put("Python".getBytes(StandardCharsets.UTF_8));

        System.out.println("还剩多少空间能被写入: " + byteBuffer.remaining());

        print(byteBuffer, "[赋值后]");

        // 让mark记住已经处理到的位置
        byteBuffer.mark();

        // flip会把mark置为-1，也即之前mark记住的位置已被还原...
        byteBuffer.flip();
        // flip()将Buffer从写模式切换成了读模式
        print(byteBuffer, "[flip后]");

        // 恢复position到mark上一次记录的位置 此时已经没有用了，因为mark已经被flip()给还原成-1
        // byteBuffer.reset();
        // print(byteBuffer, "[reset后]");

        // 结论: 一旦进入读模式，必须读完所有的数据，清空缓冲区后，才能再次被写入

        // 此时仍然处于读模式,因此remaining()获取的是还剩多少数据能被读取
        System.out.println("还剩多少数据能被读取: " + byteBuffer.remaining());
        // put()操作时，会判断当前剩余的空间是否能容纳待写入的数据，计算方法就是remaining()，这就导致remaining()的结果不符合put
        // 为了能让remaining()去计算还剩多少空间能被写入，我们要么暴力的清空整个缓冲区(数据仍在),要么读完所有的数据,并清除所有已经读完过的数据

        // byteBuffer.clear();
        //queryData(byteBuffer);
        //byteBuffer.compact();
        byteBuffer.clear();

        byteBuffer.put("Java".getBytes(StandardCharsets.UTF_8));
        print(byteBuffer, "[赋值后]");
        queryData(byteBuffer);
    }

    public static void print(ByteBuffer byteBuffer, String status) {
        System.out.println();
        System.out.println(status + "Limit 当前缓冲区内还有多少空间可以存放数据，或还有多少数据需要取出(读取): " + byteBuffer.limit());
        System.out.println(status + "Position 下一个要被读或写的元素的位置: " + byteBuffer.position());
        System.out.println(status + "Capacity 缓冲区内总共能容纳的数据元素的总量: " + byteBuffer.capacity());
    }

    public static void queryData(ByteBuffer byteBuffer) {
        System.out.println();
        System.out.println("开始读取缓冲区内所有的数据==================");

        final byte[] bytes = new byte[byteBuffer.remaining()];
        int i = 0;
        while (byteBuffer.remaining() > 0) {
            bytes[i++] = byteBuffer.get();
        }
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }
}
