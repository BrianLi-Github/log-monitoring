package com.spade.storm.logmonitor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Brian
 * @Date: 2020/05/28/21:50
 * @Description:
 * 定义消息实体类 --> storm框架各组件之间传递的对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogMessage implements Serializable {
     private String appId;
     private String appName;
     private String ruleId;
     private String keyword;
     private int isEmail;//告警信息是否通过邮件告警
     private int isPhone;//告警信息是否通过短信告警
     private String messageContent;

     public LogMessage(String appId, String messageContent) {
          this.appId = appId;
          this.messageContent = messageContent;
     }
}
