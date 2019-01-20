package com.mmr.io.netty.reconnection;

import com.mmr.io.utils.RequestMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.WriteTimeoutHandler;
import com.mmr.io.utils.SerializableFactoryMarshalling;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月20日 21:47
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Client4Timer {
    // 处理请求和处理服务端响应的线程组
    private EventLoopGroup group = null;
    // 服务启动相关配置信息
    private Bootstrap bootstrap = null;
    private ChannelFuture future = null;

    public Client4Timer(){
        init();
    }

    private void init(){
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        // 绑定线程组
        bootstrap.group(group);
        // 设定通讯模式为NIO
        bootstrap.channel(NioSocketChannel.class);
        // bootstrap.handler(new LoggingHandler(LogLevel.INFO));
    }

    public void setHandlers() throws InterruptedException{
        this.bootstrap.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(SerializableFactoryMarshalling.marshallingDecoderBuidler());
                ch.pipeline().addLast(SerializableFactoryMarshalling.marshallingEncoderBuilder());
                // 写操作自定断线。 在指定时间内，没有写操作，自动断线。
                //ch.pipeline().addLast(new WriteTimeoutHandler(3));
                ch.pipeline().addLast(new ClientReconnectionHandler());
            }
        });
    }

    public ChannelFuture getChannelFuture(String host, int port) throws InterruptedException{
        if(future == null){
            future = this.bootstrap.connect(host, port).sync();
        }
        if(!future.channel().isActive()){
            future = this.bootstrap.connect(host, port).sync();
        }
        return future;
    }

    public void release(){
        this.group.shutdownGracefully();
    }

    public static void main(String[] args) {
        Client4Timer client = null;
        ChannelFuture future = null;
        try{
            client = new Client4Timer();
            client.setHandlers();

            future = client.getChannelFuture("localhost", 9999);
            for(int i = 0; i < 3; i++){
                RequestMessage msg = new RequestMessage(new Random().nextLong(),
                        "test"+i, new byte[0]);
                future.channel().writeAndFlush(msg);
                // TODO 为何客户端无法发送信息至服务端，此处需要看序列化传递的视频！
                TimeUnit.SECONDS.sleep(2);
            }
            TimeUnit.SECONDS.sleep(5);

            future = client.getChannelFuture("localhost", 9999);
            RequestMessage msg = new RequestMessage(new Random().nextLong(),
                    "test", new byte[0]);
            future.channel().writeAndFlush(msg);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(null != future){
                try {
                    future.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(null != client){
                client.release();
            }
        }
    }
}
