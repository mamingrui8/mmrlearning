package com.mmr.learn.io.netty.first;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.StandardCharsets;

/**
 * Description: 客户端处理器
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月09日 16:59
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        try{
            ByteBuf readBuf = (ByteBuf)msg;
            byte[] tempData = new byte[readBuf.readableBytes()];
            readBuf.readBytes(tempData);
            System.out.println("from server : " + Thread.currentThread().getName() + ", " + new String(tempData, StandardCharsets.UTF_8));
        }finally{
            //由于客户端只做了读操作，没有写操作，不能使用writeAndFlush,所以必须手动释放缓存，从而避免内存的溢出。
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        System.out.println("客户端遇到异常，出现异常，具体原因如下: " + cause.toString());
        ctx.close();
    }
}
