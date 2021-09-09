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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色表';-- cavy.t_bus_answer definition

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