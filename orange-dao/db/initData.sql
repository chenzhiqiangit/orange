CREATE TABLE `sys_user` (
`user_id`  bigint NULL AUTO_INCREMENT ,
`user_name`  varchar(32) NULL ,
`password`  varchar(64) NULL ,
`create_time`  datetime NULL ,
`update_time`  datetime NULL ,
`create_by`  varchar(64) NULL ,
`update_by`  varchar(64) NULL ,
`create_method`  varchar(8) NULL ,
`update_method`  varchar(8) NULL ,
`delete_flag`  int(1) NULL ,
PRIMARY KEY (`user_id`)
);
CREATE TABLE `sys_user_role` (
`id` bigint NULL AUTO_INCREMENT,
`user_id`  bigint NULL ,
`role_id`  bigint NULL ,
PRIMARY KEY (`id`)
);
CREATE TABLE `sys_role` (
`role_id`  bigint NULL AUTO_INCREMENT,
`role_name` varchar(64)  NULL ,
`create_time`  datetime NULL ,
`create_by`  varchar(64) NULL ,
PRIMARY KEY (`role_id`)
);
CREATE TABLE `sys_permission` (
`permission_id`  bigint NULL AUTO_INCREMENT,
`permission_name`  varchar(128) NULL ,
`create_time`  datetime NULL ,
`create_by`  varchar(64) NULL ,
PRIMARY KEY (`permission_id`)
);

CREATE TABLE `sys_role_permission` (
`id`  bigint NULL AUTO_INCREMENT,
`permission_id`  bigint NULL ,
`role_id`  bigint NULL ,
PRIMARY KEY (`id`)
);

CREATE TABLE `sys_user_permission` (
`id`  bigint NULL AUTO_INCREMENT,
`user_id`  bigint NULL ,
`permission_id`  bigint NULL ,
PRIMARY KEY (`id`)
);




