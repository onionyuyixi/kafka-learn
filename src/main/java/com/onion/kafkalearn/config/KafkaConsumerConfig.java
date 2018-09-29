package com.onion.kafkalearn.config;

public class KafkaConsumerConfig {

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

/**
 * 消费者的配置
 * 案
 * 1 topic 对 consumerGroup 负责  group中的 consumer  由group自身负责
 * 2 topic 中的 partition 的数目a 与 group中 consumer的数目b 的关系 推荐  a>=b 这样不会出现idle consumer
 * 3 a ,b 任何一方的数目发生变化  都会引起rebalance  Rebalance 会造成consumerGroup 暂时停用  保证以前的数据 能与现在 对接上
 * 4 案如果你提交的offset1 < broker 知道的最新consumer端的offset2 那么 offset1<offset<offset2 的消息 或被重新消费
 * 如果 offset1 > offset2  则 offset2<offset<offset1 的消息 将不会被消费  但依然存在
 * public static class Consumer {
 *         private final KafkaProperties.Ssl ssl = new KafkaProperties.Ssl();
 *         private Duration autoCommitInterval;
 *         // when no offset or offset is not valid  ,where is the offset to start ; default is latest (最新的offset)
 *         //if you wanna start from 0 , should change this value to earliest
 *         private String autoOffsetReset;
 *         private List<String> bootstrapServers;
 *         private String clientId;
 *         //坏处  在autoCommit的间隔其间内  if rebanlance , consumer 将会从 latest 的地方 获取offset(Rebalance后会对offset有改变)
 *         ,由于没有提交offset ,导致了前一部分的offset失效
 *         代替的有commitSync() commitAsync()的手动提交
 *         private Boolean enableAutoCommit;// 是否允许自动提交offset topic  partition 元数据   有好处  也有坏处
 *         // consumer 向broker发送fetch请求后 wait broker 的 max time
 *         private Duration fetchMaxWait;
 *         //consumer 获取message 的最小的数据量
 *         private Integer fetchMinSize;
 *         private String groupId;
 *         private Duration heartbeatInterval;  心跳间隔时间
 *         private Class<?> keyDeserializer = StringDeserializer.class;
 *         private Class<?> valueDeserializer = StringDeserializer.class;
 *         private Integer maxPollRecords;
 *         private final Map<String, String> properties = new HashMap();
 *
 *         public Consumer() {
 *         }
 */

// 补充属性

//    1 max.partition.fetch.bytes   一个分区一次能够拉取的最大大小 默认1MB  案 broker的输出(max.partition.fetch.bytes) > 输入（max.message.size）是必须的
//     后果嘛  “堵不如疏”呗
//    2 session.timeout.ms   consumer 离开后的 最长抢救时间 超过这个时间  就会被判定死亡 默认3s
//    与之相关的另外一个比较的参数 heatbeat.interval.ms  心跳周期间隔时间
//     二者关系是 session = n * heatbeat

//    3 partition.assign.strategy  分区的分配策略














}
