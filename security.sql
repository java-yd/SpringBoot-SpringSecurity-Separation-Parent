/*
SQLyog Ultimate v8.32 
MySQL - 5.7.20-log : Database - bootdruid
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bootdruid` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `bootdruid`;

/*Table structure for table `tbmanager` */

DROP TABLE IF EXISTS `tbmanager`;

CREATE TABLE `tbmanager` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `username` varchar(20) DEFAULT NULL COMMENT '账号',
  `password` char(32) DEFAULT NULL COMMENT '密码',
  `identifying` char(32) DEFAULT NULL COMMENT '标识，用过禁止重复登录',
  `gmt_creat` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tbmanager` */

insert  into `tbmanager`(`id`,`username`,`password`,`identifying`,`gmt_creat`,`gmt_modified`) values (0,'admin','202cb962ac59075b964b07152d234b70','d169e8b33f6a48778d3ace0864c46225','2018-08-03 11:55:55','2018-08-03 18:22:32'),(1,'zhangsan','202cb962ac59075b964b07152d234b70',NULL,'2018-08-03 11:55:55','2018-08-03 16:17:01'),(2,'lisi','202cb962ac59075b964b07152d234b70',NULL,'2018-08-03 11:55:55','2018-08-03 11:55:55');

/*Table structure for table `tbmanager_role` */

DROP TABLE IF EXISTS `tbmanager_role`;

CREATE TABLE `tbmanager_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `manager_id` int(11) DEFAULT NULL COMMENT '管理员id',
  `role_id` int(11) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tbmanager_role` */

insert  into `tbmanager_role`(`id`,`manager_id`,`role_id`) values (1,1,1),(2,1,2),(3,1,4);

/*Table structure for table `tbresource` */

DROP TABLE IF EXISTS `tbresource`;

CREATE TABLE `tbresource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父id，本表',
  `url` varchar(20) DEFAULT NULL COMMENT '资源url',
  `mark` varchar(50) DEFAULT NULL COMMENT '备注',
  `gmt_creat` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tbresource` */

insert  into `tbresource`(`id`,`parent_id`,`url`,`mark`,`gmt_creat`,`gmt_modified`) values (1,0,NULL,'用户操作',NULL,NULL),(2,1,'/user/addUser','添加用户',NULL,NULL),(3,1,'/user/editUser','修改用户',NULL,NULL),(4,1,'/user/delUser','删除用户',NULL,NULL),(5,0,NULL,'商品操作',NULL,NULL),(6,5,'/goods/addGoods','添加商品',NULL,NULL),(7,5,'/goods/editGoods','修改商品',NULL,NULL),(8,5,'/goods/delGoods','删除商品',NULL,NULL);

/*Table structure for table `tbrole` */

DROP TABLE IF EXISTS `tbrole`;

CREATE TABLE `tbrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rname` varchar(20) DEFAULT NULL,
  `gmt_creat` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tbrole` */

insert  into `tbrole`(`id`,`rname`,`gmt_creat`,`gmt_modified`) values (1,'用户管理',NULL,NULL),(2,'商品管理',NULL,NULL),(3,'订单管理',NULL,NULL),(4,'物流管理',NULL,NULL);

/*Table structure for table `tbrole_resource` */

DROP TABLE IF EXISTS `tbrole_resource`;

CREATE TABLE `tbrole_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '权限id',
  `resource_id` int(11) DEFAULT NULL COMMENT '资源id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tbrole_resource` */

insert  into `tbrole_resource`(`id`,`role_id`,`resource_id`) values (1,1,2),(2,1,3),(3,2,6),(4,2,7),(5,4,7);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
