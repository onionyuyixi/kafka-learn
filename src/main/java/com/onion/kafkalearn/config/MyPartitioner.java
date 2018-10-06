package com.onion.kafkalearn.config;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

//实现partitioner接口
@Configuration
public class MyPartitioner implements Partitioner {


    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes1, Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        if((keyBytes==null)||(!(key instanceof String))){
            throw new InvalidRecordException("we expect all messages to have acustomer name as key");
        }
        if(((String)key).equals("onion")){
            return numPartitions; //key是onion的消息  分配在最后一个分区上
        }
        return (Math.abs(Utils.murmur2(keyBytes))%(numPartitions-1)); //hash 散列 均衡
//        return super.partition(topic, key, keyBytes, value, valueBytes, cluster);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}