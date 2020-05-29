package com.spade.storm.logmonitor;

import com.spade.storm.logmonitor.bolt.FilterBlot;
import com.spade.storm.logmonitor.bolt.NotifyBolt;
import com.spade.storm.logmonitor.bolt.PersistBolt;
import com.spade.storm.logmonitor.utils.Constants;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpoutConfig.Builder;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Brian
 * @Date: 2020/05/28/21:03
 * @Description:
 * 项目启动类
 */
public class LogMonitorDriver {

    public static void main(String[] args) throws Exception {
        //1.创建Topology程序
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        Builder<String, String> kafkaSpoutBuilder = new Builder<String, String>(Constants.KAFKA_SERVERS, Constants.LOG_TOPIC);
        kafkaSpoutBuilder.setProcessingGuarantee(KafkaSpoutConfig.ProcessingGuarantee.AT_MOST_ONCE);
        kafkaSpoutBuilder.setProp("group.id", Constants.KAFKA_CONSUMER_GROUP);
        kafkaSpoutBuilder.setProp("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaSpoutBuilder.setProp("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaSpout<String, String> spout = new KafkaSpout<String, String>(kafkaSpoutBuilder.build());

        //2.用KafkaSpout消费Kafka中的消息
        topologyBuilder.setSpout(Constants.KAFKA_SPOUT_NAME, spout, 2);
        //3.定义Bolt进行过滤规则，发送警告，持久化警告
        topologyBuilder.setBolt(Constants.FILTER_BOLT_NAME, new FilterBlot(), 2).shuffleGrouping(Constants.KAFKA_SPOUT_NAME);
        topologyBuilder.setBolt(Constants.NOTIFY_BOLT_NAME, new NotifyBolt(), 2).fieldsGrouping(Constants.FILTER_BOLT_NAME, new Fields("appId"));
        topologyBuilder.setBolt(Constants.PERSIST_BOLT_NAME, new PersistBolt(), 2).shuffleGrouping(Constants.NOTIFY_BOLT_NAME);

        //4.设置worker数量 （JVM数量）并提交topology程序到nimbus
        Config config = new Config();
        config.setNumWorkers(1);
//        StormSubmitter.submitTopology(Constants.TOPOLOGY_NAME, config, topologyBuilder.createTopology());
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology(Constants.TOPOLOGY_NAME, config, topologyBuilder.createTopology());

    }
}
