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
     *         // 0 表示只管发送消息 不用等待broker返回消息（成功与否） 1 则表示集群的master(leader)收到消息即可
     *         //all 表示集群中的所有broker都必须有该消息 （通过集群间的异步通讯）
     *         private String acks;
     *         //针对发送到同一个分区的消息 会存在批量提交的现象 这里指定大小 也有默认值 16KB
     *         private Integer batchSize;
     *         private List<String> bootstrapServers;
     *         //设置生产者的缓存大小 默认是32KB  程序（for开发人员）的发送消息速度> producer (kafka Api)的消息发送速度
     *         //会导致send()方法阻塞 阻塞时间设置 block.on.buffer.full的参数设置
     *         private Long bufferMemory;
     *         //该参数可以是任意的字符串，服务器会用它来识别消息的来橱，还可以用在日志和配额指标里。
     *         private String clientId;
     *         private String compressionType;
     *         private Class<?> keySerializer = StringSerializer.class;
     *         private Class<?> valueSerializer = StringSerializer.class;
     *         //生产者发送消息失败时的尝试次数 默认间隙是100ms
     *         private Integer retries;
     *         private String transactionIdPrefix;
     *         private final Map<String, String> properties = new HashMap();
     *
     *         public Producer() {
     *         }
     *    }
     * @return
     */

//    补充producer的参数
//     linger.timer 苟延残喘的拖延时间(尽可能的拖延)
//    该参数指定了生产者在发送批次之前等待更多消息加入批次的时间 。 KafkaProduce 「 会在
//    批次填满或 linger.time 达到上限时把批次发送出去。默认情况下，只要有可用的线程， 生
//    产者就会把消息发送出去，就算批次里只有一个消息。把 linger.time 设置成比 0 大的数，
//    让生产者在发送批次之前等待一会儿，使更多的消息加入到这个批次。虽然这样会增加延
//    迟，但也会提升吞吐量（因为一次性发送更多的消息，每个消息的开销就变小了） 。


//    max.in.flight.requests.per.connection 每次连接的最大航班请求
//    该参数指定了生产者在收到服务器晌应之前可以发送多少个消息。它的值越高，就会占用
//    越多的内存，不过也会提升吞吐量。
//    把它设为 1 可以保证消息是按照发送的顺序写入服务
//    器的，即使发生了重试。

//     timeout.ms 、 request.timeout.ms 和 metadata.fetch.timeout.ms
//   request.timeout.ms 指定了生产者在发送数据时等待服务器返回响应的时间，(producer 在 send nessage时 请求等待 broker响应的最长时间)
//   metadata.fetch.timeout.ms  指定了生产者在获取元数据（比如目标分区的首领是谁）时等待服务器返回响应的时间。如果等待响应超时，那么生产者要么重试发送数据，要么返回一个错误
//    （抛出异常或执行回调）（producer get metadata wait broker response max time） ===> retry ==> error
//   timeout.ms 指定了 broker 等待同步副本返回消息确认的时间，与asks 的配置相匹配------如果在指定时间内没有收到同步副本的确认，那么 broker 就会返回
//    一个错误 。
//     max.block.ms
//    该参数指定了在调用 send()或使用 partitionsFor() 获取元数据时生产者的阻塞时间。
//    当生产者的发送缓冲区（buffer.memory）已满，或者没有可用的元数据时，这些method就会阻塞。
//    在阻塞时间达到 max.block.ms 时，生产者会抛出超时异常。
//     max.request.size
//    该参数用于控制生产者发送的请求大小。它可以指能发送的单个消息的最大值，也可以指
//    单个请求里所有消息总的大小。例如，假设这个值为 lMB ，那么可以发送的单个最大消
//    息为 lMB ，
//     或者生产者可以在单个请求里发送一个批次，该批次包含了 1000 个消息，每
//    个消息大小为 1KB。
//  另外， broker 对可接收的消息最大值也有自己的限制（message.max.bytes ），所以两边的配置最好可以匹配，避免生产者发送的消息被 broker 拒绝 。
//    receive.buffer. bytes 和 send . buffer.bytes
//    这两个参数分别指定了 TCP socket 接收和发送数据包的缓冲区大小。 如果它们被设为 － 1 ,
//    就使用操作系统的默认值。如果生产者或消费者与 broker 处于不同的数据中心，那么可以
//    适当增大这些值，因为跨数据中心的网络一般都有比较高的延迟和比较低的带宽。

/**
 * 消费者的配置
 * 案
 * 1 topic 对 consumerGroup 负责  group中的 consumer  由group自身负责
 * 2 topic 中的 partition 的数目a 与 group中 consumer的数目b 的关系 推荐  a>=b 这样不会出现idle consumer
 * 3 a ,b 任何一方的数目发生变化  都会引起rebalance  Rebalance 会造成consumerGroup 暂时停用  保证以前的数据 能与现在 对接上
 * 4
 * public static class Consumer {
 *         private final KafkaProperties.Ssl ssl = new KafkaProperties.Ssl();
 *         private Duration autoCommitInterval;
 *         private String autoOffsetReset;
 *         private List<String> bootstrapServers;
 *         private String clientId;
 *         private Boolean enableAutoCommit;
 *         // consumer 向broker发送fetch请求后 wait broker 的 max time
 *         private Duration fetchMaxWait;
 *         //consumer 获取message 的最小的数据量
 *         private Integer fetchMinSize;
 *         private String groupId;
 *         private Duration heartbeatInterval;
 *         private Class<?> keyDeserializer = StringDeserializer.class;
 *         private Class<?> valueDeserializer = StringDeserializer.class;
 *         private Integer maxPollRecords;
 *         private final Map<String, String> properties = new HashMap();
 *
 *         public Consumer() {
 *         }
 */















}
