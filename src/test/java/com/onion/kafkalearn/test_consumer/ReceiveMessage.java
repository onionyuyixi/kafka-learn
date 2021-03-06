package com.onion.kafkalearn.test_consumer;


import com.onion.kafkalearn.config.MyConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.CompositeName;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReceiveMessage {


    private Map consumerProperties() {
        Map<String, Object> map = new HashMap<>();
        map.put("bootstrap.servers", "localhost:9092");
        map.put("group.id", "sentence");
//        map.put("value.deserializer", LongDeserializer.class);
        map.put("value.deserializer", StringDeserializer.class);
        map.put("key.deserializer", StringDeserializer.class);
        map.put("enableAutoCommit", true);
        return map;
    }


    @Test
    public void consumeMessage() {
        Map map = consumerProperties();
        KafkaConsumer<String, Long> consumer = new KafkaConsumer<String, Long>(map);
        consumer.subscribe(Collections.singletonList("streams-wordcount-output"));
        fetchMessage(consumer);
    }


    @Test
    public void consumeMessageWithSeek() {
        Map map = consumerProperties();
        KafkaConsumer<String, Long> consumer = new KafkaConsumer<String, Long>(map);
//        consumer.subscribe(Collections.singletonList("streams-wordcount-output"),new MyConsumerRebalanceListener(consumer));
//        consumer.subscribe(Collections.singletonList("kstream-output1"),new MyConsumerRebalanceListener(consumer));
//        consumer.subscribe(Collections.singletonList("dsl-wc2-kstream-output1"),new MyConsumerRebalanceListener(consumer));
//        consumer.subscribe(Collections.singletonList("dsl-wc-KSTREAM-AGGREGATE-STATE-STORE-0000000003-changelog-0"),new MyConsumerRebalanceListener(consumer));
        consumer.subscribe(Collections.singletonList("word-input"), new MyConsumerRebalanceListener(consumer));
        fetchMessage(consumer);

    }

    @Test
    public void oneConsumer() {
        Map map = consumerProperties();
        final KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(map);
        List<PartitionInfo> partitionInfos = consumer.partitionsFor("CustomerCountry"); // 获取CustomerCountry消费群组中所有分区
        List<TopicPartition> topicPartitions = new ArrayList<>();
        if (partitionInfos != null) {
            for (PartitionInfo partitionInfo : partitionInfos) {
                topicPartitions.add(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()));
            }
        }
        consumer.assign(topicPartitions); //为消费者 分配partition

    }


    private void fetchMessage(KafkaConsumer<String, Long> consumer) {
        try {
            while (true) {
                ConsumerRecords<String, Long> consumerRecords = consumer.poll(100);
                for (ConsumerRecord<String, Long> consumerRecord : consumerRecords) {
                    System.err.println(consumerRecord);
                }
                consumer.commitAsync(); //没有异常的情况下 采用异步提交  可以提高效率
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                consumer.commitSync(); //有异常的时候  就一直阻塞 知道完全错误
            } finally {
                consumer.close();
            }
        }
    }
}
