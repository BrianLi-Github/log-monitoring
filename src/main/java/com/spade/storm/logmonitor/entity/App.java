package com.spade.storm.logmonitor.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Brian
 * @Date: 2020/05/29/12:47
 * @Description:
 */
@Data
public class App {
    private int id;//应用编号
    private String name;//应用名称
    private int isOnline;//应用是否在线
    private int typeId;//应用所属类别
    private String userId;//应用的负责人，多个用户用逗号分开
}
