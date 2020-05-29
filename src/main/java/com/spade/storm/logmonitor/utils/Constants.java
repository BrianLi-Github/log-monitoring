package com.spade.storm.logmonitor.utils;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Brian
 * @Date: 2020/05/28/20:54
 * @Description:
 * 全局常量类
 */
public class Constants {
    public static String KAFKA_SERVERS = "shizhan01:9092,shizhan02:9092,shizhan03:9092";
    public static String LOG_TOPIC = "log-monitor";
    public static String TOPOLOGY_NAME = "LogMonitorTopology";
    public static String KAFKA_SPOUT_NAME = "LogMonitorSpout";//消费kafka中消息的spout 名字
    public static String KAFKA_CONSUMER_GROUP = "LogMonitorConsumerGroup";//Kafka消费组
    public static String FILTER_BOLT_NAME = "FilterBolt";    //根据触发规则过滤日志信息
    public static String NOTIFY_BOLT_NAME = "NotifyBolt";    //发送邮件，短信通知
    public static String PERSIST_BOLT_NAME = "PersistBolt";  //将处理好的数据持久化
    public static String RULE_REDIS_KEY = "LogMonitorRules";  //规则缓存的key


    public static String REDIS_HOST = "192.168.71.1";
    public static String REDIS_PWD = "123456";
}
