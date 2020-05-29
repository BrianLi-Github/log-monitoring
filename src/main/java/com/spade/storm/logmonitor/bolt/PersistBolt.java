package com.spade.storm.logmonitor.bolt;

import com.spade.storm.logmonitor.entity.LogMessage;
import com.spade.storm.logmonitor.utils.LogMonitorHandler;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Brian
 * @Date: 2020/05/28/21:20
 * @Description:
 */
public class PersistBolt extends BaseRichBolt {

    OutputCollector collector = null;
    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    public void execute(Tuple input) {
        LogMessage message = (LogMessage) input.getValueByField("record");
        //TODO 将告警信息保存到数据库中，这是信息的处理状态为：未读
        System.out.println("++++++++++++++++将告警信息保存到MySQL中：" + message.getMessageContent());
        LogMonitorHandler.createMessageRecord(message);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
