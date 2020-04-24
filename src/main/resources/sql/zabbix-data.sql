/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50728
Source Host           : localhost:3306
Source Database       : zabbix_data

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2020-04-24 11:17:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for excel
-- ----------------------------
DROP TABLE IF EXISTS `excel`;
CREATE TABLE `excel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of excel
-- ----------------------------

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('68');

-- ----------------------------
-- Table structure for item_name_mapping
-- ----------------------------
DROP TABLE IF EXISTS `item_name_mapping`;
CREATE TABLE `item_name_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(255) DEFAULT NULL,
  `mapping_name` varchar(255) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item_name_mapping
-- ----------------------------
INSERT INTO `item_name_mapping` VALUES ('1', 'Interface eth0: Bits received', '网卡eth0: 接收比特速率', '0');
INSERT INTO `item_name_mapping` VALUES ('2', 'Interface eth0: Bits sent', '网卡eth0: 发送比特速率', '0');
INSERT INTO `item_name_mapping` VALUES ('3', 'Number of CPUs', 'CPU的数量', '0');
INSERT INTO `item_name_mapping` VALUES ('5', 'CPU utilization', 'CPU 使用率', '0');
INSERT INTO `item_name_mapping` VALUES ('6', 'CPU system time', 'CPU内核态使用时间比', '0');
INSERT INTO `item_name_mapping` VALUES ('7', 'CPU user time', 'CPU用户态使用时间比', '0');
INSERT INTO `item_name_mapping` VALUES ('8', 'vda1: Disk average queue size (avgqu-sz)', 'vda1: 磁盘平均队列大小(avgqui -sz)', '0');
INSERT INTO `item_name_mapping` VALUES ('9', 'vda: Disk average queue size (avgqu-sz)', 'vda: 磁盘平均队列大小(avgqui -sz)', '0');
INSERT INTO `item_name_mapping` VALUES ('10', 'vdb1: Disk average queue size (avgqu-sz)', 'vdb1: 磁盘平均队列大小(avgqui -sz)', '0');
INSERT INTO `item_name_mapping` VALUES ('11', 'vdb: Disk average queue size (avgqu-sz)', 'vdb: 磁盘平均队列大小(avgqui -sz)', '0');
INSERT INTO `item_name_mapping` VALUES ('12', 'vda1: Disk read request avg waiting time (r_await)', 'vda1: 磁盘读请求avg等待时间(r_await)', '0');
INSERT INTO `item_name_mapping` VALUES ('13', 'vda: Disk read request avg waiting time (r_await)', 'vda: 磁盘读请求avg等待时间(r_await)', '0');
INSERT INTO `item_name_mapping` VALUES ('14', 'vdb1: Disk read request avg waiting time (r_await)', 'vdb1: 磁盘读请求avg等待时间(r_await)', '0');
INSERT INTO `item_name_mapping` VALUES ('15', 'vdb: Disk read request avg waiting time (r_await)', 'vdb: 磁盘读请求avg等待时间(r_await)', '0');
INSERT INTO `item_name_mapping` VALUES ('16', 'vda1: Disk read rate', 'vda1: 磁盘读速率', '0');
INSERT INTO `item_name_mapping` VALUES ('17', 'vda: Disk read rate', 'vda: 磁盘读速率', '0');
INSERT INTO `item_name_mapping` VALUES ('18', 'vdb1: Disk read rate', 'vdb1: 磁盘读速率', '0');
INSERT INTO `item_name_mapping` VALUES ('19', 'vdb: Disk read rate', 'vdb2: 磁盘读速率', '0');
INSERT INTO `item_name_mapping` VALUES ('20', 'vda1: Disk utilization', 'vda1: 磁盘使用率', '0');
INSERT INTO `item_name_mapping` VALUES ('21', 'vda1: Disk utilization', 'vda: 磁盘使用率', '0');
INSERT INTO `item_name_mapping` VALUES ('22', 'vdb1: Disk utilization', 'vdb1: 磁盘使用率', '0');
INSERT INTO `item_name_mapping` VALUES ('23', 'vdb: Disk utilization', 'vdb: 磁盘使用率', '0');
INSERT INTO `item_name_mapping` VALUES ('24', 'vda1: Disk write request avg waiting time (w_await)', 'vda1: 磁盘写请求avg等待时间(w_await)', '0');
INSERT INTO `item_name_mapping` VALUES ('25', 'vda: Disk write request avg waiting time (w_await)', 'vda: 磁盘写请求avg等待时间(w_await)', '0');
INSERT INTO `item_name_mapping` VALUES ('26', 'vdb1: Disk write request avg waiting time (w_await)', 'vdb1: 磁盘写请求avg等待时间(w_await)', '0');
INSERT INTO `item_name_mapping` VALUES ('27', 'vdb: Disk write request avg waiting time (w_await)', 'vdb: 磁盘写请求avg等待时间(w_await)', '0');
INSERT INTO `item_name_mapping` VALUES ('28', 'vda1: Disk write rate', 'vda1: 磁盘写速率', '0');
INSERT INTO `item_name_mapping` VALUES ('29', 'vda: Disk write rate', 'vda: 磁盘写速率', '0');
INSERT INTO `item_name_mapping` VALUES ('30', 'vdb1: Disk write rate', 'vdb1 :磁盘写速率', '0');
INSERT INTO `item_name_mapping` VALUES ('31', 'vdb: Disk write rate', 'vdb: 磁盘写速率', '0');
INSERT INTO `item_name_mapping` VALUES ('32', 'vda1: Disk write time (rate)', 'vda1: 磁盘写时间(速率)', '0');
INSERT INTO `item_name_mapping` VALUES ('33', 'vda: Disk write time (rate)', 'vda: 磁盘写时间(速率)', '0');
INSERT INTO `item_name_mapping` VALUES ('34', 'vdb1: Disk write time (rate)', 'vdb1: 磁盘写时间(速率)', '0');
INSERT INTO `item_name_mapping` VALUES ('35', 'vdb: Disk write time (rate)', 'vdb: 磁盘写时间(速率)', '0');
INSERT INTO `item_name_mapping` VALUES ('36', '/: Free inodes in %', '/: 空闲索引节点比', '0');
INSERT INTO `item_name_mapping` VALUES ('37', '/volume: Free inodes in %', '/volume: 空闲索引节点比', '0');
INSERT INTO `item_name_mapping` VALUES ('38', '/: Space utilization', '/: 空间使用率', '0');
INSERT INTO `item_name_mapping` VALUES ('39', '/: Total space', '/: 空间总大小', '0');
INSERT INTO `item_name_mapping` VALUES ('40', '/: Used space', '/: 已使用空间', '0');
INSERT INTO `item_name_mapping` VALUES ('41', '/volume: Space utilization', '/volume: 空间使用率', '0');
INSERT INTO `item_name_mapping` VALUES ('42', '/volume: Total space', '/volume: 空间总大小', '0');
INSERT INTO `item_name_mapping` VALUES ('43', '/volume: Used space', '/volume: 已使用空间', '0');
INSERT INTO `item_name_mapping` VALUES ('44', 'Available memory', '内存剩余大小', '0');
INSERT INTO `item_name_mapping` VALUES ('45', 'Memory utilization', '内存使用率', '0');
INSERT INTO `item_name_mapping` VALUES ('46', 'Total memory', '内存总大小', '0');
INSERT INTO `item_name_mapping` VALUES ('47', '同步服务是否存活', '同步服务是否存活', '0');
INSERT INTO `item_name_mapping` VALUES ('48', '爬虫script', '爬虫script', '0');
INSERT INTO `item_name_mapping` VALUES ('49', '爬虫主服务是否存活', '爬虫主服务是否存活', '0');
INSERT INTO `item_name_mapping` VALUES ('50', '爬虫work', '爬虫work', '0');
INSERT INTO `item_name_mapping` VALUES ('51', '/mnt: Free inodes in %', '/mnt: 空闲索引节点比', '0');
INSERT INTO `item_name_mapping` VALUES ('52', '/mnt: Space utilization', '/mnt: 空间使用率', '0');
INSERT INTO `item_name_mapping` VALUES ('53', '/mnt: Total space', '/mnt: 总空间大小', '0');
INSERT INTO `item_name_mapping` VALUES ('54', '/mnt: Used space', '/mnt: 已使用空间', '0');
INSERT INTO `item_name_mapping` VALUES ('55', 'vdc: Disk average queue size (avgqu-sz)', 'vdc: 磁盘平均队列大小(avgqui -sz)', '0');
INSERT INTO `item_name_mapping` VALUES ('56', 'vdc: Disk read request avg waiting time (r_await)', 'vdc: 磁盘读请求avg等待时间(r_await)', '0');
INSERT INTO `item_name_mapping` VALUES ('57', 'vdc: Disk read rate', 'vdc: 磁盘读速率', '0');
INSERT INTO `item_name_mapping` VALUES ('58', 'vdc: Disk read time (rate)', 'vdc: 磁盘读取时间(速率)', '0');
INSERT INTO `item_name_mapping` VALUES ('59', 'vdc: Disk utilization', 'vdc: 磁盘利用率', '0');
INSERT INTO `item_name_mapping` VALUES ('60', 'vdc: Disk write request avg waiting time (w_await)', 'vdc: 磁盘写请求avg等待时间(w_await)', '0');
INSERT INTO `item_name_mapping` VALUES ('61', 'vdc: Disk write rate', 'vdc: 磁盘写速率', '0');
INSERT INTO `item_name_mapping` VALUES ('62', 'vdc: Disk write time (rate)', 'vdc: 磁盘写时间(速率)', '0');
INSERT INTO `item_name_mapping` VALUES ('63', 'nginx status server accepts', 'nginx服务器总成功握手次数', '0');
INSERT INTO `item_name_mapping` VALUES ('64', 'nginx status connections active', 'nginx服务器并发处理连接数', '0');
INSERT INTO `item_name_mapping` VALUES ('65', 'nginx status server handled', 'nginx服务器总处理请求次数', '0');
INSERT INTO `item_name_mapping` VALUES ('66', 'nginx status server requests', 'nginx服务器总处理请求次数', '0');
INSERT INTO `item_name_mapping` VALUES ('67', 'nginx status connections waiting', 'nginx服务器等待下一次指令驻留链接数', '0');
INSERT INTO `item_name_mapping` VALUES ('68', 'nginx status connections writing', 'nginx服务器返回给客户端的Header信息数', '0');
INSERT INTO `item_name_mapping` VALUES ('69', 'python3 爬虫是否正在运行', 'python3 爬虫是否正在运行', '0');
INSERT INTO `item_name_mapping` VALUES ('70', 'nginx status connections reading', 'nginx服务器读取客户端的Header信息数', '0');
INSERT INTO `item_name_mapping` VALUES ('74', '网卡eth0总流量', '网卡eth0总流量', '0');
INSERT INTO `item_name_mapping` VALUES ('75', '网卡eth0总下载流量', '网卡eth0总下载流量', '0');
INSERT INTO `item_name_mapping` VALUES ('76', '网卡eth0总上传流量', '网卡eth0总上传流量', '0');
