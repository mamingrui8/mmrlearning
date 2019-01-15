package com.mmr.io.netty.sticky.fixlength;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Description: netty粘包问题解决方法——使用定长数据流  具体请参考NettyServerFixLength注释
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月14日 22:06
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class NettyClientFixLength {
    //处理请求和处理服务器端响应的线程组
    private EventLoopGroup serverGroup;
    //服务启动相关配置
    private Bootstrap bootstrap;

    public NettyClientFixLength(){
        init();
    }

    private void init(){
        serverGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();

        //把线程组加入到配置中
        bootstrap.group(serverGroup);
        //设置通讯模式
        bootstrap.channel(NioSocketChannel.class);
    }

    //定长数据流核心部分
    public ChannelFuture doRequest(String host, int port) throws InterruptedException{
        this.bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception{
                ChannelHandler[] handlers = new ChannelHandler[3];
                handlers[0] = new FixedLengthFrameDecoder(3);
                handlers[1] = new StringDecoder(StandardCharsets.UTF_8);
                handlers[2] = new NettyClientFixLengthHandler();
            }
        });

        ChannelFuture future = this.bootstrap.connect(host, port).sync();
        return future;
    }

    //释放线程组
    private void release(){
        this.serverGroup.shutdownGracefully();
    }

    public static void main(String[] args){
        NettyClientFixLength client = null;
        ChannelFuture future = null;
        try{
            client = new NettyClientFixLength();
            future = client.doRequest("localhost", 9999); //这一步只不过是明确了处理逻辑，并没有实质性的发送消息

            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNext()){
                System.out.println("请输入发送给服务端的数据 >" );
                String line = scanner.nextLine();
                if("exit".equals(line)){
                    future.channel().closeFuture().sync();
                    break;
                }
                future.channel().writeAndFlush(Unpooled.copiedBuffer(line.getBytes(StandardCharsets.UTF_8)));
                TimeUnit.SECONDS.sleep(1);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            if(future != null){
                try{
                    future.channel().closeFuture().sync();
                } catch (InterruptedException e){
                   e.printStackTrace();
                }
            }
            if(client != null){
                client.release();
            }
        }
    }
}
