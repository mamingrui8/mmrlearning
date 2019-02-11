package com.mmr.learn.kafka.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * Description: 生产者分区
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月11日 10:49
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@Slf4j
public class Partitioner implements org.apache.kafka.clients.producer.Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        int partition = 0;
        int offset = Integer.valueOf((String) key);
        if (offset >= 0) {
            partition = Integer.valueOf((String) key) % cluster.partitionCountForTopic(topic);
            // logger.info("key {}, partition {}", key, partition);
        }
        return partition;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public void configure(Map<String, ?> configs) {
        // TODO Auto-generated method stub

    }
}
