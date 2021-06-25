create table t_sys_user_menu
(
    id         int          null,
    user_id    int          null,
    menu_id    int          null,
    addTime    datetime     null,
    addUser    varchar(255) null,
    updateTime datetime     null,
    updateUser varchar(255) null
)
    comment '用户菜单表';

