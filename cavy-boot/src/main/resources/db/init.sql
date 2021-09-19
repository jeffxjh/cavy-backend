-- cavy.t_bus_answer definition

CREATE TABLE `t_bus_answer` (
                                `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `question_id` int DEFAULT NULL COMMENT '问题id',
                                `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '内容',
                                `add_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                `add_time` datetime DEFAULT NULL,
                                `update_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                `update_time` datetime DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='回答表';


-- cavy.t_bus_question definition

CREATE TABLE `t_bus_question` (
                                  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                                  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
                                  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '内容',
                                  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `add_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                  `update_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='问题表';


-- cavy.t_sys_config definition

CREATE TABLE `t_sys_config` (
                                `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
                                `code_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号名称',
                                `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
                                `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `add_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                `update_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';


-- cavy.t_sys_file definition

CREATE TABLE `t_sys_file` (
                              `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                              `file_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件编号',
                              `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件名称',
                              `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件路径',
                              `web_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '前端地址',
                              `add_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                              `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `update_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人',
                              `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件表';


-- cavy.t_sys_menu definition

CREATE TABLE `t_sys_menu` (
                              `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                              `menu_id` int DEFAULT NULL COMMENT '菜单id',
                              `menu_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单编号',
                              `menu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
                              `parent_id` int DEFAULT '0' COMMENT '父id',
                              `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
                              `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
                              `hidden` int DEFAULT NULL COMMENT '是否隐藏',
                              `weight` int DEFAULT NULL COMMENT '权重',
                              `sort` int DEFAULT NULL COMMENT '排序',
                              `is_default` int DEFAULT NULL COMMENT '是否默认',
                              `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `add_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                              `update_time` datetime DEFAULT NULL COMMENT '修改人',
                              `update_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改时间',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单表';


-- cavy.t_sys_role definition

CREATE TABLE `t_sys_role` (
                              `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                              `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色名称',
                              `menu_id` int DEFAULT NULL COMMENT '菜单id',
                              `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `add_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                              `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                              `update_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';


-- cavy.t_sys_user definition

CREATE TABLE `t_sys_user` (
                              `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                              `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
                              `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
                              `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '真实姓名',
                              `gender` int DEFAULT NULL COMMENT '性别',
                              `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
                              `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮件',
                              `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `add_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                              `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                              `update_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';


-- cavy.t_sys_user_menu definition

CREATE TABLE `t_sys_user_menu` (
                                   `id` int DEFAULT NULL COMMENT 'id',
                                   `user_id` int DEFAULT NULL COMMENT '用户id',
                                   `menu_id` int DEFAULT NULL COMMENT '菜单id',
                                   `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   `add_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                   `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                   `update_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户菜单表';


-- cavy.t_sys_user_role definition

CREATE TABLE `t_sys_user_role` (
                                   `id` int NOT NULL COMMENT 'id',
                                   `user_id` int DEFAULT NULL COMMENT '用户id',
                                   `role_id` int DEFAULT NULL COMMENT '角色id',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色表';



INSERT INTO t_bus_answer (question_id,content,add_user,add_time,update_user,update_time) VALUES (1,'哈哈1','admin','2021-01-01 00:00:00.0',NULL,NULL);
INSERT INTO t_bus_answer (question_id,content,add_user,add_time,update_user,update_time) VALUES (1,'哈哈2','admin','2021-01-01 00:00:00.0',NULL,NULL);
INSERT INTO t_bus_answer (question_id,content,add_user,add_time,update_user,update_time) VALUES (1,'哈哈3','admin','2021-01-01 00:00:00.0',NULL,NULL);
INSERT INTO t_bus_answer (question_id,content,add_user,add_time,update_user,update_time) VALUES (1,'哈哈4','admin','2021-01-01 00:00:00.0',NULL,NULL);
INSERT INTO t_bus_answer (question_id,content,add_user,add_time,update_user,update_time) VALUES (1,'哈哈5','admin','2021-01-01 00:00:00.0',NULL,NULL);
INSERT INTO t_bus_answer (question_id,content,add_user,add_time,update_user,update_time) VALUES (1,'哈哈6','admin','2021-01-01 00:00:00.0',NULL,NULL);
;
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('test','test',NULL,NULL,NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('test','test',NULL,NULL,NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('test','test',NULL,NULL,NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('test','test',NULL,NULL,NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('test','test',NULL,NULL,NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('',NULL,NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('',NULL,NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('123','<p>123</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('11','<p>11</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('123','<p>123</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('123','<p>123</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('3','<p>3</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('2','<p>2</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('1','<p>1</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('1','<p>1</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('2','<p>2</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('4','<p>4</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('2','<p>2</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('2','<p>2</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('22','<p>22</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('123','<p>123</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('2','<p>2</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('123','<p>123</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('44','<p>44</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('1','<p>1</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('1','<p>1</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('1','<p>1</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('1','<p>1</p>',NULL,NULL,NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('1','<p>1</p>',NULL,'admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('1','<p>1</p>','2021-07-31 14:53:48.0','admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('123','<p>123</p>','2021-07-31 15:08:21.0','admin',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('3','<p>3</p>','2021-08-01 09:49:45.0','123',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('1','<p>1</p>','2021-08-06 15:15:10.0','123',NULL,NULL);
INSERT INTO t_bus_question (title,content,add_time,add_user,update_time,update_user) VALUES ('.','<p>.0</p>','2021-08-06 15:24:36.0','123',NULL,NULL);
;
INSERT INTO t_sys_config (code,code_value,name,add_time,add_user,update_time,update_user) VALUES ('init-database','cavy','初始化数据库名称',NULL,NULL,NULL,NULL);
;
INSERT INTO t_sys_file (file_code,file_name,file_path,web_url,add_user,add_time,update_user,update_time) VALUES ('022a0c0c18505a1290081bfc06cf45af152261ab972b27a7f46d79b828169480','15358071_large.png','2021/31/31/15358071_large.png','http://193.149.161.195:9000/2021/31/31/15358071_large.png',NULL,NULL,NULL,NULL);
;
INSERT INTO t_sys_menu (menu_id,menu_code,menu_name,parent_id,icon,url,hidden,weight,sort,is_default,add_time,add_user,update_time,update_user) VALUES (1,'main','后台中心',0,'el-icon-house','main',1,1,1,1,NULL,NULL,NULL,NULL);
INSERT INTO t_sys_menu (menu_id,menu_code,menu_name,parent_id,icon,url,hidden,weight,sort,is_default,add_time,add_user,update_time,update_user) VALUES (2,'order','订单列表',0,'el-icon-shopping-cart-full','order',1,3,3,1,NULL,NULL,NULL,NULL);
INSERT INTO t_sys_menu (menu_id,menu_code,menu_name,parent_id,icon,url,hidden,weight,sort,is_default,add_time,add_user,update_time,update_user) VALUES (4,'test1','测试1',0,'el-icon-message','3',1,4,4,1,NULL,NULL,NULL,NULL);
INSERT INTO t_sys_menu (menu_id,menu_code,menu_name,parent_id,icon,url,hidden,weight,sort,is_default,add_time,add_user,update_time,update_user) VALUES (8,'test-1','测试1-1',3,'el-icon-menu','4',1,1,1,1,NULL,NULL,NULL,NULL);
INSERT INTO t_sys_menu (menu_id,menu_code,menu_name,parent_id,icon,url,hidden,weight,sort,is_default,add_time,add_user,update_time,update_user) VALUES (16,'user','用户管理',0,'el-icon-user-solid','user',1,2,2,1,NULL,NULL,NULL,NULL);
INSERT INTO t_sys_menu (menu_id,menu_code,menu_name,parent_id,icon,url,hidden,weight,sort,is_default,add_time,add_user,update_time,update_user) VALUES (32,'test2','消息管理',0,'el-icon-message','message',1,4,4,1,NULL,NULL,NULL,NULL);
INSERT INTO t_sys_menu (menu_id,menu_code,menu_name,parent_id,icon,url,hidden,weight,sort,is_default,add_time,add_user,update_time,update_user) VALUES (64,'test-3','消息推送',6,'el-icon-menu','message/websocket',1,1,1,1,NULL,NULL,NULL,NULL);
INSERT INTO t_sys_menu (menu_id,menu_code,menu_name,parent_id,icon,url,hidden,weight,sort,is_default,add_time,add_user,update_time,update_user) VALUES (128,'system','系统管理',0,'el-icon-setting','system',1,9,1,1,NULL,NULL,NULL,NULL);
INSERT INTO t_sys_menu (menu_id,menu_code,menu_name,parent_id,icon,url,hidden,weight,sort,is_default,add_time,add_user,update_time,update_user) VALUES (256,'file','文件管理',8,'el-icon-file','system/file',1,9,1,1,NULL,NULL,NULL,NULL);
INSERT INTO t_sys_menu (menu_id,menu_code,menu_name,parent_id,icon,url,hidden,weight,sort,is_default,add_time,add_user,update_time,update_user) VALUES (512,'questionanswer','问答管理',0,'el-icon-question','questionanswer',1,10,1,1,NULL,NULL,NULL,NULL);
;
INSERT INTO t_sys_role (role_name,menu_id,add_time,add_user,update_time,update_user) VALUES ('测试用户',1,NULL,NULL,NULL,NULL);
;
INSERT INTO t_sys_user (user_name,password,real_name,gender,phone,email,add_time,add_user,update_time,update_user) VALUES ('admin',' 123','超级管理员',1,'110','admin@qq.com','2021-07-05 14:55:38.0','admin',NULL,NULL);
INSERT INTO t_sys_user (user_name,password,real_name,gender,phone,email,add_time,add_user,update_time,update_user) VALUES ('guest',' guest','游客',1,'110','admin@qq.com','2021-07-05 14:55:38.0','admin',NULL,NULL);
INSERT INTO t_sys_user (user_name,password,real_name,gender,phone,email,add_time,add_user,update_time,update_user) VALUES ('123',' guest','test',1,'110','admin@qq.com','2021-07-05 14:55:38.0','admin',NULL,NULL);
;

INSERT INTO t_sys_user_role (id,user_id,role_id) VALUES (1,3,1);
;
