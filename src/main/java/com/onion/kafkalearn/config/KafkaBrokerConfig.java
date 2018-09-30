package com.onion.kafkalearn.config;

/**
 * Broker的参数定义
 */
public class KafkaBrokerConfig {


//    replication.factor      topic 级别的备份  也就是broker级别的备份 默认是3
//    unclean.leader.selection  不干净的首领选举     默认是true     不干净 是指 在备份并不完全的意思
//    min.insync.replicas      最小的备份数目  假设relication.factor = rf  min.insync.replicas=mir  则    1<rmir<rf

    //消息的幂等性  即同一个消息的在此出现  不会对程序有负面的影响（类比cqrs 则相当于query 不修改数据）
    //消息的重复消费 可能会导致数据的修改 （不如 购物车的订单消息 被重复消费的话  那其他的微服务也会有相应的修改 从而造成了错误）

    /**
     * 一个可靠性高的系统设计参数
     * replication.factor = 3
     * unclean.leader.selection = false
     * acks = 1  案 1的可靠性并不很高  如要很高 则选用all
     *
     */

}
