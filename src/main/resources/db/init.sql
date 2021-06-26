create table t_sys_menu
(
    id         int primary key,
    menu_code  varchar(255) null,
    menu_name  varchar(255) not null,
    parent_id  int default 0 null,
    icon       varchar(255) null,
    url        varchar(255) null,
    hidden     int null,
    weight     int null,
    sort       int null,
    is_default int null,
    addTime    datetime null,
    addUser    varchar(255) null,
    updateTime datetime null,
    updateUser varchar(255) null
) comment '菜单表';

INSERT INTO t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime,
                        addUser, updateTime, updateUser)
VALUES (1, 'main', '后台中心', 0, null, null, 1, null, 1, 1, null, null, null, null);
INSERT INTO t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime,
                        addUser, updateTime, updateUser)
VALUES (2, 'order', '订单列表', 0, null, null, 1, null, 3, 1, null, null, null, null);
INSERT INTO t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime,
                        addUser, updateTime, updateUser)
VALUES (3, 'test', '测试', 0, null, null, 1, null, 4, 1, null, null, null, null);
INSERT INTO t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime,
                        addUser, updateTime, updateUser)
VALUES (4, 'test-1', '测试-1', 3, null, null, 1, null, 1, 1, null, null, null, null);
INSERT INTO t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime,
                        addUser, updateTime, updateUser)
VALUES (5, 'user', '用户管理', 0, null, null, 1, null, 2, 1, null, null, null, null);


create table t_sys_user
(
    id         int primary key,
    user_name  varchar(255) null,
    password   varchar(255) null,
    real_name  varchar(255) null,
    gender     int null,
    phone      varchar(255) null,
    email      varchar(255) null,
    addTime    datetime null,
    addUser    varchar(255) null,
    updateTime datetime null,
    updateUser varchar(255) null
) comment '用户表';

INSERT INTO t_sys_user (id, user_name, password, real_name, gender, phone, email, addTime, addUser, updateTime,
                        updateUser)
VALUES (1, 'admin', ' 123', '超级管理员', 1, null, null, null, null, null, null);


create table t_sys_user_menu
(
    id         int,
    user_id    int null,
    menu_id    int null,
    addTime    datetime null,
    addUser    varchar(255) null,
    updateTime datetime null,
    updateUser varchar(255) null
) comment '用户菜单表';




