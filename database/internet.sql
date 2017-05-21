# Host: 127.0.0.1  (Version 5.5.44)
# Date: 2017-04-15 14:38:36
# Generator: MySQL-Front 6.0  (Build 1.122)


#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(16) NOT NULL DEFAULT '' COMMENT '用户id',
  `password` varchar(16) NOT NULL DEFAULT '' COMMENT '用户密码',
  `ip` varchar(16) NOT NULL DEFAULT '0' COMMENT 'IP地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "user"
#

INSERT INTO `user` VALUES ('14050601','123456','0'),('14050602','123456','0'),('14050603','123456','0'),('140506219','123456','192.168.88.101'),('140506226','960815','192.168.88.111');
