package com.onion.kafkalearn.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;

/**
 * 从一个topic到另外一个topic
 */
public class StreamEasyDemo {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,"dsl-wc");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String,String> stream = builder.stream("dsl-input1");
        KTable<String, Long> countTable = stream
                .flatMapValues(value -> Arrays.asList(value.split(" ")))
                .groupBy((key, word) -> word)
                .count("Counts");
        KStream<String, Long> countStream = countTable.toStream();
        countTable.to(Serdes.String(),Serdes.Long(),"ktable-output1");
        countStream.to("kstream-output1",Produced.with(Serdes.String(),Serdes.Long()));
        StreamsConfig streamsConfig = new StreamsConfig(props);
        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig);
        streams.start();



    }
}
