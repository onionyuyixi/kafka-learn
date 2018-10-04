package com.onion.kafkalearn.stream;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;

public class StreamEasyDemo {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,"dsl-wc");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
//        props.put(StreamsConfig.consumerPrefix(ConsumerConfig.METADATA_MAX_AGE_CONFIG), 60000);
//        props.put(StreamsConfig.producerPrefix(ProducerConfig.METADATA_MAX_AGE_CONFIG), 60000);
        props.put(StreamsConfig.consumerPrefix(ConsumerConfig.METADATA_MAX_AGE_CONFIG), 60000);
//        props.put(StreamsConfig. 60000);


        StreamsBuilder builder = new StreamsBuilder();
        KStream<String,String> stream = builder.stream("dsl-input1");
        KTable<String, Long> countTable = stream
                .flatMapValues(value -> Arrays.asList(value.split(" ")))
                .groupBy((key, word) -> word)
                .count();
        KStream<String, Long> countStream = countTable.toStream();
        countStream.to("kstream-output1",Produced.with(Serdes.String(),Serdes.Long()));
        StreamsConfig streamsConfig = new StreamsConfig(props);
        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig);
        streams.start();



    }
}
