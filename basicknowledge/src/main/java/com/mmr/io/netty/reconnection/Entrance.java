package com.mmr.io.netty.reconnection;

/**
 * Description: netty定时断线重连机制
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月20日 18:27
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
     * 为了保障客户端(client)和服务端(server)在通讯时资源被合理的利用，我们想到了以下两种办法:
     * 1. client和server保持一条不断开的长连接(当然了，连接一定是client发送到server的)
     *    优点: server能快速响应client的请求(实时响应)
     *    缺点: server针对每一个client的请求都要分配一条单独的线程去处理，并且长时间得不到释放，因此对服务器的负担极重。
     *
     * 2. client和server不进行连接，当client需要发送请求时，会将请求积攒起来，当且仅当请求拦截的时间超过临界值(比如5分钟)时，
     *    client才会和server建立连接，并把整个数据包全部发送给server。
     *    优点: 不用长时间连接server，server压力不大。
     *    缺点: 临界值无论是设置成5分钟还是10分钟，其实都不好。因为有可能某个时间段的请求非常集中，这将导致拦截下来的数据包
     *          体量极大，那么传输到server就会很慢，并且还有传输被中断的风险。
     *
     * 上述两个方法都不理想。
     * 关键在于——连接持续多长时间
     * 因此，我们想到了断线重连机制，具体操作如下:
     *    1. client和server进行连接，若某时间间隔(如120s)内没有任何交互，则client断开连接。若有交互，则保持连接，直至下一个120s内
     *    没有任何交互时再断开连接。
     *    优点:
     *          1. 不用长时间连接server，server压力不大。
     *          2. 若有数据则能相对较快的响应client的请求(重新建立连接并向server发出请求肯定要花费一定的时间，所以我用的是"相对较快")
     */
}
