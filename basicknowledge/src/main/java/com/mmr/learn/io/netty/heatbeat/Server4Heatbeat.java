package com.mmr.learn.io.netty.heatbeat;

import com.mmr.learn.io.utils.SerializableFactoryMarshalling;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Description: 心跳监测-服务端
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 22:42
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class Server4Heatbeat {
    //监听器
    EventLoopGroup acceptorGroup;
    //处理器 处理与客户端相关的数据交互
    EventLoopGroup clientGroup;;
    //引导程序，netty服务端相关配置
    ServerBootstrap bootstrap;

    public Server4Heatbeat(){
        init();
}

    private void init(){
        //初始化监听器
        acceptorGroup = new NioEventLoopGroup();
        //初始化客户端处理器
        clientGroup = new NioEventLoopGroup();
        //初始化引导程序
        bootstrap = new ServerBootstrap();
        //绑定线程组
        bootstrap.group(acceptorGroup, clientGroup);
        //设置通讯方式
        bootstrap.channel(NioServerSocketChannel.class);
        //设置缓冲区大小
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_SNDBUF, 16 * 1024)
                .option(ChannelOption.SO_RCVBUF, 16 * 1024)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    public ChannelFuture doAcceptor(int port) throws InterruptedException{
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(SerializableFactoryMarshalling.marshallingEncoderBuilder());
                ch.pipeline().addLast(SerializableFactoryMarshalling.marshallingDecoderBuidler());
                //ch.pipeline().addLast(new Server4HeatbeatHandler());
            }
        });
        //TODO 为什么一定要加上sync()?
        //答:future是netty异步执行的结果，sync()会异步等待结果返回并返还future，类似监控器。
        //TODO 如果不加sync()会如何？
        ChannelFuture future = bootstrap.bind(port).sync();
        return future;
    }

    public void release(){
        if(clientGroup != null) clientGroup.shutdownGracefully();
        if(acceptorGroup != null) acceptorGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        Server4Heatbeat server4Heatbeat = null;
        ChannelFuture future= null;
        try{
            server4Heatbeat = new Server4Heatbeat();
            future = server4Heatbeat.doAcceptor(9999);
            System.out.println("server started");

            future.channel().closeFuture().sync();
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            if(null != future) {
                try {
                    future.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(null != server4Heatbeat) server4Heatbeat.release();
        }
    }

}
