package com.mmr.learn.io.netty.sticky.serializable;

import com.mmr.learn.io.utils.GzipUtils;
import com.mmr.learn.io.utils.RequestMessage;
import com.mmr.learn.io.utils.SerializableFactory4Marshalling;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * Description: 对象序列化传输
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月16日 22:48
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class NettyClientSerializable {
    //处理请求和响应的线程组
    private EventLoopGroup serverGroup = null;
    //服务启动时相应的配置信息
    private Bootstrap bootstrap = null;

    public NettyClientSerializable(){
        init();
    }

    private void init(){
        serverGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(serverGroup);
        bootstrap.channel(NioSocketChannel.class);
    }

    public ChannelFuture doRequest(String hostname, int port, ChannelHandler... handlers) throws InterruptedException{
        bootstrap.handler(new ChannelInitializer<SocketChannel>(){
            protected void initChannel(SocketChannel channel) throws Exception{
                channel.pipeline().addLast(SerializableFactory4Marshalling.marshallingEncoderBuilder());
                channel.pipeline().addLast(SerializableFactory4Marshalling.marshallingDecoderBuidler());
                channel.pipeline().addLast(handlers);
            }
        });
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(hostname, port)).sync(); //sync()是一个阻塞方法，等待future的到来
        return future;
    }

    public void release(){
        this.serverGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        ChannelFuture future = null;
        NettyClientSerializable client = null;
        try{
            client = new NettyClientSerializable();
            future = client.doRequest("localhost", 9999, new NettyClientSerializableHandler());

            String attachment = "test attachment for Mr Ma";
            byte[] attBuf = attachment.getBytes();
            attBuf = GzipUtils.zip(attBuf);
            RequestMessage msg = new RequestMessage(new Random().nextLong(), "my test", attBuf);
            future.channel().writeAndFlush(msg);
            TimeUnit.SECONDS.sleep(1);
            future.addListener(ChannelFutureListener.CLOSE);
        }catch (InterruptedException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(null != future){
                try {
                    future.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(client != null){
                client.release();
            }
        }
    }
}
