package com.mmr.learn.kafka.demo2;

import com.mmr.utils.PropertiesUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.Arrays;

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
        KafkaConsumer<String, String> consumer = null;
        try {
            consumer = new KafkaConsumer<>(PropertiesUtil.getProperties("kafka-consumer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置从哪些主题下拉取数据
        consumer.subscribe(Arrays.asList("TOPIC_UYUN_ALARM"));
        //取数据
        try{
            while (true) {
                //100毫秒轮询一次，有可能会取出0~n条
                /**
                 * 注意: 当把数据获取到时，默认已经提交了offset
                 * 好处: 自动提交offset，消费者非常方便
                 * 坏处: 如果本次获取的数据没来得及处理，则该数据永远也无法再被获取到了，因为offset已经发生了改变。
                 */

                ConsumerRecords<String, String> records = consumer.poll(100); //时间间隔不能太长了
                for (ConsumerRecord<String, String> record : records)
                    System.out.printf("消费者取到的数据: offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
            }
        }finally{
            consumer.close();
        }

    }
}
