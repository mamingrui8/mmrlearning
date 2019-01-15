package com.mmr.io.netty.sticky.fixlength;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月14日 22:06
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class NettyServerFixLengthHandler extends ChannelInboundHandlerAdapter {

    /**
     * 业务处理逻辑
     * @param ctx 处理器上下文对象
     * @param msg 和客户端沟通的内容
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){ //继承时，绝对不可以缩小父类方法的访问权限！
        String message = msg.toString();  //这里甚至连msg是否为空都不用去担心！
        System.out.println("from client: " + message);
        String line = "服务端收到请求了，客户端你好！";
        ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 异常处理逻辑
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        System.out.println("服务端处理逻辑遇到错误，正在执行自毁程序...");
        ctx.close();
    }
}
