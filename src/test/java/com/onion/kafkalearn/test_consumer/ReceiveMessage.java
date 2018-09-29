package com.onion.kafkalearn.test_consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReceiveMessage {

    Logger logger = LoggerFactory.getLogger(ReceiveMessage.class);


    private Map consumerProperties() {
        Map<String, Object> map = new HashMap<>();
        map.put("bootstrap.servers", "localhost:9092");
        map.put("group.id", "CountryCounter");
        map.put("value.deserializer", StringDeserializer.class);
        map.put("key.deserializer", StringDeserializer.class);
        map.put("enableAutoCommit", true);
        return map;
    }


    @Test
    public void consumeMessage() {
        Map map = consumerProperties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(map);
        consumer.subscribe(Collections.singletonList("CustomerCountry"));
        try {
            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    logger.info("topic =%s ,partition = %s ,offset=%s ,customer =%s,coutry=%s\n",
                            consumerRecord.topic(),
                            consumerRecord.partition(),
                            consumerRecord.offset(),
                            consumerRecord.key(),
                            consumerRecord.value()
                    );
                }
                int updatedCount =1;
                updatedCount++;
                System.err.println(updatedCount);
                consumer.commitSync();
            }
        } finally {
            consumer.close();
        }


    }


}
