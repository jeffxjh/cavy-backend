CREATE TABLE t_bus_answer
(
    id          int NOT NULL AUTO_INCREMENT COMMENT 'id',
    question_id int      DEFAULT NULL COMMENT '问题id',
    content     text COMMENT '内容',
    add_user    varchar(255),
    add_time    datetime DEFAULT NULL,
    update_user varchar(255),
    update_time datetime DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE t_bus_question
(
    id          int NOT NULL AUTO_INCREMENT COMMENT 'id',
    title       varchar(255) DEFAULT NULL COMMENT '标题',
    content     text COMMENT '内容',
    add_time    datetime     DEFAULT NULL COMMENT '创建时间',
    add_user    varchar(255) COMMENT '创建人',
    update_time datetime     DEFAULT NULL COMMENT '修改时间',
    update_user varchar(255) COMMENT '修改人',
    PRIMARY KEY (id)
);



CREATE TABLE t_sys_config
(
    id          int          NOT NULL AUTO_INCREMENT COMMENT 'id',
    code        varchar(255) NOT NULL COMMENT '编号',
    code_value  varchar(255) NOT NULL COMMENT '编号名称',
    name        varchar(255) NOT NULL COMMENT '名称',
    add_time    datetime DEFAULT NULL COMMENT '创建时间',
    add_user    varchar(255) COMMENT '创建人',
    update_time datetime DEFAULT NULL COMMENT '修改时间',
    update_user varchar(255) COMMENT '修改人',
    PRIMARY KEY (id)
);



CREATE TABLE t_sys_file
(
    id          int NOT NULL AUTO_INCREMENT COMMENT 'id',
    file_code   varchar(255) DEFAULT NULL COMMENT '文件编号',
    file_name   varchar(255) DEFAULT NULL COMMENT '文件名称',
    file_path   varchar(255) DEFAULT NULL COMMENT '文件路径',
    web_url     varchar(255) DEFAULT NULL COMMENT '前端地址',
    add_user    varchar(255) COMMENT '创建人',
    add_time    datetime     DEFAULT NULL COMMENT '创建时间',
    update_user varchar(255) COMMENT '修改人',
    update_time datetime     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);



CREATE TABLE t_sys_menu
(
    id          int          NOT NULL AUTO_INCREMENT COMMENT 'id',
    menu_id     int          DEFAULT NULL COMMENT '菜单id',
    menu_code   varchar(255) COMMENT '菜单编号',
    menu_name   varchar(255) NOT NULL COMMENT '菜单名称',
    parent_id   int          DEFAULT '0' COMMENT '父id',
    menu_type   int          DEFAULT NULL COMMENT '菜单类型 1：目录；2：菜单；3：按钮',
    icon        varchar(255) DEFAULT NULL COMMENT '图标',
    url         varchar(255) DEFAULT NULL COMMENT '地址',
    hidden      int          DEFAULT NULL COMMENT '是否隐藏',
    weight      int          DEFAULT NULL COMMENT '权重',
    sort        int          DEFAULT NULL COMMENT '排序',
    is_default  int          DEFAULT NULL COMMENT '是否默认',
    add_time    datetime     DEFAULT NULL COMMENT '创建时间',
    add_user    varchar(255) COMMENT '创建人',
    update_time datetime     DEFAULT NULL COMMENT '修改人',
    update_user varchar(255) COMMENT '修改时间',
    status int COMMENT '菜单状态 1启用0禁用',
    PRIMARY KEY (id)
);



CREATE TABLE t_sys_role
(
    id          int NOT NULL AUTO_INCREMENT COMMENT 'id',
    role_name   varchar(255) DEFAULT NULL COMMENT '角色名称',
    add_time    datetime     DEFAULT NULL COMMENT '创建时间',
    add_user    varchar(255) COMMENT '创建人',
    update_time datetime     DEFAULT NULL COMMENT '修改时间',
    update_user varchar(255) COMMENT '修改人',
    PRIMARY KEY (id)
);




CREATE TABLE `t_sys_user` (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
                              `user_name` varchar(255) DEFAULT NULL COMMENT '账号',
                              `password` varchar(255) DEFAULT NULL COMMENT '密码',
                              `real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
                              `gender` int(11) DEFAULT NULL COMMENT '性别',
                              `phone` varchar(255) DEFAULT NULL COMMENT '电话',
                              `email` varchar(255) DEFAULT NULL COMMENT '邮件',
                              `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态 0已新建;1已使用;2已禁用;3已冻结',
                              `default_user` char(1) NOT NULL DEFAULT '0' COMMENT '默认用户 0否;1是',
                              `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `add_user` varchar(255) DEFAULT NULL COMMENT '创建人',
                              `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                              `update_user` varchar(255) DEFAULT NULL COMMENT '修改人',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;



CREATE TABLE t_sys_user_menu
(
    id      int NOT NULL AUTO_INCREMENT COMMENT 'id',
    user_id     int      DEFAULT NULL COMMENT '用户id',
    menu_id     int      DEFAULT NULL COMMENT '菜单id',
    add_time    datetime DEFAULT NULL COMMENT '创建时间',
    add_user    varchar(255) COMMENT '创建人',
    update_time datetime DEFAULT NULL COMMENT '修改时间',
    update_user varchar(255) COMMENT '修改人',
    PRIMARY KEY (id)
);


CREATE TABLE t_sys_user_role
(
    id      int NOT NULL AUTO_INCREMENT COMMENT 'id',
    user_id int DEFAULT NULL COMMENT '用户id',
    role_id int DEFAULT NULL COMMENT '角色id',
    PRIMARY KEY (id)

);

CREATE TABLE `t_sys_message` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                 `receiver` varchar(100) NOT NULL COMMENT '接收人账号',
                                 `title` varchar(255) DEFAULT NULL COMMENT '标题',
                                 `content` varchar(500) NOT NULL COMMENT '编号名称',
                                 `msg_level` char(1) NOT NULL COMMENT '消息等级 1：提醒；2：一般；3：重要；',
                                 `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                                 `add_user` varchar(255) DEFAULT NULL COMMENT '创建人',
                                 `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                 `update_user` varchar(255) DEFAULT NULL COMMENT '修改人',
                                 `status` char(1) DEFAULT '0' COMMENT '状态 1已读；0未读',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;



CREATE TABLE `t_sys_dict_item` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `dic_id` varchar(100) NOT NULL COMMENT '主表id',
                                   `item` varchar(100) NOT NULL COMMENT 'key',
                                   `label` varchar(100) NOT NULL COMMENT 'value',
                                   `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   `add_user` varchar(255) DEFAULT NULL COMMENT '创建人',
                                   `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                   `update_user` varchar(255) DEFAULT NULL COMMENT '修改人',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `t_sys_dict_item_UN` (`dic_id`,`item`,`label`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据字典项';




CREATE TABLE `t_sys_dict` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `code` varchar(100) DEFAULT NULL COMMENT '编码',
                              `name` varchar(100) DEFAULT NULL COMMENT '名称',
                              `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `add_user` varchar(255) DEFAULT NULL COMMENT '创建人',
                              `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                              `update_user` varchar(255) DEFAULT NULL COMMENT '修改人',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `t_sys_dict_UN` (`code`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据字典表';

create table t_sys_menu_resource
(
    id          bigint auto_increment comment '主键'
        primary key,
    menu_id     bigint       not null comment '菜单表主键',
    resource_id bigint       not null comment '资源表主键',
    add_time    datetime     null comment '创建时间',
    add_user    varchar(255) null comment '创建人',
    update_time datetime     null comment '修改时间',
    update_user varchar(255) null comment '修改人',
    constraint t_sys_menu_resource_UN
        unique (menu_id, resource_id)
)
    comment '菜单资源关联表';

create table t_sys_resource
(
    id            bigint auto_increment comment '主键'
        primary key,
    resource_code varchar(255) not null comment '资源编码',
    resource_name varchar(255) not null comment '资源名称',
    resource_path varchar(255) not null comment '资源路径',
    add_time      datetime     null comment '创建时间',
    add_user      varchar(255) null comment '创建人',
    update_time   datetime     null comment '修改时间',
    update_user   varchar(255) null comment '修改人',
    constraint t_sys_resource_UN
        unique (resource_code)
)
    comment '资源表';

create table t_sys_role_menu
(
    id          bigint auto_increment comment '主键'
        primary key,
    role_id     bigint       not null comment '角色表主键',
    menu_id     bigint       not null comment '菜单表主键',
    add_time    datetime     null comment '创建时间',
    add_user    varchar(255) null comment '创建人',
    update_time datetime     null comment '修改时间',
    update_user varchar(255) null comment '修改人',
    constraint t_sys_role_menu_UN
        unique (role_id, menu_id)
)
    comment '角色菜单关联表';

create table t_bus_favour_relative
(
    id             int auto_increment comment '主键'
        primary key,
    real_name      VARCHAR(255) not null comment '亲友名称',
    nick_name      varchar(255) null comment '亲友昵称',
    user_id        int          not null comment '关联当前用户表主键(是谁的亲友)',
    relate_user_id int          null comment '亲友关联的用户主键(该亲友在系统中的用户主键)',
    add_time    datetime     null comment '创建时间',
    add_user    varchar(255) null comment '创建人',
    update_time datetime     null comment '修改时间',
    update_user varchar(255) null comment '修改人'
)
    comment '人情管理-亲友表';

create unique index t_bus_favour_relative_user_id_real_name_relate_user_id_uindex
    on t_bus_favour_relative (user_id, real_name, relate_user_id);

-- drop table t_bus_favour_record;

create table t_bus_favour_record
(
    id              int auto_increment comment '主键'
        primary key,
    current_user_id int            null comment '当前用户主键(主体用户)',
    relate_user_id  int            null comment '关联用户主键',
    trade_type      char           not null comment '往来交易类型 1支出 0收入',
    buss_type char(3) not null comment '交易事项 数据字典(生日,结婚,乔迁)',
    amt             decimal(18, 2) null comment '金额',
    add_time    datetime     null comment '创建时间',
    add_user    varchar(255) null comment '创建人',
    update_time datetime     null comment '修改时间',
    update_user varchar(255) null comment '修改人'
)
    comment '人情管理-人情记录表';

create index t_bus_favour_record_index
    on t_bus_favour_record (relate_user_id, current_user_id, trade_type);



