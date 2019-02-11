package com.mmr.learn.kafka.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月11日 11:06
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@Component
@Slf4j
@SuppressWarnings("Duplicates")
public class ConsumerTest {
    // 在消费的时候生产者一定要处于运行状态，否则就会得不到数据，无法消费
    public Properties createSimpleProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "test");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "60000");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return properties;
    }

    // 自动提交偏移量
    public void simpleConsumer() {
        Properties properties = createSimpleProperties();

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList("test"));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(10);
                for (ConsumerRecord<String, String> record : records) {
//                    log.info("partition = {}, offset = {}, key = {}, value = {}", record.partition(), record.offset(), record.key(),
//                            record.value());
                    System.out.println(String.format("partition = %s, offset = %s, key = %s, value = %s",
                            record.partition(), record.offset(), record.key(),record.value()));
                }
            }
        } finally {
            consumer.close();
        }
    }


    public Properties createProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "test");
        properties.put("enable.auto.commit", "false");
        properties.put("auto.commit.interval.ms", "60000");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return properties;
    }

    /**
     * 手动控制偏移量
     * 异步提交offset
     */
    public void consumer() {
        Properties properties = createProperties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList("test"));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(10);
                for (ConsumerRecord<String, String> record : records) {
                    log.info("partition = {}, offset = {}, key = {}, value = {}", record.partition(), record.offset(), record.key(),
                            record.value());
                }
                if (!records.isEmpty()) {
                    // 异步提交offset
                    consumer.commitAsync(new OffsetCommitCallback() {

                        @Override
                        public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                            for (Entry<TopicPartition, OffsetAndMetadata> offset : offsets.entrySet()) {
                                //log.info("commit offset: partition = {}, offset = {}", offset.getKey(), offset.getValue().offset());
                                System.out.println(String.format("commit offset: partition = %s, offset = %s", offset.getKey(), offset.getValue().offset()));
                            }
                        }
                    });
                }
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 手工精确控制每个分区的偏移量
     * 分区同步提交offset
     */
    public void consumerExactly() {
        Properties properties = createProperties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList("test"));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
                    for (ConsumerRecord<String, String> record : partitionRecords) {
//                        log.info("partition = {}, offset = {}, key = {}, value = {}", record.partition(), record.offset(), record.key(),
//                                record.value());
                        System.out.println(String.format("partition = %s, offset = %s, key = %s, value = %s", record.partition(), record.offset(), record.key(),
                                record.value()));
                    }
                    long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
                }
            }
        } finally {
            consumer.close();
        }

    }
}
