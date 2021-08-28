create table t_sys_config
(
    id         int          not null
        primary key,
    code       varchar(255) not null,
    code_value varchar(255) not null,
    name       varchar(255) not null,
    addTime    datetime     null,
    addUser    varchar(255) null,
    updateTime datetime     null,
    updateUser varchar(255) null
)
    comment '系统配置表';

INSERT INTO cavy.t_sys_config (id, code, code_value, name, addTime, addUser, updateTime, updateUser) VALUES (1, 'init-database', 'cavy', '初始化数据库名称', null, null, null, null);