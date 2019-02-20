package com.mmr.learn.io.netty.reconnection;

import com.mmr.learn.io.utils.RequestMessage;
import com.mmr.learn.io.utils.SerializableFactory4Marshalling;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.WriteTimeoutHandler;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Description: 定时断线重连---客户端
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月20日 19:15
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class ClientReconnection {
    private EventLoopGroup group = null;
    private Bootstrap bootstrap = null;
    private ChannelFuture future = null;

    public ClientReconnection(){
        init();
    }

    private void init(){
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
    }

    /**
     * 初始化处理器
     */
    private void setHandlers(){
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(SerializableFactory4Marshalling.marshallingEncoderBuilder());
                ch.pipeline().addLast(SerializableFactory4Marshalling.marshallingDecoderBuidler());
                //写操作自定义断线。 在指定的时间内，若没有任何的写操作，则client -> server自动断线
                ch.pipeline().addLast(new WriteTimeoutHandler(3, TimeUnit.SECONDS));
                ch.pipeline().addLast(new ClientReconnectionHandler());
            }
        });
    }

    /**
     * 获取未来
     */
    private ChannelFuture getChannelFuture(String host, int port) throws InterruptedException{
        //若future不存在，则重新创建future
        if(future == null) {
            System.out.println("future不存在");
            future = this.bootstrap.connect(host, port).sync();
        }
        //若future存在，但管道尚未激活，则重新创建future
        if(!future.channel().isActive()) {
            System.out.println("future存在，但channel尚未激活");
            this.bootstrap.connect(host, port).sync();
        }
        return future;
    }

    private void release(){
        this.group.shutdownGracefully();
    }

    public static void main(String[] args){
        ClientReconnection client= null;
        ChannelFuture future = null;

        try{
            client = new ClientReconnection();
            //设置处理器
            client.setHandlers();
            future = client.getChannelFuture("localhost", 9999);

            //这里我们练习一下WriteTimeoutHandler TODO

            //Step1 向server分3次发送数据包
            for(int i=0; i<3; i++){
                RequestMessage msg = new RequestMessage(new Random().nextLong(), "test"+i, new byte[0]);
                future.channel().writeAndFlush(msg);
                TimeUnit.SECONDS.sleep(2); //2 < 3，因此并不会受到"自定义断线"的限制
            }
            TimeUnit.SECONDS.sleep(5);

            //Step2 停顿5秒，再向server发送数据包(由于5>3， 因此一定会自动断线)
            future = client.getChannelFuture("localhost", 9999);
            RequestMessage msg = new RequestMessage(new Random().nextLong(), "test", new byte[0]);
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
