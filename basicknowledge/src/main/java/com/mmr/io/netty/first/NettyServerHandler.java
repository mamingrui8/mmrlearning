package com.mmr.io.netty.first;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Description: 监听处理逻辑
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月09日 15:03
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
//  @Sharable注解 -
// 代表当前的Handler是一个可分享的处理器，也就意味着，服务端注册此Handler后，可以分享给多个客户端同时使用。
// 如果不使用@Sharable注解描述类型，则每次 客户端请求时，必须为客户端重新 创建一个Handler对象
// 正是因为Handler对象被重用（分享）了，多个客户端共用，所以尽量不要在 Handler的实现类中定义可读的变量。 (比如: private String name;)
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {  //这里既可以implements ChannelHandler  ，也可以extends ChannelHandlerAdaptor

    /**
     *  业务处理逻辑 重点！！！
     *  用于处理读取数据请求的逻辑
     *  ctx - 上下文对象。其中包含用于和客户端进行连接的所有资源。如: 对应的Channel
     *  msg - 读取到的数据。默认的类型是ByteBuf，注意: ByteBuf是Netty自定义的，是对ByteBuffer进行的封装。
     *
     *        问: 很奇怪为什么要用Object类型来接收...
     *        答1: 看了NettyServerFixLength.java中的doAccpet()方法就能弄懂了。并不一定msg会是一个ByteBuf,倘若通过codec的StringDecoder解码器，msg将被自动转成String类型！！！
     *        答2: 答1有问题，本质上msg仍然是一个ByteBuf
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{ //channelRead 是netty 5.X版本才开始提供的
        //获取读取的数据，是一个Buf
        ByteBuf readBuffer = (ByteBuf) msg;  //此Buffer非常特殊，不需要重置游标！ (flip)
        byte[] tempData = new byte[readBuffer.readableBytes()];
        //缓存 -> 字节数组
        readBuffer.readBytes(tempData);
        //字节数组 -> 字符串
        String message = new String(tempData, StandardCharsets.UTF_8);
        System.out.println("from client: " + message);
        if("exit".equals(message)){
            ctx.close();
            return;
        }
        String line = "server message to client!";
        //写操作自动释放缓存，避免内存溢出问题。
        //注意，如果只是调用write方法，将不会自动刷新缓存，并且缓存中的数据也不会发送到客户端，必须再次调用flush方法才行。
        //ctx.write(); ctx.flush();
        /*
            此处详细详解flush操作:
            如果没有使用flush。比如缓冲区的大小是1KB  写入的数据首先会进入缓冲区，当1KB满后，往后再写入的数据将会被写入至内存中。
            当内存到达一定限度时，就会报错: OutOfMemoryException。
            当且仅当使用了flush,在flush调用处，缓存中现存的数据才会被发送给客户端。
         */
        ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes(StandardCharsets.UTF_8)));
    }


    /**
     * 异常处理逻辑  (不仅在服务端异常时会运行，当客户端异常退出时同样会运行)
     * ChannelHandlerContext上下文被关闭，代表着处理逻辑就被关闭了，同时也代表着当前与客户端连接的资源关闭了。
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        System.out.println("服务端监听逻辑处理时，出现异常, 具体原因如下: " + cause.toString());
        ctx.close();
    }
}
