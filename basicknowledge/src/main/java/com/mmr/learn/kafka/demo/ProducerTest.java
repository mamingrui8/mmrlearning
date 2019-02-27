package com.mmr.learn.kafka.demo;

import org.apache.kafka.clients.producer.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Description: kafka测试Demo1 Producer
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月11日 10:43
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
//@Slf4j
@Component
public class ProducerTest {
    //private static final Logger log = LoggerFactory.getLogger(ProducerTest.class);

    public Properties createProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092"); //localhost:9093,localhost:9094

        /**
         * acks: 生产者认为一个请求完成，所需要kafka集群主服务的应答次数
         * acks=0:
         *         如果设置为0，生产者不会等待kafka的响应，高吞吐。消息会被立刻加到发送缓冲通道中，并且认为已经发送成功。
         *         这种情况下，不能保证kafka接收到了这条消息，retries配置不会生效，每条消息的偏移量都是1；
         * acks=1
         *         这个配置意味着kafka会把这条消息写到本地日志文件中，会等待主机器，但是不会等待集群中其他机器的成功响应。
         *         这种情况下，在写入日志成功后，集群主机器挂掉，同时从机器还没来得及写的话，消息就会丢失掉。
         * acks=all
         *         这个配置意味着leader会等待所有的follower同步完成。这个确保消息不会丢失，除非kafka集群中所有机器挂掉。
         *         这是最强的可用性保证，最安全模式，但延迟相对较长。
         */
        properties.put("acks", "all");
        properties.put("retries", 0); // 消息发送请求失败重试次数
        properties.put("batch.size", 2000);
        properties.put("linger.ms", 1); // 消息逗留在缓冲区的时间，等待更多的消息进入缓冲区一起发送，减少请求发送次数
        properties.put("buffer.memory", 33554432); // 内存缓冲区的总量
        // 如果发送到不同分区，并且不想采用默认的Utils.abs(key.hashCode) % numPartitions分区方式，则需要自己自定义分区逻辑
        properties.put("partitioner.class", "com.mmr.learn.kafka.udp.Partitioner");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return properties;
    }

    public void sendMessage() {
        Properties properties = createProperties();
        Producer<String, String> producer = new KafkaProducer<>(properties);
        int i = 0;
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(2);
                String key = Integer.toString(i);
                String value = "马明瑞，times: " + key;
                ProducerRecord<String, String> record = new ProducerRecord<>("test", key, value);
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception e) {
                        if (e != null) {
                            //log.error("send record error {}", e);
                            System.out.println("send record error " + e.toString());
                        }
                        //log.info("offset: {}, partition: {}", metadata.offset(), metadata.partition());
                        System.out.println("offset: " + metadata.offset() + "; partition: " + metadata.partition());
                    }
                });
                i++;
            }
        } catch (Exception e) {
            //log.error("{}", e);
            System.out.println("ERROR: " + e.toString());
        } finally {
            producer.close();
        }

    }
}
