package com.mmr.learn.io.netty.reconnection;

import com.mmr.learn.io.utils.SerializableFactoryMarshalling;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

/**
 * Description: 定时断线重连 —— 服务端
 *              ReadTimeoutHandler是 netty提供的，注意它没有被io.netty.channel.ChannelHandler.@Sharable 所修饰
 *              因此不支持多线程并发使用， 一次仅支持一个连接
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月20日 19:15
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class ServerReconnection {
    //设置监听线程组
    private EventLoopGroup acceptorGroup = null;
    //设置处理线程组， 专门用于应付各种client
    private EventLoopGroup clientGroup = null;
    //设置netty相关配置
    private ServerBootstrap bootstrap = null;

    public ServerReconnection(){
        init();
    }

    private void init(){
        //初始化线程组
        acceptorGroup = new NioEventLoopGroup();
        clientGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        //设置配置
        bootstrap.group(acceptorGroup, clientGroup);
        //设置通讯模式
        bootstrap.channel(NioServerSocketChannel.class);
        //设定缓冲区大小
        bootstrap.option(ChannelOption.SO_BACKLOG, 16);
        bootstrap.option(ChannelOption.SO_SNDBUF, 16 * 1024)
                .option(ChannelOption.SO_RCVBUF, 16 * 1024)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    /*
    private void setHandler(){
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception{
                ch.pipeline().addLast(SerializableFactoryMarshalling.marshallingDecoderBuidler());
                ch.pipeline().addLast(SerializableFactoryMarshalling.marshallingEncoderBuilder());
                //定义一个定时断线处理器，当指定长度时间内没有读取到(client写入的)任何数据时，服务端自动断开针对该client的连接
                //构造函数的参数就是间隔时长，默认的单位是秒。
                //自定义间隔时长单位。 new ReadTimeoutHandler(long times, TimeUnit unit)
                ch.pipeline().addLast(new ReadTimeoutHandler(3));
                //TODO 听老师说，断开的操作是client来做的，到底是真是假？
                ch.pipeline().addLast(new ServerReconnectionHandler());
            }
        });
    }

    private ChannelFuture getChannelFuture(int port) throws InterruptedException{
        return this.bootstrap.bind(port).sync();
    }
*/

    private ChannelFuture doAccept(int port)throws InterruptedException{
        this.bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception{
                ch.pipeline().addLast(SerializableFactoryMarshalling.marshallingDecoderBuidler());
                ch.pipeline().addLast(SerializableFactoryMarshalling.marshallingEncoderBuilder());
                // 定义一个定时断线处理器，当多长时间内，没有任何的可读取数据，自动断开连接。
                // 构造参数，就是间隔时长。 默认的单位是秒。
                // 自定义间隔时长单位。 new ReadTimeoutHandler(long times, TimeUnit unit);
                ch.pipeline().addLast(new ReadTimeoutHandler(3, TimeUnit.SECONDS));
                ch.pipeline().addLast(new ServerReconnectionHandler());
            }
        });
        ChannelFuture future = this.bootstrap.bind(port).sync();
        return future;
    }


    private void release(){
        this.acceptorGroup.shutdownGracefully();
        this.clientGroup.shutdownGracefully();
    }


    public static void main(String[] args){
        ChannelFuture future = null;
        ServerReconnection server = null;

        try {
            server = new ServerReconnection();
//            server.setHandler();
//            future = server.getChannelFuture(9999);
            future = server.doAccept(9999);
            System.out.println("server started...");

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(null != future){
                try {
                    future.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(server != null){
                server.release();
            }
        }
    }
}
