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

INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (1, 'main', '后台中心', 0, 'el-icon-house', 'main', 1, 1, 1, 1, null, null, null, null);
INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (2, 'order', '订单列表', 0, 'el-icon-shopping-cart-full', 'order', 1, 3, 3, 1, null, null, null, null);
INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (3, 'test1', '测试1', 0, 'el-icon-message', '3', 1, 4, 4, 1, null, null, null, null);
INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (4, 'test-1', '测试1-1', 3, 'el-icon-menu', '4', 1, 1, 1, 1, null, null, null, null);
INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (5, 'user', '用户管理', 0, 'el-icon-user-solid', 'user', 1, 2, 2, 1, null, null, null, null);
INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (6, 'test2', '消息管理', 0, 'el-icon-message', 'message', 1, 4, 4, 1, null, null, null, null);
INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (7, 'test-3', '消息推送', 6, 'el-icon-menu', 'message/websocket', 1, 1, 1, 1, null, null, null, null);
INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (8, 'system', '系统管理', 0, 'el-icon-setting', 'system', 1, 9, 1, 1, null, null, null, null);
INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (9, 'file', '文件管理', 8, 'el-icon-file', 'system/file', 1, 9, 1, 1, null, null, null, null);
INSERT INTO cavy.t_sys_menu (id, menu_code, menu_name, parent_id, icon, url, hidden, weight, sort, is_default, addTime, addUser, updateTime, updateUser) VALUES (10, 'questionanswer', '问答管理', 0, 'el-icon-question', 'questionanswer', 1, 10, 1, 1, null, null, null, null);