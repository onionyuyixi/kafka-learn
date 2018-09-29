package com.onion.kafkalearn;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaLearnApplication {

    @Autowired
    static ProducerConfig producerConfig;
    public static void main(String[] args) {
        SpringApplication.run(KafkaLearnApplication.class, args);

    }
}
