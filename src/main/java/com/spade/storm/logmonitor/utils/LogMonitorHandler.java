package com.spade.storm.logmonitor.utils;

import com.google.common.collect.Maps;
import com.spade.storm.logmonitor.dao.LogMonitorDao;
import com.spade.storm.logmonitor.entity.*;
import com.spade.storm.logmonitor.utils.mail.MailInfo;
import com.spade.storm.logmonitor.utils.mail.MessageSender;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Brian
 * @Date: 2020/05/29/9:13
 * @Description:
 */
public class LogMonitorHandler {

    //已授权的APP列表缓存
    private static List<App> appList = null;
    //告警规则缓存
    private static Map<String, List<AppRule>> appRuleMap = null;
    //监控系统负责人
    private static List<SystemUser> sysUserList = null;
    //各系统负责人
    private static Map<String, List<AppUser>> appUserMap = null;
    //当前时间点是否已经重新加载了数据
    private static boolean reloaded = false;

    static {
        loadData();
    }

    public static boolean validateApp(String appId, LogMessage message) {
        if (StringUtils.isBlank(appId)) {
            return false;
        }
        if (appList == null) {
            loadData();
        }
        for (App app : appList) {
            if (app != null && 1 == app.getIsOnline()) {
                message.setAppName(app.getName());
                return true;
            }
        }
        return false;
    }

    public static void refreshData() {
        String date = getDateTime();
        int now = Integer.parseInt(date.split(":")[1]);
        if (now % 10 == 0) {//每10分钟加载一次
            //1,2,3,4,5,6
            reloadData();
        }else {
            reloaded = true;
        }
    }

    private static String getDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    private static synchronized void reloadData() {
        //只有一个线程进来，将数据重新加载后，将锁设置位false，下一个线程进来就不会重新加载
         if (reloaded) {
             loadData();
             reloaded = false;
         }
    }

    private static void loadData() {
        loadApp();
        loadRules();
        loadAppUsers();
        loadSysUsers();
    }

    private static void loadAppUsers() {
        List<AppUser> appUsers = LogMonitorDao.findAppUsers();
        appUserMap = Maps.newHashMap();
        appList.forEach(app -> {
            for (AppUser user : appUsers) {
                if (app.getId() == user.getAppId()) {
                    appUserMap.put(String.valueOf(app.getId()), appUsers);
                }
            }
        });
    }

    private static void loadSysUsers() {
        sysUserList = LogMonitorDao.findSysUsers();
    }

    private static void loadApp() {
        appList = LogMonitorDao.findAppList();
    }

    public static boolean validateTrigger(String appId, LogMessage message) {
        if (StringUtils.isBlank(appId)) {
            return false;
        }
        List<AppRule> rules = appRuleMap.get(appId);
        for (AppRule rule : rules) {
            if (message.getMessageContent().contains(rule.getKeyword())) {
                message.setRuleId(String.valueOf(rule.getId()));
                message.setKeyword(rule.getKeyword());
                return true;
            }
        }
        return false;
    }

    private static void loadRules() {
         List<AppRule> ruleList = LogMonitorDao.findActiveRules();
         appRuleMap = ruleList.parallelStream().collect(Collectors.groupingBy((AppRule appRule) -> String.valueOf(appRule.getAppId())));
    }

    /**
     * 发送邮件
     * 后期可以改造为将邮件数据发送到外部的消息队里中，然后创建一个worker去发送短信。
     * @param appId
     * @param message
     * @return
     */
    public static boolean sendEmail(String appId, LogMessage message) {
        List<AppUser> appUsers = appUserMap.get(appId);
        if (CollectionUtils.isEmpty(appUsers) && CollectionUtils.isEmpty(sysUserList)) {
            return false;
        }
        Set<String> receiver = new HashSet<>();
        for (AppUser appUser : appUsers) {
            if (1 == appUser.getIsValid()) {
                receiver.add(appUser.getEmail());
            }
        }
        for (SystemUser user : sysUserList) {
            if (1 == user.getIsValid()) {
                receiver.add(user.getEmail());
            }
        }
        for (App app : appList) {
            if (app.getId() == Integer.parseInt(appId.trim())) {
                message.setAppName(app.getName());
                break;
            }
        }
        if (receiver.size() >= 1) {
            String date = getDateTime();
            String content = "系统【" + message.getAppName() + "】在 " + date + " 触发规则 " + message.getRuleId() + " ，过滤关键字为：" + message.getKeyword() + "  错误内容：" + message.getMessageContent();
            MailInfo mailInfo = new MailInfo("系统运行日志监控", content, new ArrayList<>(receiver), null);
            return MessageSender.sendMail(mailInfo);
        }
        return false;
    }

    public static boolean sendSms(String appId, LogMessage message) {
        List<AppUser> appUsers = appUserMap.get(appId);
        if (CollectionUtils.isEmpty(appUsers) && CollectionUtils.isEmpty(sysUserList)) {
            return false;
        }
        //TODO send sms
        return false;
    }

    public static void createMessageRecord(LogMessage message) {
        LogMessageRecord record = new LogMessageRecord();
        record.setAppId(Integer.parseInt(message.getAppId()));
        record.setRuleId(Integer.parseInt(message.getRuleId()));
        record.setIsEmail(message.getIsEmail());
        record.setIsPhone(message.getIsPhone());
        record.setMessageContent(message.getMessageContent());
        LogMonitorDao.createMsgRecord(record);
    }
}
