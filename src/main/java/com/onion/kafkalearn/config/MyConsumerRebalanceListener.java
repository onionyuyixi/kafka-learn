package com.onion.kafkalearn.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyConsumerRebalanceListener implements ConsumerRebalanceListener {

    private Consumer<?, ?> consumer;

    private Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    public MyConsumerRebalanceListener(Consumer<?, ?> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
    }

    /**
     * seek（）可用以指定 从固定的offset 读取消息
     * @param partitions
     */
    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

        for (TopicPartition partition : partitions) {
            consumer.seek(partition,1);//指定从offset=1 处开始获取消息
        }

    }
}
