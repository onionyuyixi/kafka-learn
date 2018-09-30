package com.onion.kafkalearn.config;

/**
 * Broker的参数定义
 */
public class KafkaBrokerConfig {


//    replication.factor      topic 级别的备份  也就是broker级别的备份 默认是3
//    unclean.leader.selection  不干净的首领选举     默认是true     不干净 是指 在备份并不完全的意思
//    min.insync.replicas      最小的备份数目  假设relication.factor = rf  min.insync.replicas=mir  则    1<rmir<rf


    /**
     * 一个可靠性高的系统设计参数
     * replication.factor = 3
     * unclean.leader.selection = false
     * acks = 1  案 1的可靠性并不很高  如要很高 则选用all
     *
     */

}
