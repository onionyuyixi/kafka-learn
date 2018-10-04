package com.onion.kafkalearn.source_examples;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;


@SpringBootTest
@RunWith(SpringRunner.class)
public class KafkaProducerExample {


    @Test
    public void testNormal() {
        Properties props = producerConfig();
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++)
            producer.send(new ProducerRecord<String, String>("my-topic", Integer.toString(i), Integer.toString(i)));
        producer.close();
    }

    // From Kafka 0.11, the KafkaProducer supports two additional modes:
    // the idempotent producer and the transactional producer.
    // 幂等性生产者 事务性生产者

    //The transactional producer allows an application to send messages
    // to multiple partitions (and topics!) atomically.
    //强调应用可以向多个分区 多个topic 发送消息
    @Test
    public void testWithTranscation() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("transactional.id", "onion-transcational-id");
        props.put("enable.idempotence", true);
        props.put("transaction.timeout.ms", 1000);
        props.put("max.inflight.requests.per.connection", 1);

        KafkaProducer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
        producer.initTransactions();
        try {
            producer.beginTransaction();
            for (int i = 0; i < 100; i++) {
                if (i < 10) {
                    producer.send(new ProducerRecord<>("transcational-topic", Integer.toString(i), Integer.toString(i)));
                } else {
                    producer.send(new ProducerRecord<String, String>("my-topic", Integer.toString(i), Integer.toString(i)));
                }

            }
            producer.commitTransaction();
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
            producer.close();
        } catch (KafkaException e) {
            producer.abortTransaction();
        }
        producer.close();
    }

    //The idempotent producer strengthens Kafka's delivery semantics from at least once to exactly once delivery.
    //只发送一次
    @Test
    public void testWithIdempotent() {
        Properties properties = producerConfig();
        properties.put("enable.idempotence", true);

    }

    private Properties producerConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
}
