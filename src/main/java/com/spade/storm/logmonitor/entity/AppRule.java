package com.spade.storm.logmonitor.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Brian
 * @Date: 2020/05/29/12:50
 * @Description:
 */
@Data
public class AppRule {
    private int id;//规则编号
    private String name;//规则名称
    private String keyword;//规则过滤的关键字
    private int isValid;//规则是否可用
    private int appId;//规则所属的应用
}
