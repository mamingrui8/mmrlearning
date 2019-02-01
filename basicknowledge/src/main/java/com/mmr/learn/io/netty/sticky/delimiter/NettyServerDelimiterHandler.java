package com.mmr.learn.io.netty.sticky.delimiter;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月15日 22:35
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class NettyServerDelimiterHandler extends ChannelInboundHandlerAdapter {
    // 业务处理逻辑
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = msg.toString();
        System.out.println("from client : " + message.trim());
        String line = "server message $MMR_CLIENT$你好啊1!!!$MMR_CLIENT$你好啊2!!!!!$MMR_CLIENT$" ;
        ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes(StandardCharsets.UTF_8)));
    }


    // 异常处理逻辑
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("server exceptionCaught method run...");
        // cause.printStackTrace();
        ctx.close();
    }
}
