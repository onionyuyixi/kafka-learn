package com.onion.kafkalearn;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.HashMap;

@SpringBootApplication
public class KafkaLearnApplication {

    @Autowired
    static ProducerConfig producerConfig;
    public static void main(String[] args) {
        SpringApplication.run(KafkaLearnApplication.class, args);

    }
}
