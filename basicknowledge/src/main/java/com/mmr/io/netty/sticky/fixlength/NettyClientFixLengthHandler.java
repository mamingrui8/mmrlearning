package com.mmr.io.netty.sticky.fixlength;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Description:
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月14日 22:06
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class NettyClientFixLengthHandler extends ChannelInboundHandlerAdapter {

    //msg本质上(底层)还是一个ByteBuf，只不过由于codec的StringUtil()的帮助，为ByteBuf的toString()加入了合适的Charset,因此能够直接toSting()成字符串  此处的msg带缓存！
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        try{
            String message = msg.toString();
            System.out.println("服务端说: " + message);
        }finally{
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        ctx.close();
        System.out.println("客户端出现异常，正在执行自毁程序...");
    }
 }
