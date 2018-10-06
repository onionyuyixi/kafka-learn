package com.onion.kafkalearn.test_producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendMessage {

    @Test
    //同步发送
    public void test1(){
        ProducerRecord<String, String> record = new ProducerRecord<>("CustomerCountry", "Precision Products", "France");
        Map map = basicalData();
        try{
            Producer producer = new KafkaProducer<String,Object>(map);
            Future send = producer.send(record);
            System.err.println("nice------------"+send.get().toString());
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("error");
        }
    }


    @Test
    //异步发送
    public void testASyncSend(){
        ProducerRecord<String, String> record = new ProducerRecord<>("CustomerCountry", "Precision Products", "France");
        Map map = basicalData();
        try{
            Producer producer = new KafkaProducer<String,Object>(map);
            producer.send(record,(recordMetadata,e)->{
                System.err.println("nice");
                System.err.println(recordMetadata.toString());
                System.err.println("hasOffseet-----"+recordMetadata.hasOffset());
                System.err.println("offset-----"+recordMetadata.offset()); //offset从0开始
                System.err.println("hasTimestamp-----"+recordMetadata.hasTimestamp());
                System.err.println("timestamp-----"+recordMetadata.timestamp());
                System.err.println("partition-----"+recordMetadata.partition());
                System.err.println("topic-----"+recordMetadata.topic());
                System.err.println("serializedKeySize-----"+recordMetadata.serializedKeySize()); //由于采用的是StringSerializer 这里的大小实际上可以代表字符串的长度
                System.err.println("serializedValueSize-----"+recordMetadata.serializedValueSize());
                System.err.println(recordMetadata);
            });
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("error");
        }

    }

    @Test
    public void sendMessageNoKey(){
        ProducerRecord<String, String> record = new ProducerRecord<>("CustomerCountry", "France");
        Map map = basicalData();
        try {
            KafkaProducer<String, String> producer = new KafkaProducer<String, String>(map);
            producer.send(record);
            System.err.println("nice");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("error");
        }

    }


    @Test
    public void sendWithMyPartitioner(){
        ProducerRecord<String, String> record = new ProducerRecord<>("CustomerCountry", "onion","France");
        Map map = partitionerData();
        try {
            KafkaProducer<String, String> producer = new KafkaProducer<String, String>(map);
            producer.send(record);
            System.err.println("nice");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("error");
        }
    }

    //配置生产者的属性
    private Map basicalData(){
        HashMap<String, Object> map = new HashMap<>();
//        map.put("bootstrap.servers","192.168.1.113:9092");
        map.put("bootstrap.servers","127.0.0.1:9092");
        map.put("value.serializer",StringSerializer.class);
        map.put("key.serializer",StringSerializer.class);
        map.put("acks","all");
        return map;
    }

    private Map partitionerData(){
        Map map = basicalData();
        map.put("partitioner.class","com.onion.kafkalearn.config.MyPartitioner");//这里配置自己的partitioner
        return map;
    }

    //avro序列化 后期再补充
    private Map avroData(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("bootstrap.servers","192.168.1.113:9092");
        map.put("value.serializer","io.confluent.kafka.serializers.KafkaAvroSerializer.class");
        map.put("key.serializer","io.confluent.kafka.serializers.KafkaAvroSerializer.class");
        map.put("acks","all");
        return map;
    }




}
