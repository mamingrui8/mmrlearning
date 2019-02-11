package com.mmr.learn.kafka;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月10日 16:16
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
     * 参考教程: https://blog.csdn.net/jenyzhang/article/details/74919494
     *
     * 面试题:
     * 1. kafka的架构是怎样的
     * 2. 消费者如何消费信息？
     * 3. 使用的kafka版本是多少？
     *    答: 0.10.0.1
     *
     *  [入门]
     *  系统的演变过程:
     *      j2EE模式(所有的模块全部放在一台服务器上)
     *      -> 1-N集群扩展(多部署几台服务器，中间用类似nginx)
     *      -> 分布式部署 (某些机器上只放某些功能的模块)
     *      到这里为止，我们发现模块与模块之间需要通讯。之前我们接触到的通讯方式如下:
     *      1. http restful接口
     *      2. cxf:webservice服务
     *      3. rpc远程过程调用
     *      4. 定时任务，定时读取数据。
     *      5. 【重点】消息队列/MQ
     *
     *
     *  消息模式:
     *  1. 点对点
     *  2. 发布-订阅
     *          1. 消息系统中的"subject":
     *                  RSS中指"频道"
     *                  消息系统当中指"主题(消息分类)"
     *          2. 主题设计的原则？
     *                  如果没有主题，订阅者接收到的消息将是消息系统中的所有消息。
     *                  有主题，订阅者很轻松的获取到自己想要的消息。
     *     发布者可以针对多个主题进行发布，订阅者同样也可以订阅多个主题
     *
     *  作用:
     *  1. 应用解耦。
     *     哪怕A系统挂掉了，但A发往B的请求已经存到了消息队列中，由消息队列发往B系统即可。
     *  2. 流量削峰。
     *     一般出现在秒杀或团购活动中。既可以控制活动的人数，也可以缓解短时间内高峰流量给服务器带来的压力。
     *     服务器会将用户发送的请求写入消息队列，若消息队列的长度超过最大限制(比如100条)，则直接抛弃后续的用户请求(比如直接跳转到错误页面)。
     *     接着再从消息队列内保存的100条用户请求中做筛选和处理。
     *  3. 日志处理
     *  4. 消息通讯
     *
     *  问题1: 消息系统如何知道某一个主题被哪些订阅者订阅？
     *         答: 进行维护。
     *  问题2: 针对某一个主题，若订阅者越多，那么消息系统维护的"元数据"会越多，该主题下新消息来临后推送给所有订阅者花费的时间也越长。请问该如何解决问题？
     *         答: 为消息系统增加处理单元(比如提升CPU，扩充主机)
     *  问题3: 如果在推送时，订阅者挂掉了？那么之后发布的消息应该如何处理？
     *         答: 由"消息系统推送给订阅者"的模式转变成"订阅者定时向消息系统拿取消息"
     *  问题4: 对于分区中的一个offset例如等于345555,如何去查找对应的message呢？
     *         答: 第一步，找到该message所在的segment文件，接着再通过二分查找的方式寻找小于等于345552的offset。
     *
     *
     *  使用方式:
     *  Step1: 从官网下载kafka安装文件，不同版本的kafka差距较大。我使用的是kafka_2.11-0.10.0.1  上传至Linux服务器并解压缩
     *  Step2: 配置环境变量
     *         vi /etc/profile
     *         export KAFKA_HOME=/xxxxx
     *         export PATH=$PATH:$KAFKA_HOME/bin
     *         source /etc/profile 使生效
     *  Step3: kafka中需要用到zk，因此首先启动zk
     *         nohup zookeeper-server-start.sh /data/kafka_2.11-0.10.0.1/config/zookeeper.properties &
     *         (后台启动)
     *  Step4: 启动broker
     *         nohup kafka-server-start.sh /data/kafka_2.11-0.10.0.1/config/server.properties &
     *  Step5: 测试
     *         模拟生产者向消费者发送数据，但消息必须属于某主题
     *   Step5-1: 创建主题
     *            kafka-topics.sh --create --zookeeper localhost:2181 --topic mytopic --partitions 1 --replication-factor 1
     *            解释：
     *            1. --zookeeper: zk的地址
     *            2. --topic: topic的名称
     *            3. --partitions: 该主题下分区数量
     *            4. --replication-factor: 副本因子的数量
     *            若端口是2181，则可以省略不写
     *
     *   Step5-2: 创建生产者
     *            kafka-console-producer.sh --topic mytopic --broker-list localhost:9092
     *   Step5-3: 创建消费者
     *            confikafka-console-consumer.sh --topic mytopic --zookeeper localhost:2181
     *   Step5-4: 删除主题
     *            kafka-topics.sh --delete --zookeeper localhost:2181 --topic test
     *
     *  概念:
     *  1. Topic(主题)
     *          创建、修改、删除。
     *          Producer针对某个主题进行生产，Consumer针对某个主题进行订阅。
     *  2. Partition(分区)
     *          Kafka可以将topic进行数据文件切片的方式分布存储在若干个broker上。
     *          单独的一个partition内的消息是有序且不可修改的，但是多个分区之间的数据是无序的。
     *          问: 生产者生产的消息怎么知道到底该进入主题下的哪个分区？
     *          答: 消息的组成部分是key-value，通过key得知所去的分区。
     *  3. replication(副本)
     *          副本因子数应当小于等于可用的broker数
     *          如果有多个副本，副本内部会出现leader和follower，其中leader与外界进行读写操作，而follower则采用拉取的方式与leader同步。
     *  4.同步与异步
     *          同步: 消息发送给kafka之后，在此等待返回结果
     *          异步: 消息先发送到阻塞队列中，通过轮询的方式，将阻塞队列中的消息发送给kafka。
     *  5. kafka与zookeeper的节点说明
     *          多个broker之间会存在一个controller broker的角色
     *
     *  demo: 入门案例
     *
     *  集群的搭建
     *  1. 准备好zookeeper服务器
     *  2. 各台主机的broker.id设置成不同
     *  3. 在server。properties中加入zk的地址
     *  4. 对log.dirs也进行修改
     *
     *  消费者并不会按照顺序依次消费kafka中的数据，而是会根据分片来选择。(如果只有一个分区，则看不出效果)
     *
     */
}
