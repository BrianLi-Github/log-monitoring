/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : log_monitor

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-05-29 21:17:53
*/
CREATE DATABASE `log_monitor`;

USE `log_monitor`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `log_monitor_app`
-- ----------------------------
DROP TABLE IF EXISTS `log_monitor_app`;
CREATE TABLE `log_monitor_app` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '应用编号',
  `name` varchar(100) DEFAULT NULL COMMENT '应用名称',
  `desc` varchar(250) DEFAULT NULL COMMENT '应用简要描述',
  `isOnline` int DEFAULT NULL COMMENT '应用是否在线',
  `typeId` int DEFAULT NULL COMMENT '应用类型对应的ID',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '应用录入时间',
  `updateaDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '应用信息修改时间',
  `createUser` varchar(100) DEFAULT NULL COMMENT '创建用户',
  `updateUser` varchar(100) DEFAULT NULL COMMENT '修改用户',
  `userId` varchar(100) DEFAULT NULL COMMENT '应用所属用户，多个用户用逗号分开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_monitor_app
-- ----------------------------
INSERT INTO `log_monitor_app` VALUES ('1', 'storm集群', 'storm集群', '1', '1', '2020-05-29 21:12:54', '2020-05-29 16:58:21', 'Brian', 'Brian', '1');
INSERT INTO `log_monitor_app` VALUES ('2', 'java应用', 'java引用', '1', '1', '2020-05-29 21:12:59', '2020-05-29 09:55:45', 'Brian', 'Brian', '1');

-- ----------------------------
-- Table structure for `log_monitor_app_type`
-- ----------------------------
DROP TABLE IF EXISTS `log_monitor_app_type`;
CREATE TABLE `log_monitor_app_type` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '应用类型编号',
  `name` varchar(100) DEFAULT NULL COMMENT '应用类型名称，如linux，web，java，storm，kafka等',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '应用类型录入时间',
  `updataDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '应用类型修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_monitor_app_type
-- ----------------------------
INSERT INTO `log_monitor_app_type` VALUES ('1', 'storm', '2020-05-29 21:12:46', '2020-05-29 16:58:42');

-- ----------------------------
-- Table structure for `log_monitor_app_user`
-- ----------------------------
DROP TABLE IF EXISTS `log_monitor_app_user`;
CREATE TABLE `log_monitor_app_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户编号，自增长',
  `appId` int DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '用户名称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '用户手机号码',
  `email` varchar(50) DEFAULT NULL COMMENT '用户的邮箱地址，默认为公司邮箱',
  `isValid` int DEFAULT NULL COMMENT '用户是否有效',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户录入时间',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '用户信息修改时间',
  `createUser` varchar(100) DEFAULT NULL COMMENT '创建用户',
  `updateUser` varchar(100) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_monitor_app_user
-- ----------------------------
INSERT INTO `log_monitor_app_user` VALUES ('1', null, 'Brian', '18278420675', '1332966295@qq.com', '1', '2020-05-29 21:12:31', '2020-05-29 16:59:13', 'Brian', 'Brian');
INSERT INTO `log_monitor_app_user` VALUES ('2', null, '森', '13229982732', 'fendou_lsc@126.com', '1', '2020-05-29 21:12:34', '2020-05-29 16:59:13', 'Brian', 'Brian');
INSERT INTO `log_monitor_app_user` VALUES ('3', null, '昌', '13229982732', 'brian.li@accentrix.com', '1', '2020-05-29 21:12:35', '2020-05-29 16:59:13', 'Brian', 'Brian');

-- ----------------------------
-- Table structure for `log_monitor_rule`
-- ----------------------------
DROP TABLE IF EXISTS `log_monitor_rule`;
CREATE TABLE `log_monitor_rule` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '规则编号，自增长',
  `name` varchar(100) DEFAULT NULL COMMENT '规则名称',
  `desc` varchar(250) DEFAULT NULL COMMENT '规则描述',
  `keyword` varchar(100) DEFAULT NULL COMMENT '规则关键词',
  `isValid` int DEFAULT NULL COMMENT '规则是否可用',
  `appId` int DEFAULT NULL COMMENT '规则所属应用',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '规则创建时间',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '规则修改时间',
  `createUser` varchar(100) DEFAULT NULL COMMENT '创建用户',
  `updateUser` varchar(100) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_monitor_rule
-- ----------------------------
INSERT INTO `log_monitor_rule` VALUES ('1', 'exe', 'Exception', 'Exception', '1', '1', '2020-05-29 21:12:15', '2020-05-29 16:57:25', 'Brian', 'Brian');
INSERT INTO `log_monitor_rule` VALUES ('2', 'sys', '测试数据', 'sys', '1', '2', '2020-05-29 21:12:08', '2020-05-29 21:12:06', 'Brian', 'Brian');
INSERT INTO `log_monitor_rule` VALUES ('3', 'error', '错误信息', 'error', '1', '3', '2020-05-29 21:12:20', '2020-05-29 16:00:58', 'Brian', 'Brian');

-- ----------------------------
-- Table structure for `log_monitor_rule_record`
-- ----------------------------
DROP TABLE IF EXISTS `log_monitor_rule_record`;
CREATE TABLE `log_monitor_rule_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '告警信息编号',
  `appId` int DEFAULT NULL COMMENT '告警信息所属应用编号',
  `ruleId` int DEFAULT NULL COMMENT '告警信息所属规则编号',
  `isEmail` int DEFAULT '0' COMMENT '是否邮件告知，0：未告知  1：告知',
  `isPhone` int DEFAULT '0' COMMENT '是否短信告知，0：未告知  1：告知',
  `isColse` int DEFAULT '0' COMMENT '是否处理完毕，0：未处理  1：已处理',
  `noticeInfo` varchar(500) DEFAULT NULL COMMENT '告警信息明细',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '告警信息入库时间',
  `updataDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '告警信息修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=281 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_monitor_rule_record
-- ----------------------------
INSERT INTO `log_monitor_rule_record` VALUES ('267', '1', '1', '0', '0', '0', 'error java.lang.ClassCastException', '2020-05-29 16:39:21', '2020-05-29 16:39:20');
INSERT INTO `log_monitor_rule_record` VALUES ('268', '1', '1', '0', '0', '0', 'error java.lang.ArrayIndexOutOfBoundsException', '2020-05-29 16:50:42', '2020-05-29 16:50:42');
INSERT INTO `log_monitor_rule_record` VALUES ('269', '1', '1', '0', '0', '0', 'error java.lang.ArithmeticException', '2020-05-29 16:50:42', '2020-05-29 16:50:42');
INSERT INTO `log_monitor_rule_record` VALUES ('270', '1', '1', '0', '0', '0', 'error java.lang.ArithmeticException:  by zero', '2020-05-29 16:50:51', '2020-05-29 16:50:51');
INSERT INTO `log_monitor_rule_record` VALUES ('271', '1', '1', '0', '0', '0', 'error:Servlet.service() for servlet action threw exception java.lang.NullPointerException', '2020-05-29 16:53:18', '2020-05-29 16:53:15');
INSERT INTO `log_monitor_rule_record` VALUES ('272', '1', '1', '0', '0', '0', 'error:Java.lang.TypeNotPresentException', '2020-05-29 16:53:26', '2020-05-29 16:53:25');
INSERT INTO `log_monitor_rule_record` VALUES ('273', '1', '1', '0', '0', '0', 'error:Servlet.service() for servlet action threw exception java.lang.NullPointerException', '2020-05-29 16:53:36', '2020-05-29 16:53:36');
INSERT INTO `log_monitor_rule_record` VALUES ('274', '1', '1', '0', '0', '0', 'error:Java.lang.IllegalStateException', '2020-05-29 16:53:36', '2020-05-29 16:53:37');
INSERT INTO `log_monitor_rule_record` VALUES ('275', '1', '1', '0', '0', '0', 'error:Exception in thread main java.lang.ArrayIndexOutOfBoundsException: 2', '2020-05-29 16:53:37', '2020-05-29 16:53:37');
INSERT INTO `log_monitor_rule_record` VALUES ('276', '1', '1', '0', '0', '0', 'error java.lang.IllegalMonitorStateException', '2020-05-29 16:53:38', '2020-05-29 16:53:38');
INSERT INTO `log_monitor_rule_record` VALUES ('277', '1', '1', '0', '0', '0', 'error:Java.lang.IllegalMonitorStateException', '2020-05-29 16:53:38', '2020-05-29 16:53:39');
INSERT INTO `log_monitor_rule_record` VALUES ('278', '1', '1', '0', '0', '0', 'error: java.lang.CloneNotSupportedException', '2020-05-29 16:53:38', '2020-05-29 16:53:39');
INSERT INTO `log_monitor_rule_record` VALUES ('279', '1', '1', '0', '0', '0', 'error:Servlet.service() for servlet action threw exception java.lang.NullPointerException', '2020-05-29 16:53:39', '2020-05-29 16:53:39');
INSERT INTO `log_monitor_rule_record` VALUES ('280', '1', '1', '1', '0', '0', 'error:Java.lang.IllegalStateException', '2020-05-29 19:22:47', '2020-05-29 19:22:48');

-- ----------------------------
-- Table structure for `log_monitor_user`
-- ----------------------------
DROP TABLE IF EXISTS `log_monitor_user`;
CREATE TABLE `log_monitor_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户编号，自增长',
  `name` varchar(20) DEFAULT NULL COMMENT '用户名称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '用户手机号码',
  `email` varchar(50) DEFAULT NULL COMMENT '用户的邮箱地址，默认为公司邮箱',
  `isValid` int DEFAULT NULL COMMENT '用户是否有效',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户录入时间',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '用户信息修改时间',
  `createUser` varchar(100) DEFAULT NULL COMMENT '创建用户',
  `updateUser` varchar(100) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_monitor_user
-- ----------------------------
INSERT INTO `log_monitor_user` VALUES ('1', 'Brian', '18278420675', '1332966295@qq.com', '1', '2020-05-29 21:16:03', '2020-05-29 16:59:13', 'Brian', 'Brian');
INSERT INTO `log_monitor_user` VALUES ('2', 'Sen', '13229982732', 'fendou_lsc@126.com', '1', '2020-05-29 21:16:23', '2020-05-29 15:00:16', 'Brian', 'Brian');
INSERT INTO `log_monitor_user` VALUES ('3', '昌', '13229982732', 'brian.li@accentrix.com', '1', '2020-05-29 21:16:12', '2020-05-29 15:01:13', 'Brian', 'Brian');
