package com.onion.kafkalearn.config;

public class KafkaProducerConfig {

    //默认的配置 以及 custom设置 都是在改变KafkaProperties.class类中的内容
    /**
     * public class KafkaProperties {
     *     private List<String> bootstrapServers = new ArrayList(Collections.singletonList("localhost:9092"));
     *     private String clientId;
     *     private final Map<String, String> properties = new HashMap();
     *     private final KafkaProperties.Consumer consumer = new KafkaProperties.Consumer();
     *     private final KafkaProperties.Producer producer = new KafkaProperties.Producer();
     *     private final KafkaProperties.Admin admin = new KafkaProperties.Admin();
     *     private final KafkaProperties.Listener listener = new KafkaProperties.Listener();
     *     private final KafkaProperties.Ssl ssl = new KafkaProperties.Ssl();
     *     private final KafkaProperties.Jaas jaas = new KafkaProperties.Jaas();
     *     private final KafkaProperties.Template template = new KafkaProperties.Template();
     *     }
     */
    //生产者的默认配置类
    /**
     * public static class Producer {
     *         private final KafkaProperties.Ssl ssl = new KafkaProperties.Ssl();
     *         private String acks;
     *         private Integer batchSize;
     *         private List<String> bootstrapServers;
     *         private Long bufferMemory;
     *         private String clientId;
     *         private String compressionType;
     *         private Class<?> keySerializer = StringSerializer.class;
     *         private Class<?> valueSerializer = StringSerializer.class;
     *         private Integer retries;
     *         private String transactionIdPrefix;
     *         private final Map<String, String> properties = new HashMap();
     *
     *         public Producer() {
     *         }
     *    }
     * @return
     */
}
