/*
Navicat MySQL Data Transfer

Source Server         : zsmome
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : his

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2018-02-04 18:05:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL auto_increment,
  `depNo` varchar(255) default NULL COMMENT '部门编号',
  `depName` varchar(255) default NULL COMMENT '部门名字',
  `depDescribe` varchar(255) default NULL COMMENT '部门描述',
  `status` tinyint(1) default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1349 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('212', '我是hhh', '我是新增', '', '1');
INSERT INTO `department` VALUES ('217', 'Dep-1894', '实施部', '动起来！动起来！动起来！', '1');
INSERT INTO `department` VALUES ('221', 'Dep-1884', 'UI部', '你这个界面好看！', '1');
INSERT INTO `department` VALUES ('223', 'Dep-1979', '实施部', '动起来！动起来！动起来！', '1');
INSERT INTO `department` VALUES ('225', 'Dep-1921', '研发部', '学世界第一技术！做世界第一产品！', '1');
INSERT INTO `department` VALUES ('227', 'Dep-1977', 'UI部', '你这个界面不好看！', '1');
INSERT INTO `department` VALUES ('228', 'Dep-1944', '研发部', '学世界第一技术！做世界第一产品！dsfsdfsdf', '1');
INSERT INTO `department` VALUES ('241', 'Dep-1858', '8765-66', '无描述', '1');
INSERT INTO `department` VALUES ('246', 'Dep-1874', '研发部', '学世界第一技术！做世界第一产品！', '1');
INSERT INTO `department` VALUES ('1335', 'ddddd', 'Uddd', '无描述内容', '0');
INSERT INTO `department` VALUES ('1336', 'ddddd', 'Uddd', '无描述内容', '0');
INSERT INTO `department` VALUES ('1337', 'dwerwe', 'Uddd', '无描述内容', '0');
INSERT INTO `department` VALUES ('1338', 'dwerwe', 'Uddd', '无描述内容', '0');
INSERT INTO `department` VALUES ('1339', 'old-wang', 'ddddd', 'dsdfsdfds', '0');
INSERT INTO `department` VALUES ('1340', 'dsfsdf', 'sdfsadf', 'sdf', '0');
INSERT INTO `department` VALUES ('1341', '1122-1', 'ddd-d', '无描述内容', '0');
INSERT INTO `department` VALUES ('1342', 'dfgdfg', 'sssss', 'sss', '0');
INSERT INTO `department` VALUES ('1343', 'sdsdf', 'sdfsdf', 'sdfdf', '0');
INSERT INTO `department` VALUES ('1344', 'dfgdfgd', 'dfgdf', 'dfgf', '0');
INSERT INTO `department` VALUES ('1345', 'erwerwe', 'eeee', '', '0');
INSERT INTO `department` VALUES ('1346', 'zsmome', '123456', 'fdfdf', '0');
INSERT INTO `department` VALUES ('1347', '123456', '1234', '', '0');
INSERT INTO `department` VALUES ('1348', '132dsdf', 'sdf', '', '0');

-- ----------------------------
-- Table structure for roles_permissions
-- ----------------------------
DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE `roles_permissions` (
  `id` int(11) NOT NULL auto_increment,
  `role_name` varchar(255) default NULL,
  `permission` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roles_permissions
-- ----------------------------
INSERT INTO `roles_permissions` VALUES ('1', 'admin', 'create');
INSERT INTO `roles_permissions` VALUES ('2', 'admin', 'update');
INSERT INTO `roles_permissions` VALUES ('3', 'admin', 'delete');
INSERT INTO `roles_permissions` VALUES ('4', 'admin', 'view');

-- ----------------------------
-- Table structure for subscription
-- ----------------------------
DROP TABLE IF EXISTS `subscription`;
CREATE TABLE `subscription` (
  `id` int(11) NOT NULL auto_increment,
  `sub_theme` varchar(255) default NULL COMMENT '发布主题',
  `sub_author` varchar(255) default NULL COMMENT '发布者',
  `sub_content` text COMMENT '发布内容',
  `sub_date` datetime default NULL COMMENT '发布时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subscription
-- ----------------------------
INSERT INTO `subscription` VALUES ('1', '2', null, null, null);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) default NULL,
  `password_salt` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('12', 'zsmome', '9e3aa1d8deddb29f1898606426b25f0b', '0eaa9f92f6d7d7c8ca3911e9dfe70503');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(255) default NULL COMMENT '用户名',
  `head_portraits` varchar(255) default NULL COMMENT '用户名对应的头像',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'zsmome', 'http://192.168.183.130:9999/group1/M00/00/00/wKi3glp2WfKANP-YAAAasmRqhgk367.jpg');

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(255) default NULL,
  `role_name` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES ('1', 'zsmome', 'admin');
INSERT INTO `user_roles` VALUES ('2', 'tourist', 'tourist');
INSERT INTO `user_roles` VALUES ('3', 'smallpig', 'admin');
INSERT INTO `user_roles` VALUES ('4', '718932505@qq.com', 'admin');
