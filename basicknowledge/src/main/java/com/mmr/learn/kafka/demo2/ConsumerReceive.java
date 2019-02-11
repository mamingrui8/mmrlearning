package com.mmr.learn.kafka.demo2;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月11日 17:39
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class ConsumerReceive {
    public static void main(String[] args){
        //建立连接
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.0.61:9093");
        //每一个消费者必须属于某一个消费组，所以必须指定group.id
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //设置从哪些主题下拿去数据
        consumer.subscribe(Arrays.asList("mytopic"));
        //取数据
        try{
            while (true) {
                //100毫秒轮询一次，有可能会取出0~n条
                /**
                 * 注意: 当把数据获取到时，默认已经提交了offset
                 * 好处: 自动提交offset，消费者非常方便
                 * 坏处: 如果本次获取的数据没来得及处理，则该数据永远也无法再被获取到了，因为offset已经发生了改变。
                 */

                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records)
                    System.out.printf("消费者取到的数据: offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
            }
        }finally{
            consumer.close();
        }

    }
}
