/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : boot

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2018-01-16 09:34:00
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
INSERT INTO `permission` VALUES ('21', '用户列表', '/user/list', '', '20', '0', '<i class=\"fa fa-fw fa-user\"></i>', '3', '', '2018-01-09 19:35:47', '\0');

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', '管理员', '2017-12-30 16:56:09', '\0');
INSERT INTO `role` VALUES ('2', '2', '二号', '2018-01-08 20:14:53', '\0');
INSERT INTO `role` VALUES ('3', '3', '333', '2017-12-30 17:04:57', '\0');
INSERT INTO `role` VALUES ('4', '4', '4', '2017-12-30 17:05:04', '\0');
INSERT INTO `role` VALUES ('5', '5', '5', '2017-12-30 17:16:27', '\0');
INSERT INTO `role` VALUES ('6', '6', '6', '2017-12-30 17:16:36', '\0');
INSERT INTO `role` VALUES ('7', '7', '7', '2017-12-30 17:16:46', '\0');
INSERT INTO `role` VALUES ('8', '8', '8', '2017-12-30 17:16:57', '\0');
INSERT INTO `role` VALUES ('9', '9', '9', '2017-12-30 17:17:10', '\0');
INSERT INTO `role` VALUES ('10', '10', '10', '2018-01-03 09:43:16', '\0');
INSERT INTO `role` VALUES ('11', '11', '11', '2018-01-02 20:54:11', '\0');
INSERT INTO `role` VALUES ('12', '12', '122', '2018-01-03 10:18:56', '\0');
INSERT INTO `role` VALUES ('13', '12', '12', '2018-01-03 10:18:56', '\0');
INSERT INTO `role` VALUES ('14', '14', '14', '2018-01-03 15:39:26', '\0');

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
INSERT INTO `role_permission` VALUES ('1', '1', '1', '2018-01-08 20:55:41', '');
INSERT INTO `role_permission` VALUES ('2', '2', '2', '2018-01-08 20:38:05', '\0');
INSERT INTO `role_permission` VALUES ('3', '2', '4', '2018-01-08 20:38:05', '\0');
INSERT INTO `role_permission` VALUES ('4', '2', '5', '2018-01-08 20:44:07', '');
INSERT INTO `role_permission` VALUES ('5', '2', '3', '2018-01-08 20:44:07', '');
INSERT INTO `role_permission` VALUES ('6', '2', '6', '2018-01-08 20:44:07', '');
INSERT INTO `role_permission` VALUES ('7', '2', '7', '2018-01-08 20:44:07', '');
INSERT INTO `role_permission` VALUES ('8', '1', '2', '2018-01-08 20:55:41', '\0');
INSERT INTO `role_permission` VALUES ('9', '1', '5', '2018-01-08 20:55:41', '\0');
INSERT INTO `role_permission` VALUES ('10', '1', '4', '2018-01-08 20:55:41', '\0');
INSERT INTO `role_permission` VALUES ('11', '1', '3', '2018-01-08 20:55:41', '\0');
INSERT INTO `role_permission` VALUES ('12', '1', '6', '2018-01-08 20:55:41', '\0');
INSERT INTO `role_permission` VALUES ('13', '1', '7', '2018-01-08 20:55:41', '\0');
INSERT INTO `role_permission` VALUES ('14', '1', '8', '2018-01-08 20:55:41', '\0');
INSERT INTO `role_permission` VALUES ('15', '1', '19', '2018-01-09 19:31:33', '\0');
INSERT INTO `role_permission` VALUES ('16', '1', '20', '2018-01-09 19:33:58', '\0');
INSERT INTO `role_permission` VALUES ('17', '1', '21', '2018-01-09 19:36:08', '\0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `salt` varchar(64) DEFAULT NULL,
  `state` tinyint(4) unsigned DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `del_flag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin', '3fbc5eeb9bf722a3414fd95c64e1957c', 'a5574c1c57975a527270eee616b2b589', '0', '2017-12-19 11:21:54', null);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `role_id` int(11) unsigned NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1', '2018-01-09 17:52:32', '\0');
