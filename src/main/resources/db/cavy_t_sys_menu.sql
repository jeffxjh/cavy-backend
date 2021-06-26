create table t_sys_menu
(
    id         int auto_increment
        primary key,
    menu_code  varchar(255)  null,
    menu_name  varchar(255)  not null,
    parent_id  int default 0 null,
    icon       varchar(255)  null,
    url        varchar(255)  null,
    hidden     int           null,
    weight     int           null,
    sort       int           null,
    is_default int           null,
    addTime    datetime      null,
    addUser    varchar(255)  null,
    updateTime datetime      null,
    updateUser varchar(255)  null
)
    comment '菜单表';

INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (1, 'main', '后台中心', 0, null, null, 1, null, 1, 1, null, null, null, null);
INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (2, 'order', '订单列表', 0, null, null, 1, null, 3, 1, null, null, null, null);
INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (3, 'test', '测试', 0, null, null, 1, null, 4, 1, null, null, null, null);
INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (4, 'test-1', '测试-1', 3, null, null, 1, null, 1, 1, null, null, null, null);
INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (5, 'user', '用户管理', 0, null, null, 1, null, 2, 1, null, null, null, null);


