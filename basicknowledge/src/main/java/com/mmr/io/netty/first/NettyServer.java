package com.mmr.io.netty.first;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Description: 双线程组
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月09日 11:01
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class NettyServer {
    //监听线程组，用于监听客户端请求
    private EventLoopGroup acceptorGroup = null;
    //处理客户端相关操作 (如读、写)， 负责与客户端进行通讯。  也即，当acceptorGroup监听到请求后，会告知clientGroup去处理请求。
    private EventLoopGroup clientGroup = null;
    //服务器启动相关配置信息    注意， AbstractBootstrap有两个子类，分别是: 1. ServerBootstrap 2. bootStrap
    private ServerBootstrap bootstrap = null;

    public NettyServer(){
        init();
    }

    /**
     *  初始化服务端
     */
    private void init(){
        //拿NIO去初始化 线程组
        acceptorGroup = new NioEventLoopGroup();
        clientGroup = new NioEventLoopGroup();
        //初始化服务端配置
        bootstrap = new ServerBootstrap();
        //绑定线程组
        bootstrap.group(acceptorGroup, clientGroup);  //这里的group是ServerBootstrap自己实现的，用于关联两个线程组
        //设置通讯模式 TODO 很奇怪这里的AbstractChannel 子类中只包含nio相关的AbstractChannel， 那如果我想通过AIO来实现netty该怎么做呢?
        bootstrap.channel(NioServerSocketChannel.class);
        //设定缓冲区的大小  (和AIO、BIO、NIO类似，netty也是利用缓冲区操作数据)  单位: 字节 (Byte)
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        //设定其它缓冲区的大小。
        //1. SO_SNDBUF: 数据发送的缓冲区
        //2. SO_RCVBUF: 接收数据的缓冲区
        //3. SO_KEEPALIVE: 开启心跳监测(保证当前连接的有效性)
        bootstrap.option(ChannelOption.SO_SNDBUF,  16 * 1024)
                .option(ChannelOption.SO_RCVBUF, 16 * 1024)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    /**
     * 监听 处理逻辑
     * @param port 服务端监听的端口号
     * @param acceptorHandlers 处理器  (处理客户端请求)
     * @return
     * @throws InterruptedException
     */
    private ChannelFuture doAccept(int port, final ChannelHandler... acceptorHandlers) throws InterruptedException{
        /*
         * childHandler非常特殊，是服务端ServerBootstrap独有的方法。用于提供处理对象。
         * 可以一次性增加若干个处理逻辑，是类似责任链模式的处理方式。
         * 若增加A，B两个处理逻辑，那么在处理客户端请求数据时，会根据A->B的顺序依次来处理。
         *
         * ChannelInitializer - 用于提供处理器的一个模型对象。
         * 其中定义了一个方法: initChannel()。 它用于初始化处理逻辑责任链条的。h
         * 好处如下：
         * 1. 可以保证服务端的Bootstrap只初始化一次处理器，尽量提供处理逻辑的重用。避免反复的创建处理对象，节约资源开销。  (需要Handler处使用@Sharable 注解配合)
         */
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(acceptorHandlers);
            }
        });

        //bind方法，用于绑定监听的端口  ServerBootstrap可以(允许)绑定多个监听端口。只需要多次调用bootstrap.bind()即可
        //sync() - 开启监听逻辑。返回一个ChannelFuture。 返回的结果 顾名思义，代表着监听成功后对应的一个未来的结果。  显然，这是一个阻塞方法  TODO 多客户端时，sync()方法如何发挥作用呢？
        //后续所有服务端和客户端的交互，全都围绕着ChannelFutrue展开。
        ChannelFuture future = bootstrap.bind(port).sync();
        return future;
    }

    /**
     * 关闭线程组
     * shutdownGracefully - 该方法是一个非常安全的方法，可以保证不放弃任何一个已接收到的客户端请求。
     * 比如三次握手协议成功建立的客户端请求。这里自然而然可以联想到刚刚在init()中设置的ChannelOption.SO_BACKLOG,它其实就是在
     * 定义已接收到但尚未处理的客户端请求存放缓存的大小！ (就像一间候诊室)
     */
    private void release(){
        this.acceptorGroup.shutdownGracefully();
        this.clientGroup.shutdownGracefully();
    }

    public static void main(String[] args){
        ChannelFuture future = null;
        NettyServer server = null;
        try{
            server = new NettyServer();
            future = server.doAccept(9999, new NettyServerHandler());  //Server4HelloWorldHandler即是监听逻辑！
            System.out.println("server started.");

            future.channel().closeFuture().sync();
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            if(null != future){
                try{
                    future.channel().closeFuture().sync();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            if(null != server){
                server.release();
            }
        }
    }
}
