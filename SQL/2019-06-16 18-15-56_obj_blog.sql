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
  `cover` varchar(255) DEFAULT '' COMMENT '封面图(直接使用图片路径)',
  `isOriginal` int(1) DEFAULT '0' COMMENT '搬运标志(1为搬运文章)',
  `todo` int(1) DEFAULT '0' COMMENT 'todo标志(1为待完善文章)',
  PRIMARY KEY (`id`),
  KEY `timeline` (`timeline`) USING BTREE,
  KEY `zuizuo` (`title`,`author`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
