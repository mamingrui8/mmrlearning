package com.mmr.io.netty.first;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Description: netty 客户端
 * 由于客户端是请求的发起者，因此不需要监听。
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月09日 16:29
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class NettyClient {
    //处理请求和处理服务端响应的线程组
    private EventLoopGroup group = null;
    //服务启动相关配置
    private Bootstrap bootstrap = null;

    public NettyClient(){
        init();
    }

    private void init(){
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        //绑定线程组
        bootstrap.group(group);
        //设置通讯模式为NIO
        bootstrap.channel(NioSocketChannel.class);
    }

    public ChannelFuture doRequest(String host, int port, final ChannelHandler... handlers) throws InterruptedException {
        //注意: 此处并没有使用childHandler,客户端也没有childHandler方法。  //TODO childHandle()和handler()有什么区别？ childHandle()有什么特殊之处？
        bootstrap.handler(new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel channel) throws Exception{
                channel.pipeline().addLast(handlers);
            }
        });
        //建立连接   (不是绑定端口，因此没有使用 bootstrap.bind())
        ChannelFuture future = this.bootstrap.connect(new InetSocketAddress(host, port)).sync();
        return future;
    }

    public void release(){
        this.group.shutdownGracefully();
    }

    public static void main(String[] args){
        NettyClient client = null;
        ChannelFuture future = null;

        try {
            client= new NettyClient();
            future= client.doRequest("localhost", 9999, new NettyClientHandler());

            Scanner scanner = null;
            while(true){
                scanner = new Scanner(System.in);
                System.out.print("enter message send to Server (enter 'exit' for close connect)");
                String line = scanner.nextLine();
                if("exit".equals(line)){
                    //在客户端准备退出之前，会先将缓冲区内剩余的数据写入数据流中，接着再添加一个Listener
                    //addListener - 增加监听， 当某条件满足时，触发监听器！
                    //ChannelFutureListener.CLOSE - 关闭监听器。当且仅当ChannelFuture执行返回后，关闭连接。
                    Objects.requireNonNull(future).channel().writeAndFlush(Unpooled.copiedBuffer(line.getBytes(StandardCharsets.UTF_8)))
                            .addListener(ChannelFutureListener.CLOSE);
                    break;
                }
                //Unpooled是一个工具类型，专门用于将byte[]转成ByteBuf
                Objects.requireNonNull(future).channel().writeAndFlush(Unpooled.copiedBuffer(line.getBytes(StandardCharsets.UTF_8)));
                TimeUnit.SECONDS.sleep(1); //这里之所以使主线程睡眠一秒钟，是为了留给服务端子线程读取客户端发送的信息。
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            if(null != future){
                try {
                    future.channel().closeFuture().sync(); //TODO 这里的sync()到底有什么用？ 客户端调用close()方法的时机是什么时候？需要等待服务端先关闭吗？
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
