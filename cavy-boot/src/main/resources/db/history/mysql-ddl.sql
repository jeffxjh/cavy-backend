create table cavy.t_bus_answer
(
    id          int auto_increment comment 'id'
        primary key,
    question_id int          null comment '问题id',
    content     text         null comment '内容',
    add_user    varchar(255) null,
    add_time    datetime     null,
    update_user varchar(255) null,
    update_time datetime     null
);

create table cavy.t_bus_favour_book
(
    id              int auto_increment comment '主键'
        primary key,
    current_user_id int            null comment '主办人用户主键',
    buss_type       char(3)        not null comment '礼薄事项 数据字典(生日,结婚,乔迁)',
    buss_name       varchar(255)   null comment '自定义的礼薄名称',
    buss_date       date           not null comment '举行日期',
    amt             decimal(18, 2) null comment '总金额',
    add_time        datetime       null comment '创建时间',
    add_user        varchar(255)   null comment '创建人',
    update_time     datetime       null comment '修改时间',
    update_user     varchar(255)   null comment '修改人',
    remark          varchar(255)   null comment '备注'
)
    comment '人情管理-礼薄表';

create index t_bus_favour_book_index
    on cavy.t_bus_favour_book (current_user_id, buss_type, buss_name, buss_date, add_time);

create table cavy.t_bus_favour_book_gift
(
    id             int auto_increment comment '主键'
        primary key,
    favour_book_id int            null comment '礼薄表主键',
    relate_user_id int            null comment '送礼用户主键',
    amt            decimal(18, 2) null comment '金额',
    add_time       datetime       null comment '创建时间',
    add_user       varchar(255)   null comment '创建人',
    update_time    datetime       null comment '修改时间',
    update_user    varchar(255)   null comment '修改人',
    remarks        varchar(255)   null comment '备注'
)
    comment '人情管理-礼薄明细表';

create index t_bus_favour_book_gift_index
    on cavy.t_bus_favour_book_gift (relate_user_id, favour_book_id, add_time);

create table cavy.t_bus_favour_record
(
    id              int auto_increment comment '主键'
        primary key,
    current_user_id int            null comment '当前用户主键(主体用户)',
    relate_user_id  int            null comment '关联用户主键',
    trade_type      char           not null comment '往来交易类型 1支出 0收入',
    buss_type       char(3)        not null comment '交易事项 数据字典(生日,结婚,乔迁)',
    amt             decimal(18, 2) null comment '金额',
    add_time        datetime       null comment '创建时间',
    add_user        varchar(255)   null comment '创建人',
    update_time     datetime       null comment '修改时间',
    update_user     varchar(255)   null comment '修改人'
)
    comment '人情管理-人情记录表';

create index t_bus_favour_record_index
    on cavy.t_bus_favour_record (relate_user_id, current_user_id, trade_type, buss_type, add_time);

create table cavy.t_bus_favour_relative
(
    id             int auto_increment comment '主键'
        primary key,
    real_name      varchar(255) not null comment '亲友名称',
    nick_name      varchar(255) null comment '亲友昵称',
    relate_type    int          not null comment '亲友关系',
    user_id        int          not null comment '关联当前用户表主键(是谁的亲友)',
    relate_user_id int          null comment '亲友关联的用户主键(该亲友在系统中的用户主键)',
    add_time       datetime     null comment '创建时间',
    add_user       varchar(255) null comment '创建人',
    update_time    datetime     null comment '修改时间',
    update_user    varchar(255) null comment '修改人',
    constraint t_bus_favour_relative_user_id_real_name_relate_user_id_uindex
        unique (user_id, real_name, relate_user_id)
)
    comment '人情管理-亲友表';

create table cavy.t_bus_question
(
    id          int auto_increment comment 'id'
        primary key,
    title       varchar(255) null comment '标题',
    content     text         null comment '内容',
    add_time    datetime     null comment '创建时间',
    add_user    varchar(255) null comment '创建人',
    update_time datetime     null comment '修改时间',
    update_user varchar(255) null comment '修改人'
);

create table cavy.t_sys_config
(
    id          int auto_increment comment 'id'
        primary key,
    code        varchar(255) not null comment '编号',
    code_value  varchar(255) not null comment '编号名称',
    name        varchar(255) not null comment '名称',
    add_time    datetime     null comment '创建时间',
    add_user    varchar(255) null comment '创建人',
    update_time datetime     null comment '修改时间',
    update_user varchar(255) null comment '修改人'
)
    comment '系统参数配置表';

create table cavy.t_sys_dict
(
    id          bigint auto_increment
        primary key,
    code        varchar(100) null comment '编码',
    name        varchar(100) null comment '名称',
    add_time    datetime     null comment '创建时间',
    add_user    varchar(255) null comment '创建人',
    update_time datetime     null comment '修改时间',
    update_user varchar(255) null comment '修改人',
    constraint t_sys_dict_UN
        unique (code, name)
)
    comment '系统数据字典表';

create table cavy.t_sys_dict_item
(
    id          bigint auto_increment
        primary key,
    dic_id      varchar(100) not null comment '主表id',
    item        varchar(100) not null comment 'key',
    label       varchar(100) not null comment 'value',
    add_time    datetime     null comment '创建时间',
    add_user    varchar(255) null comment '创建人',
    update_time datetime     null comment '修改时间',
    update_user varchar(255) null comment '修改人',
    constraint t_sys_dict_item_UN
        unique (dic_id, item, label)
)
    comment '系统数据字典项';

create table cavy.t_sys_file
(
    id          int auto_increment comment 'id'
        primary key,
    file_code   varchar(255) null comment '文件编号',
    file_name   varchar(255) null comment '文件名称',
    file_path   varchar(255) null comment '文件路径',
    web_url     varchar(255) null comment '前端地址',
    add_user    varchar(255) null comment '创建人',
    add_time    datetime     null comment '创建时间',
    update_user varchar(255) null comment '修改人',
    update_time datetime     null comment '修改时间'
)
    comment '系统文件表';

create table cavy.t_sys_menu
(
    id          int auto_increment comment 'id'
        primary key,
    menu_id     int           null comment '菜单id',
    menu_code   varchar(255)  null comment '菜单编号',
    menu_name   varchar(255)  not null comment '菜单名称',
    parent_id   int default 0 null comment '父id',
    menu_type   char          null comment '菜单类型 M：目录；C：菜单；F：按钮',
    icon        varchar(255)  null comment '图标',
    url         varchar(255)  null comment '地址',
    hidden      int           null comment '是否隐藏',
    weight      int           null comment '权重',
    sort        int           null comment '排序',
    is_default  int           null comment '是否默认',
    add_time    datetime      null comment '创建时间',
    add_user    varchar(255)  null comment '创建人',
    update_time datetime      null comment '修改人',
    update_user varchar(255)  null comment '修改时间',
    status      int(1)        null comment '菜单状态 1启用0禁用'
)
    comment '系统菜单表';

create index t_sys_menu_index
    on cavy.t_sys_menu (status, add_time, menu_code, menu_type, parent_id, sort, weight);

create table cavy.t_sys_menu_resource
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

create table cavy.t_sys_message
(
    id          int auto_increment comment 'id'
        primary key,
    receiver    varchar(100)     not null comment '接收人账号',
    title       varchar(255)     null comment '标题',
    content     varchar(500)     not null comment '编号名称',
    msg_level   char             not null comment '消息等级 1：提醒；2：一般；3：重要；',
    add_time    datetime         null comment '创建时间',
    add_user    varchar(255)     null comment '创建人',
    update_time datetime         null comment '修改时间',
    update_user varchar(255)     null comment '修改人',
    status      char default '0' null comment '状态 1已读；0未读'
)
    comment '系统消息表';

create table cavy.t_sys_resource
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
    comment '系统资源表';

create table cavy.t_sys_role
(
    id          int auto_increment comment 'id'
        primary key,
    role_name   varchar(255) null comment '角色名称',
    add_time    datetime     null comment '创建时间',
    add_user    varchar(255) null comment '创建人',
    update_time datetime     null comment '修改时间',
    update_user varchar(255) null comment '修改人'
)
    comment '系统角色表';

create table cavy.t_sys_role_menu
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
    comment '系统角色菜单关联表';

create table cavy.t_sys_user
(
    id           int auto_increment comment 'id'
        primary key,
    user_name    varchar(255)     null comment '账号',
    pwd     varchar(255)     null comment '密码',
    openid       varchar(255)     null comment '微信openid',
    channel       CHAR(1)         NULL comment '渠道来源',
    real_name    varchar(255)     null comment '真实姓名',
    gender       int              null comment '性别',
    phone        varchar(255)     null comment '电话',
    email        varchar(255)     null comment '邮件',
    status       char default '0' not null comment '状态 0已新建;1已使用;2已禁用;3已冻结',
    add_time     datetime         null comment '创建时间',
    add_user     varchar(255)     null comment '创建人',
    update_time  datetime         null comment '修改时间',
    update_user  varchar(255)     null comment '修改人',
    default_user char default '0' null comment '默认用户 0否;1是',
    constraint t_sys_user_pk
        unique (openid)
)
    comment '系统用户表';

create index t_sys_user_index
    on cavy.t_sys_user (real_name, pwd, status, phone, add_time);

create table cavy.t_sys_user_menu
(
    id          int auto_increment comment 'id'
        primary key,
    user_id     int          null comment '用户id',
    menu_id     int          null comment '菜单id',
    add_time    datetime     null comment '创建时间',
    add_user    varchar(255) null comment '创建人',
    update_time datetime     null comment '修改时间',
    update_user varchar(255) null comment '修改人',
    constraint t_sys_user_menu_user_id_menu_id_uindex
        unique (user_id, menu_id)
)
    comment '系统用户菜单表';

create table cavy.t_sys_user_role
(
    id      int auto_increment comment 'id'
        primary key,
    user_id int null comment '用户id',
    role_id int null comment '角色id',
    constraint t_sys_user_role_user_id_role_id_uindex
        unique (user_id, role_id)
)
    comment '系统用户角色表';

