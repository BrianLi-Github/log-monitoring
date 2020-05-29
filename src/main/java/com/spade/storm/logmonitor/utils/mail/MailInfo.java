package com.spade.storm.logmonitor.utils.mail;

import lombok.Data;

import java.util.List;
import java.util.Properties;

@Data
public class MailInfo {
    private String mailServerHost; // 发送邮件的服务器的IP
    private String mailServerPort = "25"; // 发送邮件的服务器端口
    private String userName; // 登陆邮件发送服务器的用户名
    private String userPassword; // 登陆邮件发送服务器的密码
    private String fromAddress; // 邮件发送者的地址
    private String toAddress; // 邮件接收者的地址
    private String ccAddress; // 邮件抄送者的地址
    private String fromUserName = "黑桃日志监控平台"; // 邮件发送者的名称，显示在他人邮件的发件人
    private String mailSubject; // 邮件主题
    private String mailContent; // 邮件的文本内容
    private boolean authValidate = true; // 是否需要身份验证
    private Properties properties; // 邮件会话属性

    public MailInfo() {
    }

    public MailInfo(String title, String content, List<String> receiver, List<String> ccList) {
        this.mailServerHost = MailCenterConstant.SMTP_SERVER;
        this.userName = MailCenterConstant.USER;
        this.userPassword = MailCenterConstant.PWD;
        this.fromAddress = MailCenterConstant.FROM_ADDRESS;
        this.toAddress = listToStringFormat(receiver);
        this.ccAddress = ccList==null?"":listToStringFormat(ccList);
        this.mailSubject = title;
        this.mailContent = content;
    }

    private synchronized String listToStringFormat(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                stringBuilder.append(list.get(i));
            } else {
                stringBuilder.append(list.get(i)).append(",");
            }
        }
        return stringBuilder.toString();
    }

    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", this.mailServerHost);
        p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.smtp.auth", authValidate ? "true" : "false");
        p.put("mail.smtp.starttls.enable", "true");
        return p;
    }
}
