package com.mmr.learn.io.buffer;

import java.nio.ByteBuffer;

/**
 * Description: 学习Buffer中的游标
 *
 * Buffer应用的固定逻辑:
 * 写操作顺序
 * 1. clear()
 * 2. put() -> 写操作，写入到Buffer
 * 3. flip() -> 重置游标
 * 4. SocketChannel.write(buffer); -> 将缓存数据发送到网络的另一端
 * 5. clear()
 *
 * 读操作
 * 1. clear()
 * 2. SocketChannel().read(buffer); -> 从网络另一端读取数据至buffer缓存中
 * 3. buffer.flip(); -> 重置游标
 * 4. buffer.get() -> 借助游标，从缓存中读取数据
 * 5. clear()
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月03日 23:10
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class TestBuffer {
    public static void main(String[] args) throws Exception{
        ByteBuffer buffer = ByteBuffer.allocate(8);

        byte[] temp = new byte[]{3,2,1};


        //java.nio.HeapByteBuffer[pos=0 lim=8 cap=8]
        //pos - 游标位置  lim - 限制数量  cap - 最大容量
        System.out.println("写入数据之前: " + buffer);

        buffer.put(temp);

        //写入数据之后: java.nio.HeapByteBuffer[pos=3 lim=8 cap=8]
        //游标位置 - 3, 限制 - 8， 容量 - 8
        System.out.println("写入数据之后: " + buffer);

        //重置游标
        buffer.flip();

        //重置游标之后: java.nio.HeapByteBuffer[pos=0 lim=3 cap=8]    游标被重置后，pos会还原成最初的位置0，lim则被原先的pos赋值， cap容量当然不会改变。
        //游标位置 - 0， 限制 - 3， 容量 - 8
        System.out.println("重置游标之后: " + buffer);

        for(int i =0; i<buffer.remaining(); i++){ //remaining()取的是 lim - pos
            int data = buffer.get(i);
            System.out.println(i + "-" + data);
        }

        //buffer.get()和 buffer.get(i) 有什么区别？
        //答: buffer.get() 无参  -> 获取当前游标指向的位置存放的数据

        System.out.println(buffer.get(0));

        //[重点] 第一次重置游标后， pos=0, lim=3   第二次重置游标后， pos=0, lim=pos=0    这就会导致当前的buffer的有效数据长度被误认为是 lim-pos=0。
        //因此无论是buffer.get()还是buffer.get(0),  前者报错: java.nio.BufferUnderflowException   后者报错:java.lang.IndexOutOfBoundsException
        //如果还想继续写入数据，请使用buffer.clear()  [当游标紊乱时，可以使用clear()]
        buffer.flip();

        buffer.clear();
        System.out.println(buffer.get());
    }
}
