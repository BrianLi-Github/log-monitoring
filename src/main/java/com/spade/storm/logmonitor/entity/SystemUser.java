package com.spade.storm.logmonitor.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Brian
 * @Date: 2020/05/29/13:06
 * @Description:
 */
@Data
public class SystemUser {
    private int id;//用户编号
    private String name;//用户名称
    private String mobile;//用户手机
    private String email;//用户邮箱
    private int isValid;//用户是否可用
}
