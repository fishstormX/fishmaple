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

/*Table structure for table `blog_bak` */

DROP TABLE IF EXISTS `blog_bak`;

CREATE TABLE `blog_bak` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `blog_id` varchar(100) DEFAULT NULL,
  `content` text,
  `author_id` varchar(100) DEFAULT NULL,
  `timeline` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
