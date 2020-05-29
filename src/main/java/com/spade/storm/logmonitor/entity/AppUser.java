package com.spade.storm.logmonitor.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Brian
 * @Date: 2020/05/29/13:06
 * @Description:
 * 系统对应的负责人
 */
@Data
public class AppUser {
    private int id;//用户编号
    private int appId;//系统APPID
    private String name;//用户名称
    private String mobile;//用户手机
    private String email;//用户邮箱
    private int isValid;//用户是否可用
}
