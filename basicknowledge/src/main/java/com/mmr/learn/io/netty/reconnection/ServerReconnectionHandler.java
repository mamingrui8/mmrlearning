package com.mmr.learn.io.netty.reconnection;

import com.mmr.learn.io.utils.ResponseMessage;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Description: 定时断线重连 --- 服务端处理器
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月20日 19:15
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ServerReconnectionHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        System.out.println("from client : ClassName - " + msg.getClass().getName()
                + " ; message : " + msg.toString());
        ResponseMessage response = new ResponseMessage(0L, "test response");
        ctx.writeAndFlush(response);
    }

    // 异常处理逻辑
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("server exceptionCaught method run...");
        cause.printStackTrace();
        ctx.close();
    }
}
