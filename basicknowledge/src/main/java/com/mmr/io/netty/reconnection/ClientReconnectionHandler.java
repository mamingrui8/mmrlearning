package com.mmr.io.netty.reconnection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Description: 定时断线重连 --- 客户端处理器
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月20日 19:15
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ClientReconnectionHandler extends ChannelInboundHandlerAdapter {

    /**
     *  这里只写了接收数据，并没有写返回(发送)给服务器的相关处理逻辑
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        System.out.println("from server: ClassName - " + msg.getClass().getName() + "; message : " + msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println("client exceptionCaught method run...");
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 当连接建立成功后会触发的代码逻辑
     * 在一次连接中仅可能运行一次
     * 因此通常用于确认连接是否成功建立或资源是否初始化。
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("client channel active. 连接建立成功，资源初始化正常。");
    }
}
