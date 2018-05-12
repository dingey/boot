/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : boot

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-05-12 22:45:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `url` varchar(255) NOT NULL DEFAULT '',
  `permission` varchar(255) NOT NULL DEFAULT '',
  `parent_id` int(11) unsigned NOT NULL DEFAULT '0',
  `sequence` int(11) unsigned NOT NULL DEFAULT '0',
  `icon` varchar(64) NOT NULL DEFAULT '',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型：0无；1头部导航；2一级菜单；3子菜单；只有0，3连接可点击；',
  `authc` bit(1) NOT NULL DEFAULT b'1' COMMENT '需要授权',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '管理主页', '/admin/index', '', '0', '0', '', '1', '', '2018-01-09 19:07:47', '\0');
INSERT INTO `permission` VALUES ('2', '系统', '', '', '0', '0', '', '1', '\0', '2018-01-09 20:22:55', '\0');
INSERT INTO `permission` VALUES ('3', '设置', '', '', '2', '0', '<i class=\"fa fa-fw fa-gear\"></i>', '2', '\0', '2018-01-09 16:25:31', '\0');
INSERT INTO `permission` VALUES ('4', '角色设置', '/admin/role/list', '', '3', '0', '<i class=\"fa fa-fw fa-user-secret\"></i>', '3', '', '2018-01-09 17:59:51', '\0');
INSERT INTO `permission` VALUES ('5', '权限设置', '/admin/permission/list', '', '3', '1', '<i class=\"fa fa-fw fa-key\"></i>', '3', '\0', '2018-01-09 18:00:15', '\0');
INSERT INTO `permission` VALUES ('6', '角色编辑页', '/admin/role/edit', '', '4', '0', '', '0', '', '2018-01-09 16:26:14', '\0');
INSERT INTO `permission` VALUES ('7', '角色保存', '/admin/role/save', '', '4', '1', '', '0', '', '2018-01-09 16:26:15', '\0');
INSERT INTO `permission` VALUES ('8', '权限编辑', '/admin/permission/edit', '', '5', '0', '', '0', '', '2018-01-09 16:26:15', '\0');
INSERT INTO `permission` VALUES ('9', '权限删除', '/admin/permission/del', '', '5', '1', '', '0', '', '2018-01-09 16:26:16', '\0');
INSERT INTO `permission` VALUES ('10', '权限排序', '/admin/permission/sort', '', '5', '2', '', '0', '', '2018-01-09 16:26:17', '\0');
INSERT INTO `permission` VALUES ('11', '权限新增', '/admin/permission/add', '', '5', '3', '', '0', '', '2018-01-09 16:26:17', '\0');
INSERT INTO `permission` VALUES ('12', '权限保存', '/admin/permission/save', '', '5', '4', '', '0', '', '2018-01-09 16:26:19', '\0');
INSERT INTO `permission` VALUES ('19', '用户', '', '', '0', '1', '', '1', '', '2018-01-09 19:31:22', '\0');
INSERT INTO `permission` VALUES ('20', '用户管理', '', '', '19', '0', '<i class=\"fa fa-fw fa-users\"></i>', '2', '', '2018-01-09 19:35:18', '\0');
INSERT INTO `permission` VALUES ('21', '用户列表', '/admin/user/list', '', '20', '0', '<i class=\"fa fa-fw fa-user\"></i>', '3', '', '2018-05-11 21:39:57', '\0');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `desc` varchar(128) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', '管理员', '2017-12-30 16:56:09', '\0');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` int(11) unsigned NOT NULL,
  `permission_id` int(11) unsigned NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(32) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  `salt` varchar(64) NOT NULL DEFAULT '',
  `state` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `del_flag` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin', '3fbc5eeb9bf722a3414fd95c64e1957c', 'a5574c1c57975a527270eee616b2b589', '0', '2017-12-19 11:21:54', '\0');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL DEFAULT '0',
  `role_id` int(11) unsigned NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1', '2018-01-09 17:52:32', '\0');
