package com.mmr.learn.io.netty.sticky.serializable;

import com.mmr.learn.io.utils.SerializableFactoryMarshalling;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Description: 对象序列化传输
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月16日 22:49
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class NettyServerSerializable {
    //监听线程组，监听客户端请求
    private EventLoopGroup acceptorGroup = null;
    //处理客户端相关操作的线程组，负责与客户端之间进行通讯
    private EventLoopGroup clientGroup = null;
    //服务启动相关的配置信息
    private ServerBootstrap bootstrap = null;

    public NettyServerSerializable(){
        init();
    }

    private void init(){
        acceptorGroup = new NioEventLoopGroup();
        clientGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();

        //绑定线程组
        bootstrap.group(acceptorGroup, clientGroup);
        //设置通讯模式
        bootstrap.channel(NioServerSocketChannel.class);
        //设定缓冲区
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.option(ChannelOption.SO_SNDBUF, 16 * 1024)
                .option(ChannelOption.SO_RCVBUF, 16 * 1024)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    public ChannelFuture doAcceptor(int port, final ChannelHandler... acceptorHandlers) throws InterruptedException {
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //decoder和encoder的前后摆放顺序随意  排名不分先后
                ch.pipeline().addLast(SerializableFactoryMarshalling.marshallingDecoderBuidler());
                ch.pipeline().addLast(SerializableFactoryMarshalling.marshallingEncoderBuilder());
                ch.pipeline().addLast(acceptorHandlers);
            }
        });
        ChannelFuture future = bootstrap.bind(port).sync();
        return future;
    }

    public void release(){
        this.acceptorGroup.shutdownGracefully();
        this.clientGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        ChannelFuture future = null;
        NettyServerSerializable server = null;
        try{
            server = new NettyServerSerializable();
            future = server.doAcceptor(9999, new NettyServerSerializableHandler());
            System.out.println("Server started...");

            future.channel().closeFuture().sync();
            System.out.println("Server closed...");
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
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
