create table t_sys_user
(
    id         int auto_increment
        primary key,
    user_name  varchar(255) null,
    password   varchar(255) null,
    real_name  varchar(255) null,
    gender     int          null,
    phone      varchar(255) null,
    email      varchar(255) null,
    addTime    datetime     null,
    addUser    varchar(255) null,
    updateTime datetime     null,
    updateUser varchar(255) null
)
    comment '用户表';

INSERT INTO cavy.t_sys_user (id, user_name, password, real_name, gender, phone, email, addTime, addUser, updateTime, updateUser) VALUES (1, 'admin', ' 123', '超级管理员', 1, null, null, null, null, null, null);

