package com.spade.storm.logmonitor.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Brian
 * @Date: 2020/05/29/14:33
 * @Description:
 */
@Data
public class LogMessageRecord {
    private int id;//告警信息编号
    private int appId;//告警信息所属的应用
    private int ruleId;//告警信息所属的规则
    private int isEmail;//告警信息是否通过邮件告警
    private int isPhone;//告警信息是否通过短信告警
    private int isClose;//告警信息是否处理完毕
    private String messageContent;//原始日志信息
}
