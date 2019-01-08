package com.mmr.io.netty.first;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Description:
 * 1. 双线程组
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月08日 22:35
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Server4HelloWorld {
    //一上来，首先定义两个线程组
    //监听线程组,用于监听客户端请求。(只是监听而已)
    private EventLoopGroup acceptorGroup = null;
    //处理客户端相关操作的线程组，负责与客户端进行通讯
    private EventLoopGroup clientGroup = null;
    //服务启动配置信息 (请注意: 与ServerBootstrap平行的，同样作为AbstractBootstrap类的子类，名为 "Bootstrap"   显而易见，ServerBootstrap用于存储服务端启动配置信息，而Bootstrap用于存储客户端启动配置信息)
    private ServerBootstrap bootstrap = null;

    //初始化方法
    public void init(){
        //初始化线程组
        acceptorGroup = new NioEventLoopGroup(); //注意，此处是拿NIO来初始化线程组的
        clientGroup = new NioEventLoopGroup();
        //初始化服务启动配置信息 (并且是服务端的配置信息)  此时配置信息对象中有配置存在吗？没有吧？
        bootstrap = new ServerBootstrap();
        //绑定线程组
        bootstrap.group(acceptorGroup, clientGroup);
        //设定通讯类型为NIO， 也即"同步非阻塞"
        bootstrap.channel(NioServerSocketChannel.class);
        //设置缓冲区大小，缓存区的单位是字节
        /**
         * ChannelOption.SO_BACKLOG, 1024:   BACKLOG用于构造服务端套接字ServerSocket对象，用于标识 当服务端处理请求的线程数 全满时，用于临时存放已完成三次握手的请求的队列最大长度。
         * 如果未设置或所设置的值小于1，Java将默认使用50
         */
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        //SO_SNDBUF: 发送缓冲区(用于向客户端写入数据)   SO_RCVBUF: 读取缓冲区(用于读取客户端数据)  SO_KEEPALIVE: 开启心跳监测(定时保证连接的有效性)
        bootstrap.option(ChannelOption.SO_SNDBUF, 16 * 1024)
                .option(ChannelOption.SO_RCVBUF, 16 * 1024)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    /**
     * 监听处理逻辑
     *
     */

}
