package com.spade.storm.logmonitor.bolt;

import com.spade.storm.logmonitor.entity.LogMessage;
import com.spade.storm.logmonitor.utils.LogMonitorHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Brian
 * @Date: 2020/05/28/21:17
 * @Description:
 * 过滤日志信息，将和触发规则的消息发射到NotifyBolt中
 */
public class FilterBlot extends BaseRichBolt {
    OutputCollector collector = null;
    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    //Kafka消息格式   APPID$$$$$messageContent
    //例： 1$$$$$error: Caused by: java.lang.NoClassDefFoundError: com/starit/gejie/dao/SysNameDao
    public void execute(Tuple input) {
        String line = (String) input.getValue(4);
        //日志内容分为两个部分：由5个$$$$$符号作为分隔符，第一部分为appid，第二部分为日志内容。
        String[] messageArr = line.split("\\$\\$\\$\\$\\$");
        //对日志进行校验
        if (messageArr.length != 2) {
            return;
        }
        if (StringUtils.isBlank(messageArr[0]) || StringUtils.isBlank(messageArr[1])) {
            return;
        }
        String appId = messageArr[0].trim();
        String msgContent = messageArr[1].trim();
        LogMessage message = new LogMessage(appId, msgContent);
        //TODO 获取MySQL中已授权的APPID，如果没有授权则返回
        if (!LogMonitorHandler.validateApp(appId, message)) {
            return;
        }

        //TODO 校验该APP下的触发规则
        if (LogMonitorHandler.validateTrigger(appId, message)) {
            collector.emit(new Values(appId, message));
        }

        //TODO 定时更新redis中的规则
        //这里需要注意，nextTuple方法是不间断地被调用，更新需要加上锁
        LogMonitorHandler.refreshData();
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("appId", "message"));
    }
}
