package com.mmr.learn.io.netty.heatbeat;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Description: 心跳监测-服务端-处理逻辑
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 23:10
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
//为了能并发的处理多个服务端传入的请求，必须加上@ChannelHandler.Sharable
@ChannelHandler.Sharable
public class Server4HeatbeatHandler extends ChannelInboundHandlerAdapter {


    /**
     * 读取缓冲区内(由服务端发送而来)的数据
     * @param ctx 通道处理器的上下文对象
     * @param msg 待处理(当前缓冲区内的)所有数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){

    }

    /**
     * 错误拦截器
     * @param ctx 通道处理器的上下文对象
     * @param cause 出现异常的原因
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){

    }


}
