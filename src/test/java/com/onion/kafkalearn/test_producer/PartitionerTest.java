package com.onion.kafkalearn.test_producer;


import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartitionerTest {





}

//继承默认
class MyPartitioner extends DefaultPartitioner{
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        //可以写自己的partition逻辑处理
        return super.partition(topic, key, keyBytes, value, valueBytes, cluster);
    }
}


