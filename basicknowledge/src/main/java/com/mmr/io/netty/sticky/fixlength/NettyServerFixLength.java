package com.mmr.io.netty.sticky.fixlength;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.StandardCharsets;

/**
 * Description: netty 粘包问题解决方案——使用定长数据流。
 * 客户端和服务端提前协商好每条消息的长度(比如，长度为10) 如果客户端或者服务端写出的数据不足10，则不足位使用空白字符来补足。
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月14日 22:05
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class NettyServerFixLength {
    //监听线程组，用来监听客户端请求
    private EventLoopGroup acceptorGroup = null;
    //处理请求线程组，用来处理客户端请求，如读、 且负责与客户单通讯
    private EventLoopGroup clientGroup = null;
    //服务端启动所需的相关配置
    private ServerBootstrap bootstrap= null;

    NettyServerFixLength(){
        init();
    }

    @SuppressWarnings("Duplicates")
    private void init(){
        acceptorGroup = new NioEventLoopGroup(1);
        clientGroup = new NioEventLoopGroup(4);
        bootstrap = new ServerBootstrap();
        bootstrap.group(acceptorGroup, clientGroup);

        //设置通讯模式
        bootstrap.channel(NioServerSocketChannel.class);
        //设定缓冲区的大小
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.option(ChannelOption.SO_SNDBUF, 16*1024)
                .option(ChannelOption.SO_RCVBUF, 16*1024)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    /**
     * 与NettyServer不同， ChannelHandler不定长参数并非是当做参数传入doAccpet
     */
    private ChannelFuture doAccept(int port) throws InterruptedException{
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            //定长数据流的核心
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelHandler[] acceptHandlers = new ChannelHandler[3];
                //定长Handler。通过构造参数设置消息发送的长度(单位是:字节 由于本文是utf-8编码，则能一次能够发送3个英文字母或1个中文)
                //注意: 不同的字符集下，字节和字符之间的转换率不同
                //      1. utf-8:  1个英文字母占用一个字节，一个中文字符(含繁体)占用3个字节
                //      2. ascII:  1个英文字母占用一个字节，一个中文字符(含繁体)占用2个字节
                //      3. unicode: 1个英文字母占用2个字节，一个中文字符(含繁体)占用2个字节
                acceptHandlers[0] = new FixedLengthFrameDecoder(3);
                // 字符串解码工具，它会自动处理channelRead()方法接收到的msg参数(包括编码)，并将ByteBuf类型的数据(这里指的就是msg啦)转换成字符串！ 若不传递任何参数，则当前文件的编码就是其默认编码
                acceptHandlers[1] = new StringDecoder(StandardCharsets.UTF_8);
                //下面是自己写的一个Handler 处理逻辑
                acceptHandlers[2] = new NettyServerFixLengthHandler();
                socketChannel.pipeline().addLast(acceptHandlers);
            }
        });

        ChannelFuture channelFuture = bootstrap.bind(port).sync();
        return channelFuture;
    }

    private void release(){
        this.acceptorGroup.shutdownGracefully();
        this.clientGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        ChannelFuture future = null;
        NettyServerFixLength nettyServer = null;

        try{
            nettyServer = new NettyServerFixLength();
            future = nettyServer.doAccept(9999);
            System.out.println("Server closed");

            future.channel().closeFuture().sync();
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            if(future != null){
                try{
                    future.channel().closeFuture().sync();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            if(null != nettyServer){
                nettyServer.release();
            }
        }
    }
}
