package com.mmr.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * Description: 非阻塞
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月02日 22:02
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 * 1. 需要集成Slf4j
 * 2. write()写操作尚未开发  36:33
 */

public class NIOServer implements Runnable{
    //多路复用器,，又叫选择器，用于注册通道
    private Selector selector;
    //这里定义了两个缓存，分别用于read和write
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024); //创建了一个容量为1024个字节的缓冲区
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) {

    }

    public NIOServer(int port){
        init(port);
    }

    private void init(int port){
        System.out.println("服务端准备启动，端口号: " + port);
        //开启多路复用器
        try {
            this.selector = Selector.open();
            //开启服务通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); //与BIO不同的是，NIO都是用静态方法来创建连接
            //设置阻塞模式
            serverSocketChannel.configureBlocking(false); //这里被设置成了"非阻塞" //TODO 这里很奇怪，若设置成true，与BIO有什么区别呢？
            //绑定端口
            serverSocketChannel.bind(new InetSocketAddress(port)); //InetSocketAddress其实就是一个服务于地址的第三方类
            //注册，把通道注册到多路复用器上，并标记当前服务通道的状态
            /*
                register(Selector, int)
                int: 状态编码
                OP_ACCEPT： 连接成功的状态值
                OP_READ: 可以读取的状态值
                OP_WRITE: 可以写入的状态值
                OP_CONNECT: 连接建立之后的状态值 //TODO 这个状态的含义模棱两可
             */
            serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            try{
                //此方法和scanner.readLine()很类似，都是阻塞方法。 至少一个通道被选中时，此方法才会返回。
                //通道是否会被选中，由注册到多路复用器的通道标记来决定。
                this.selector.select();
                //返回已选中的通道标记的集合, 注意: 集合中保存的是通道的标记，相当于通道的ID。
                Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
                while(keys.hasNext()){
                    SelectionKey key = keys.next();
                    //将本次要处理的通道从列表中删除，下次循环时，根据新的通道列表再次执行必要的业务逻辑
                    keys.remove();
                    //通道是否有效
                    if(key.isValid()){
                        try{
                            //目前通道处于"阻塞状态"
                            if(key.isAcceptable()){ //isAcceptable()对应上面方法中的 SelectionKey.OP_ACCEPT   对应的还有isReadable(), isWriteable()和isConnectable()
                                accept(key);
                            }
                        }catch(CancelledKeyException cke){
                            //进入此处，表示通道出现了异常，因此断开连接
                            key.cancel();
                        }
                        //目前通道处于"可读状态"
                        try{
                            if(key.isReadable()){
                                read(key);
                            }
                        }catch(CancelledKeyException cke){
                            key.cancel();
                        }
                        //目前通道处于"可写状态"
                        try{
                            if(key.isWritable()){
                                //write(key);
                            }
                        }catch (CancelledKeyException cke){
                            key.cancel();
                        }
                    }
                }
            }catch (IOException e){

            }
        }
    }

    /*
        SelectionKey是一个监听事件的集合。 是用于跟踪这些监听事件的句柄。 由于内含channel的ID，所以才能通过key.channel()来获取channel
     */

    private void accept(SelectionKey key){
        try{
            //此通道是是先前在init()方法中注册到Selector多路复用器上的ServerSocketChannel
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            //阻塞方法，当客户端发起请求后返回。 此通道和客户端一一对应。
            SocketChannel channel = serverSocketChannel.accept();
            channel.configureBlocking(false); //将该通道设置成非阻塞
            //设置对应客户端的通道标记状态，此通道为读取数据使用
            //也就是说，客户端请求服务端，通道由accept变成read(可读)。此时客户端再次发送请求，多路复用器就有能力(有资格)去调用read()方法从通道中读取数据
            //如若不然，通道中的数据没办法被读取出来
            channel.register(this.selector, SelectionKey.OP_READ);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Tips:
     * 1. channel.read(Buffer) 和Buffer.get(new byte[])  这两个方法都很有意思，前者将数据读取到Buffer缓存中，后者将Buffer缓存中的数据读取到字节数组中。和普通的赋值方法截然不同。
     * @author : MaMingrui
     * @param key :
     */
    private void read(SelectionKey key){
        try{
            //清空读缓存
            this.readBuffer.clear();
            //获取通道
            SocketChannel channel = (SocketChannel)key.channel();
            //将通道中的数据读取到缓存中。 显然，通道中的数据就是客户端发送给服务器的数据
            int readLength = channel.read(readBuffer);
            if(readLength == -1){
                //客户端没有发送任何的数据至服务端,因此服务端只好选择关闭通道
                key.channel().close();
                //断开与服务端的连接
                key.cancel();
                return;
            }
            /*
             *  flip(): NIO中最为复杂的操作就是对Buffer的控制！
             *  Buffer中有一个游标。游标信息在操作后不会归零，如果贸然的访问Buffer，有可能会产生数据不一致的后果
             *  而flip()就是重置游标的重要方法！因此NIO编程中，flip()是常用方法 //TODO Buffer需要好好学
             */
            this.readBuffer.flip();
            //获取到保存了具体数据(客户端发来的)字节数组
            //Buffer.remaining():  用于获取Buffer中有效(真实)数据的长度
            byte[] datas = new byte[readBuffer.remaining()];
            //将Buffer中的数据保存到datas字节数组中
            readBuffer.get(datas);
            System.out.println("从IP: " + channel.getRemoteAddress() + "的客户端而来，该客户端说: " + new String(datas, StandardCharsets.UTF_8));

            //最后，再次对通道进行注册，也即，每次的通道状态只能使用一次。
            //本次把通道标记为"write"操作，"读"完了再往通道里面 写数据，返回给客户端嘛
            channel.register(this.selector, SelectionKey.OP_WRITE);
        }catch(IOException e){
            try{
                key.channel().close();
                key.channel();
            }catch(Exception e1){
                e1.printStackTrace();
            }
        }
    }

}
