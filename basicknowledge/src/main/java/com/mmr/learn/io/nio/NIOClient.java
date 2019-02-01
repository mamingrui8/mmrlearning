package com.mmr.learn.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Description: 非阻塞-客户端
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月03日 22:33
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class NIOClient {

    public static void main(String[] args) {
        //创建远程地址信息
        InetSocketAddress remote = new InetSocketAddress("localhost", 9999);  //TODO 猜测InetSocketAddress在创建时就已经建立了一条连接(或者尝试发送过请求)
        //创建通道 (相当于创建一根水管)
        SocketChannel channel = null;

        //定义缓存 用于存放读取和写入的数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try{
            //开启通道
            channel = SocketChannel.open();
            //把水管架到指定的路径上，连接至远程服务器
            channel.connect(remote);
            //监听控制台
            Scanner scanner = new Scanner(System.in);
            while(true){
                /* 写入  */
                System.out.println("请输入一些信息以发送给服务端吧！");
                String line = scanner.nextLine();
                if("exit".equals(line)){
                    break;
                }
                //将控制台输入的字符串写入到缓存中
                buffer.put(line.getBytes(StandardCharsets.UTF_8));
                //重置游标
                buffer.flip();
                //将缓存中的数据写入到管道中，发送给服务端
                channel.write(buffer);
                buffer.clear();
                //TODO 疑问: 为何写完了就读取？服务端返回的有那么快吗？如何做到客户端写入 -> 服务端读取 -> 服务端写入 -> 客户端读取 的？ 难道是同步的吗？
                //解答就在下面这条语句 channel.read(buffer);

                /* 读取 */
                int readLength = channel.read(buffer); //channel.read()方法是 阻塞方法，当且仅当获取 到数据时，才会返回
                if(readLength == -1){
                    break;
                }
                buffer.flip();
                int realLength = buffer.remaining();
                byte[] bytes = new byte[realLength]; //byte字节数组用于读取缓存中的数据
                buffer.get(bytes);
                System.out.println("服务端说: " + new String(bytes, StandardCharsets.UTF_8));
                //清空buffer
                buffer.clear();
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(null != channel){
                try{
                    channel.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
