package com.onion.kafkalearn.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Arrays;
import java.util.Properties;

public class SentenceStream {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,"sentence");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG,4000);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<byte[], String> sentences = builder.stream("sentence-topic");
        KStream<byte[], String> words = sentences.flatMapValues(value -> Arrays.asList(value.split("\\s+")));
        words.to("word-input");
        KStream<byte[], String> lowerWords = words.mapValues(value -> value.toLowerCase());
        KStream<String, Integer> _words2 = lowerWords.map(((key, value) -> KeyValue.pair(value, value.length())));

        KGroupedStream<String, String> groups = lowerWords.groupBy(((key, value) -> value));
        groups = lowerWords.groupBy((key, value) -> value);
        lowerWords.map((key, value) -> KeyValue.pair(value,value)).groupByKey();
        lowerWords.selectKey((key, value) -> value).groupByKey();
        KTable<String, Long> counts2Sentence = groups.count("Counts2Sentence");
        KTable<String, Long> filter = counts2Sentence.filter((key, value) -> value > 2);
        KTable<byte[], String> table = builder.table("word-input", Consumed.with(Serdes.ByteArray(), Serdes.String()));
        table.mapValues(value -> value.toLowerCase())
                .groupBy((key, value) -> KeyValue.pair(value,value))
                .count("table2sentence");

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();


    }
}
