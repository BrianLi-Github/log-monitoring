package com.spade.storm.logmonitor.dao;

import com.spade.storm.logmonitor.entity.*;
import com.spade.storm.logmonitor.utils.DataSourceUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Brian
 * @Date: 2020/05/29/9:28
 * @Description:
 * 数据库操作类
 */
public class LogMonitorDao {

    private static JdbcTemplate jdbcTemplate;

    static {
        jdbcTemplate = new JdbcTemplate(DataSourceUtils.getDataSource());
    }


    public static List<App> findAppList() {
        return jdbcTemplate.query("SELECT id,name,isOnline,typeId,userId  FROM log_monitor.log_monitor_app WHERE isOnline =1", new BeanPropertyRowMapper<App>(App.class));
    }

    public static List<AppRule> findActiveRules() {
        return jdbcTemplate.query("SELECT id,name,keyword,isValid,appId FROM log_monitor.log_monitor_rule WHERE isValid =1", new BeanPropertyRowMapper<AppRule>(AppRule.class));
    }

    public static List<AppUser> findAppUsers() {
        return jdbcTemplate.query("SELECT id,appId,name,mobile,email,isValid FROM log_monitor.log_monitor_user WHERE isValid =1", new BeanPropertyRowMapper<AppUser>(AppUser.class));
    }

    public static List<SystemUser> findSysUsers() {
        return jdbcTemplate.query("SELECT id,name,mobile,email,isValid FROM log_monitor.log_monitor_user WHERE isValid =1", new BeanPropertyRowMapper<SystemUser>(SystemUser.class));
    }

    public static void main(String[] args) {
        List<App> appList = LogMonitorDao.findAppList();
        appList.forEach(app -> System.out.println(app.getId()));
        List<AppRule> ruleList = LogMonitorDao.findActiveRules();
        ruleList.forEach(app -> System.out.println(app.getId()));
        List<AppUser> appUsers = LogMonitorDao.findAppUsers();
        appUsers.forEach(app -> System.out.println(app.getId()));
    }

    public static void createMsgRecord(LogMessageRecord record) {
        jdbcTemplate.update("INSERT INTO log_monitor.log_monitor_rule_record \n" +
                "(appId,ruleId,isEmail,isPhone,isColse,noticeInfo,updataDate) \n" +
                "VALUES ( ?,?,?,?,?,?,?)",
                record.getAppId(), record.getRuleId(), record.getIsEmail(),
                record.getIsPhone(), record.getIsClose(), record.getMessageContent(), new Date());
    }
}
