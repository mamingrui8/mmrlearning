package com.mmr.learn.kafka.demo2;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月11日 17:17
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ProducerSend {
    public static void main(String[] args){
        //与kafka连接
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.0.61:9093");
//        props.put("acks", "all");
//        props.put("retries", 0);
//        props.put("batch.size", 16384);
//        props.put("linger.ms", 1);
//        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //由于网络传输时存在对象传递，因此需要用到序列化

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props);

        //发送
        for(int i=0;i<100;i++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord("mytopic", "hello，我是小马" + i);
            kafkaProducer.send(producerRecord);
        }

        //关闭
        kafkaProducer.close();
    }
}
