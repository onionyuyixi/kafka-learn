package com.onion.kafkalearn.test_consumer;


import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerMessage {


    @Test
    public void test(){
        Map map = new HashMap<String,Object>();
        map.put("zookeeper.connect","localhost:2181");
        map.put("group.id","my-group-id");
        KafkaConsumer consumer = new KafkaConsumer<>(map);

    }











}
