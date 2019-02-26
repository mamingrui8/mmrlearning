package com.mmr.learn.io.netty.sticky.fixlength;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月14日 22:06
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class NettyServerFixLengthHandler extends ChannelInboundHandlerAdapter {

    // 业务处理逻辑
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = msg.toString();
        System.out.println("from client : " + message.trim());
        String line = "ok ";

//      显然，我也可以按如下的方式(通过字节数组交互)来写，但是没必要啊！ 反正line这里写死成3个字节 "ok+空格"， 何必像客户端那样补0呢
//        byte[] bb = new byte[3];
//        byte[] cc = line.getBytes(StandardCharsets.UTF_8);
//        if(cc.length > 0){
//            for(int i=0; i<cc.length; i++){
//                bb[i] = cc[i];
//            }
//        }
//        ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes(StandardCharsets.UTF_8)));

        ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes("UTF-8")));
    }


    // 异常处理逻辑
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("server exceptionCaught method run...");
        // cause.printStackTrace();
        ctx.close();
    }
}
