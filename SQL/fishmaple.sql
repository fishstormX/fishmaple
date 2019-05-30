/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.7.23-0ubuntu0.16.04.1 : Database - fish_dev
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fish_dev` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `fish_dev`;

/*Table structure for table `anonymous` */

DROP TABLE IF EXISTS `anonymous`;

CREATE TABLE `anonymous` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `session` varchar(255) NOT NULL DEFAULT '0',
  `timeline` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uni` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Table structure for table `blog` */

DROP TABLE IF EXISTS `blog`;

CREATE TABLE `blog` (
  `content` mediumtext,
  `title` varchar(255) DEFAULT NULL,
  `id` varchar(100) NOT NULL,
  `timeline` varchar(30) DEFAULT NULL,
  `author` varchar(32) DEFAULT NULL,
  `anchors` text,
  `priority` int(1) NOT NULL DEFAULT '0' COMMENT '优先级(置顶)',
  `useDictionary` int(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否开启字典(默认开启)',
  `cover` varchar(255) DEFAULT NULL COMMENT '封面图(直接使用图片路径)',
  `isOriginal` int(1) DEFAULT '0' COMMENT '搬运标志(1为搬运文章)',
  `todo` int(1) DEFAULT '0' COMMENT 'todo标志(1为待完善文章)',
  PRIMARY KEY (`id`),
  KEY `timeline` (`timeline`) USING BTREE,
  KEY `zuizuo` (`title`,`author`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `blog_recycle` */

DROP TABLE IF EXISTS `blog_recycle`;

CREATE TABLE `blog_recycle` (
  `id` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` mediumtext NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `timeline` varchar(30) NOT NULL,
  `anchors` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `blog_tag` */

DROP TABLE IF EXISTS `blog_tag`;

CREATE TABLE `blog_tag` (
  `id` varchar(255) NOT NULL,
  `blog_id` varchar(255) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `blog_id` (`blog_id`),
  KEY `tag` (`tag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `bluser` */

DROP TABLE IF EXISTS `bluser`;

CREATE TABLE `bluser` (
  `mid` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `face` varchar(255) DEFAULT NULL,
  `fans` varchar(255) DEFAULT NULL,
  `rank` varchar(255) DEFAULT NULL,
  `handle` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Table structure for table `can` */

DROP TABLE IF EXISTS `can`;

CREATE TABLE `can` (
  `id` varchar(255) CHARACTER SET utf8 NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `uid` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `content` text CHARACTER SET utf8 NOT NULL,
  `type` int(11) DEFAULT '0' COMMENT '罐头类型：0 普通匿名罐头 1 铁皮罐头',
  `date` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `outTime` varchar(255) CHARACTER SET utf8 DEFAULT '259200' COMMENT '默认三天',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `chat` */

DROP TABLE IF EXISTS `chat`;

CREATE TABLE `chat` (
  `id` varchar(32) NOT NULL COMMENT 'uuid',
  `name` varchar(255) NOT NULL COMMENT '发言人',
  `content` varchar(600) NOT NULL,
  `timeline` bigint(20) NOT NULL COMMENT '发言时间',
  `session` varchar(255) DEFAULT NULL COMMENT 'sessionId(默认不启用）',
  `ip` varchar(15) NOT NULL COMMENT 'ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Table structure for table `cities` */

DROP TABLE IF EXISTS `cities`;

CREATE TABLE `cities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cityid` varchar(20) NOT NULL,
  `city` varchar(50) NOT NULL,
  `provinceid` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=346 DEFAULT CHARSET=utf8 COMMENT='行政区域地州市信息表';

/*Table structure for table `config` */

DROP TABLE IF EXISTS `config`;

CREATE TABLE `config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) CHARACTER SET utf8 NOT NULL,
  `value` varchar(255) CHARACTER SET utf8 NOT NULL,
  `param2` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `describe` text CHARACTER SET utf8,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Table structure for table `dictionary` */

DROP TABLE IF EXISTS `dictionary`;

CREATE TABLE `dictionary` (
  `key` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '词条',
  `value` text CHARACTER SET utf8 NOT NULL COMMENT '解释',
  `author` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `timeline` varchar(30) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `issue` */

DROP TABLE IF EXISTS `issue`;

CREATE TABLE `issue` (
  `id` varchar(32) NOT NULL,
  `timeline` varchar(20) DEFAULT NULL,
  `content` text,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `state` smallint(1) DEFAULT NULL COMMENT '状态 0open 1closed',
  `num` bigint(20) DEFAULT NULL COMMENT '记录issue号，总不能用奇怪的uuid呀',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `issue_solve` */

DROP TABLE IF EXISTS `issue_solve`;

CREATE TABLE `issue_solve` (
  `id` varchar(32) NOT NULL,
  `issue_id` varchar(32) NOT NULL,
  `timeline` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `reply_id` varchar(32) DEFAULT NULL COMMENT '回复的是issue还是回复本身（不可回复回复回复的回复）',
  PRIMARY KEY (`id`),
  KEY `issue_id` (`issue_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `login_log` */

DROP TABLE IF EXISTS `login_log`;

CREATE TABLE `login_log` (
  `timeline` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `id` varchar(255) CHARACTER SET utf8 NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `os` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `browser` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `action` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `ip` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `time` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `notes` */

DROP TABLE IF EXISTS `notes`;

CREATE TABLE `notes` (
  `id` varchar(255) NOT NULL,
  `uid` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  `timestamp` varchar(255) DEFAULT NULL,
  `param0` varchar(255) DEFAULT NULL,
  `param1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `provinces` */

DROP TABLE IF EXISTS `provinces`;

CREATE TABLE `provinces` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provinceid` varchar(20) NOT NULL,
  `province` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='省份信息表';

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `id` varchar(100) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `describe` varchar(255) DEFAULT NULL,
  `readonly` int(1) NOT NULL,
  `path` varchar(255) NOT NULL,
  `dateline` int(11) DEFAULT NULL,
  `downloadCode` varchar(255) NOT NULL,
  `suffix` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tag` */

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
  `name` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tool` */

DROP TABLE IF EXISTS `tool`;

CREATE TABLE `tool` (
  `id` varchar(32) NOT NULL COMMENT 'uuid主键',
  `name` varchar(255) NOT NULL COMMENT '工具名',
  `describe` text NOT NULL COMMENT '描述',
  `createDate` int(11) NOT NULL COMMENT '上传时间',
  `author` varchar(32) DEFAULT NULL COMMENT '上传者 没有则是root',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Table structure for table `tool_resource` */

DROP TABLE IF EXISTS `tool_resource`;

CREATE TABLE `tool_resource` (
  `id` varchar(100) NOT NULL,
  `toolId` varchar(100) NOT NULL,
  `resourceId` varchar(100) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `registertime` varchar(20) DEFAULT NULL,
  `pswd` varchar(255) DEFAULT NULL,
  `auth` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` varchar(255) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `words` */

DROP TABLE IF EXISTS `words`;

CREATE TABLE `words` (
  `Chinese` text,
  `English` text,
  `from` varchar(255) DEFAULT NULL,
  `id` int(255) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
