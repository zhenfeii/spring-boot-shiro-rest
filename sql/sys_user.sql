/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50546
Source Host           : localhost:3306
Source Database       : online

Target Server Type    : MYSQL
Target Server Version : 50546
File Encoding         : 65001

Date: 2018-11-01 16:33:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(20) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `hash` varchar(200) DEFAULT NULL COMMENT '登录时所带的随机数',
  `mail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'huang', '202cb962ac59075b964b07152d234b70', null, '94m07ci464', null);
INSERT INTO `sys_user` VALUES ('2', 'user', '202cb962ac59075b964b07152d234b70', null, null, null);
