package com.mmr.learn.io.netty.sticky.serializable;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Description: 对象序列化传输
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月16日 22:49
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class NettyClientSerializableHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        System.out.println("from server: ClassName - " + msg.getClass().getCanonicalName() + " ; message : " + msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println("client exceptionCaught  method run...");
        cause.printStackTrace();
        ctx.close();
    }
}
