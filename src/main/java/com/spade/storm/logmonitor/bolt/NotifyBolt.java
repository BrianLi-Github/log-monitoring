package com.spade.storm.logmonitor.bolt;

import com.spade.storm.logmonitor.entity.LogMessage;
import com.spade.storm.logmonitor.utils.LogMonitorHandler;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Brian
 * @Date: 2020/05/28/21:19
 * @Description:
 */
public class NotifyBolt extends BaseRichBolt {

    private static final Logger log = LoggerFactory.getLogger(NotifyBolt.class);

    OutputCollector collector = null;

    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    public void execute(Tuple input) {
        //获取appId，根据appId获取MySQL中指定的负责人的联系方式，并发送相关告警信息
        String appId = input.getStringByField("appId");
        LogMessage message = (LogMessage) input.getValueByField("message");
        System.out.println("---------------向系统相关的负责人发送告警信息：" + message.getMessageContent());
        if (LogMonitorHandler.sendEmail(appId, message)) {
            message.setIsEmail(1);
        }
        if (LogMonitorHandler.sendSms(appId, message)) {
            message.setIsPhone(1);
        }
        collector.emit(new Values(message));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("record"));
    }
}
