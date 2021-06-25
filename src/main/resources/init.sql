create table t_sys_menu
(
    id int auto_increment
        primary key,
    menu_name varchar(255) not null,
    parent_id int default 0 null,
    sort int null,
    is_default int null,
    addTime datetime null,
    addUser varchar(255) null,
    updateTime datetime null,
    updateUser varchar(255) null
)
    comment '菜单表';

create table t_sys_user
(
    id int auto_increment
        primary key,
    user_name varchar(255) null,
    password varchar(255) null,
    real_name varchar(255) null,
    gender int null,
    phone varchar(255) null,
    email varchar(255) null,
    addTime datetime null,
    addUser varchar(255) null,
    updateTime datetime null,
    updateUser varchar(255) null
)
    comment '用户表';

create table t_sys_user_menu
(
    id int null,
    user_id int null,
    menu_id int null,
    addTime datetime null,
    addUser varchar(255) null,
    updateTime datetime null,
    updateUser varchar(255) null
)
    comment '用户菜单表';

