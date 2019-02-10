package com.mmr.learn.io.netty.sticky.serializable;

import com.mmr.learn.io.utils.GzipUtils;
import com.mmr.learn.io.utils.RequestMessage;
import com.mmr.learn.io.utils.ResponseMessage;
import io.netty.channel.ChannelHandler;
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
@ChannelHandler.Sharable
public class NettyServerSerializableHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("from client: ClassName - " + msg.getClass().getCanonicalName() + " ; message : " + msg.toString());
        if(msg instanceof RequestMessage){
            RequestMessage request = (RequestMessage) msg;
            byte[] attachment = GzipUtils.unzip(request.getAttachment());
            System.out.println(new String(attachment));
        }
        ResponseMessage response = new ResponseMessage(0L, "test response,I'm Mr Ma");
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println("server exception caught Exception run...");
        cause.printStackTrace();
        ctx.close();
    }
}
