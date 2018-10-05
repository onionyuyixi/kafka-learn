package com.onion.kafkalearn.stream;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

/**
 * 打印table 与 stream的内容
 */
public class PrintStream {

    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,"dsl-wc2");
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG,15000);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        StreamsBuilder builder = new StreamsBuilder();
        builder.table("ktable-output1").print();
        builder.table("kstream-output1").print();
        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), props);
        kafkaStreams.start();
        Thread.sleep(40000L);
        kafkaStreams.close();
    }

}
