CREATE TABLE cavy.t_bus_answer (
                                   id          SERIAL PRIMARY KEY, -- 主键
                                   question_id INT NULL, -- 问题id
                                   content     TEXT NULL, -- 内容
                                   add_user    VARCHAR(255) NULL, -- 创建人
                                   add_time    TIMESTAMP(0) NULL, -- 创建时间
                                   update_user VARCHAR(255) NULL, -- 修改人
                                   update_time TIMESTAMP(0) NULL  -- 修改时间
);


COMMENT ON TABLE cavy.t_bus_answer IS '问题表';
-- Add comments for columns (optional)
COMMENT ON COLUMN cavy.t_bus_answer.id IS 'id';
COMMENT ON COLUMN cavy.t_bus_answer.question_id IS '问题id';
COMMENT ON COLUMN cavy.t_bus_answer.content IS '内容';
COMMENT ON COLUMN cavy.t_bus_answer.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_bus_answer.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_bus_answer.update_user IS '修改人';
COMMENT ON COLUMN cavy.t_bus_answer.update_time IS '修改时间';

CREATE TABLE cavy.t_bus_favour_book (
                                        id              SERIAL PRIMARY KEY,      -- 主键
                                        current_user_id INT NULL,                -- 主办人用户主键
                                        buss_type       CHAR(3) NOT NULL,        -- 礼薄事项 数据字典(生日,结婚,乔迁)
                                        buss_name       VARCHAR(255) NULL,       -- 自定义的礼薄名称
                                        buss_date       DATE NOT NULL,           -- 举行日期
                                        amt             DECIMAL(18, 2) NULL,     -- 总金额
                                        add_time        TIMESTAMP NULL,          -- 创建时间
                                        add_user        VARCHAR(255) NULL,       -- 创建人
                                        update_time     TIMESTAMP NULL,          -- 修改时间
                                        update_user     VARCHAR(255) NULL,       -- 修改人
                                        remark          VARCHAR(255) NULL         -- 备注
);

COMMENT ON TABLE cavy.t_bus_favour_book IS '人情管理-礼薄表';
COMMENT ON COLUMN cavy.t_bus_favour_book.id IS '主键';
COMMENT ON COLUMN cavy.t_bus_favour_book.current_user_id IS '主办人用户主键';
COMMENT ON COLUMN cavy.t_bus_favour_book.buss_type IS '礼薄事项 数据字典(生日,结婚,乔迁)';
COMMENT ON COLUMN cavy.t_bus_favour_book.buss_name IS '自定义的礼薄名称';
COMMENT ON COLUMN cavy.t_bus_favour_book.buss_date IS '举行日期';
COMMENT ON COLUMN cavy.t_bus_favour_book.amt IS '总金额';
COMMENT ON COLUMN cavy.t_bus_favour_book.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_bus_favour_book.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_bus_favour_book.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_bus_favour_book.update_user IS '修改人';
COMMENT ON COLUMN cavy.t_bus_favour_book.remark IS '备注';

create index t_bus_favour_book_index
    on cavy.t_bus_favour_book (current_user_id, buss_type, buss_name, buss_date, add_time);

CREATE TABLE cavy.t_bus_favour_book_gift (
                                             id             SERIAL PRIMARY KEY, -- 主键
                                             favour_book_id INT NULL,            -- 礼薄表主键
                                             relate_user_id INT NULL,            -- 送礼用户主键
                                             amt            NUMERIC(18, 2) NULL, -- 金额
                                             add_time       TIMESTAMP NULL,      -- 创建时间
                                             add_user       VARCHAR(255) NULL,   -- 创建人
                                             update_time    TIMESTAMP NULL,      -- 修改时间
                                             update_user    VARCHAR(255) NULL,   -- 修改人
                                             remarks        VARCHAR(255) NULL     -- 备注
);

COMMENT ON TABLE cavy.t_bus_favour_book_gift IS '礼物赠送记录表';
COMMENT ON COLUMN cavy.t_bus_favour_book_gift.id IS '主键';
COMMENT ON COLUMN cavy.t_bus_favour_book_gift.favour_book_id IS '礼薄表主键';
COMMENT ON COLUMN cavy.t_bus_favour_book_gift.relate_user_id IS '送礼用户主键';
COMMENT ON COLUMN cavy.t_bus_favour_book_gift.amt IS '金额';
COMMENT ON COLUMN cavy.t_bus_favour_book_gift.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_bus_favour_book_gift.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_bus_favour_book_gift.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_bus_favour_book_gift.update_user IS '修改人';
COMMENT ON COLUMN cavy.t_bus_favour_book_gift.remarks IS '备注';

create index t_bus_favour_book_gift_index
    on cavy.t_bus_favour_book_gift (relate_user_id, favour_book_id, add_time);

CREATE TABLE cavy.t_bus_favour_record (
                                          id              SERIAL PRIMARY KEY, -- 主键
                                          current_user_id INT NULL,           -- 当前用户主键(主体用户)
                                          relate_user_id  INT NULL,           -- 关联用户主键
                                          trade_type      CHAR NOT NULL,      -- 往来交易类型 1支出 0收入
                                          buss_type       CHAR(3) NOT NULL,   -- 交易事项 数据字典(生日,结婚,乔迁)
                                          amt             NUMERIC(18, 2) NULL, -- 金额
                                          add_time        TIMESTAMP NULL,      -- 创建时间
                                          add_user        VARCHAR(255) NULL,   -- 创建人
                                          update_time     TIMESTAMP NULL,      -- 修改时间
                                          update_user     VARCHAR(255) NULL    -- 修改人
);

COMMENT ON TABLE cavy.t_bus_favour_record IS '亲友往来交易记录表';
COMMENT ON COLUMN cavy.t_bus_favour_record.id IS '主键';
COMMENT ON COLUMN cavy.t_bus_favour_record.current_user_id IS '当前用户主键(主体用户)';
COMMENT ON COLUMN cavy.t_bus_favour_record.relate_user_id IS '关联用户主键';
COMMENT ON COLUMN cavy.t_bus_favour_record.trade_type IS '往来交易类型 1支出 0收入';
COMMENT ON COLUMN cavy.t_bus_favour_record.buss_type IS '交易事项 数据字典(生日,结婚,乔迁)';
COMMENT ON COLUMN cavy.t_bus_favour_record.amt IS '金额';
COMMENT ON COLUMN cavy.t_bus_favour_record.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_bus_favour_record.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_bus_favour_record.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_bus_favour_record.update_user IS '修改人';

create index t_bus_favour_record_index
    on cavy.t_bus_favour_record (relate_user_id, current_user_id, trade_type, buss_type, add_time);

CREATE TABLE cavy.t_bus_favour_relative (
                                            id             SERIAL PRIMARY KEY, -- 主键
                                            real_name      VARCHAR(255) NOT NULL, -- 亲友名称
                                            nick_name      VARCHAR(255) NULL,     -- 亲友昵称
                                            relate_type    INT NOT NULL,          -- 亲友关系
                                            user_id        INT NOT NULL,          -- 关联当前用户表主键(是谁的亲友)
                                            relate_user_id INT NULL,              -- 亲友关联的用户主键(该亲友在系统中的用户主键)
                                            add_time       TIMESTAMP NULL,        -- 创建时间
                                            add_user       VARCHAR(255) NULL,     -- 创建人
                                            update_time    TIMESTAMP NULL,        -- 修改时间
                                            update_user     VARCHAR(255) NULL,    -- 修改人
                                            CONSTRAINT t_bus_favour_relative_user_id_real_name_relate_user_id_uindex
                                                UNIQUE (user_id, real_name, relate_user_id) -- 唯一约束
);

COMMENT ON TABLE cavy.t_bus_favour_relative IS '亲友关系表';
COMMENT ON COLUMN cavy.t_bus_favour_relative.id IS '主键';
COMMENT ON COLUMN cavy.t_bus_favour_relative.real_name IS '亲友名称';
COMMENT ON COLUMN cavy.t_bus_favour_relative.nick_name IS '亲友昵称';
COMMENT ON COLUMN cavy.t_bus_favour_relative.relate_type IS '亲友关系';
COMMENT ON COLUMN cavy.t_bus_favour_relative.user_id IS '关联当前用户表主键(是谁的亲友)';
COMMENT ON COLUMN cavy.t_bus_favour_relative.relate_user_id IS '亲友关联的用户主键(该亲友在系统中的用户主键)';
COMMENT ON COLUMN cavy.t_bus_favour_relative.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_bus_favour_relative.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_bus_favour_relative.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_bus_favour_relative.update_user IS '修改人';

CREATE TABLE cavy.t_bus_question (
                                     id          SERIAL PRIMARY KEY, -- id
                                     title       VARCHAR(255) NULL,   -- 标题
                                     content     TEXT NULL,           -- 内容
                                     add_time    TIMESTAMP NULL,      -- 创建时间
                                     add_user    VARCHAR(255) NULL,   -- 创建人
                                     update_time TIMESTAMP NULL,      -- 修改时间
                                     update_user VARCHAR(255) NULL     -- 修改人
);

COMMENT ON TABLE cavy.t_bus_question IS '问题表';
COMMENT ON COLUMN cavy.t_bus_question.id IS 'id';
COMMENT ON COLUMN cavy.t_bus_question.title IS '标题';
COMMENT ON COLUMN cavy.t_bus_question.content IS '内容';
COMMENT ON COLUMN cavy.t_bus_question.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_bus_question.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_bus_question.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_bus_question.update_user IS '修改人';
CREATE TABLE cavy.t_sys_config (
                                   id          SERIAL PRIMARY KEY, -- id
                                   code        VARCHAR(255) NOT NULL, -- 编号
                                   code_value  VARCHAR(255) NOT NULL, -- 编号名称
                                   name        VARCHAR(255) NOT NULL, -- 名称
                                   add_time    TIMESTAMP NULL,        -- 创建时间
                                   add_user    VARCHAR(255) NULL,     -- 创建人
                                   update_time TIMESTAMP NULL,        -- 修改时间
                                   update_user VARCHAR(255) NULL      -- 修改人
);

COMMENT ON TABLE cavy.t_sys_config IS '系统配置表';
COMMENT ON COLUMN cavy.t_sys_config.id IS 'id';
COMMENT ON COLUMN cavy.t_sys_config.code IS '编号';
COMMENT ON COLUMN cavy.t_sys_config.code_value IS '编号名称';
COMMENT ON COLUMN cavy.t_sys_config.name IS '名称';
COMMENT ON COLUMN cavy.t_sys_config.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_sys_config.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_sys_config.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_sys_config.update_user IS '修改人';

---

CREATE TABLE cavy.t_sys_dict (
                                 id          BIGSERIAL PRIMARY KEY, -- id
                                 code        VARCHAR(100) NULL,      -- 编码
                                 name        VARCHAR(100) NULL,      -- 名称
                                 add_time    TIMESTAMP NULL,         -- 创建时间
                                 add_user    VARCHAR(255) NULL,      -- 创建人
                                 update_time TIMESTAMP NULL,         -- 修改时间
                                 update_user VARCHAR(255) NULL,      -- 修改人,
                                 CONSTRAINT t_sys_dict_UN UNIQUE (code, name) -- 唯一约束
);

COMMENT ON TABLE cavy.t_sys_dict IS '系统字典表';
COMMENT ON COLUMN cavy.t_sys_dict.id IS 'id';
COMMENT ON COLUMN cavy.t_sys_dict.code IS '编码';
COMMENT ON COLUMN cavy.t_sys_dict.name IS '名称';
COMMENT ON COLUMN cavy.t_sys_dict.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_sys_dict.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_sys_dict.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_sys_dict.update_user IS '修改人';

---

CREATE TABLE cavy.t_sys_dict_item (
                                      id          BIGSERIAL PRIMARY KEY,  -- id
                                      dic_id      bigint NOT NULL,  -- 主表id
                                      item        VARCHAR(100) NOT NULL,   -- key
                                      label       VARCHAR(100) NOT NULL,   -- value
                                      add_time    TIMESTAMP NULL,          -- 创建时间
                                      add_user    VARCHAR(255) NULL,       -- 创建人
                                      update_time TIMESTAMP NULL,          -- 修改时间
                                      update_user VARCHAR(255) NULL,       -- 修改人,
                                      CONSTRAINT t_sys_dict_item_UN UNIQUE (dic_id, item, label) -- 唯一约束
);

COMMENT ON TABLE cavy.t_sys_dict_item IS '系统字典项表';
COMMENT ON COLUMN cavy.t_sys_dict_item.id IS 'id';
COMMENT ON COLUMN cavy.t_sys_dict_item.dic_id IS '主表id';
COMMENT ON COLUMN cavy.t_sys_dict_item.item IS 'key';
COMMENT ON COLUMN cavy.t_sys_dict_item.label IS 'value';
COMMENT ON COLUMN cavy.t_sys_dict_item.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_sys_dict_item.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_sys_dict_item.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_sys_dict_item.update_user IS '修改人';

---

CREATE TABLE cavy.t_sys_file (
                                 id          SERIAL PRIMARY KEY, -- id
                                 file_code   VARCHAR(255) NULL,   -- 文件编号
                                 file_name   VARCHAR(255) NULL,   -- 文件名称
                                 file_path   VARCHAR(255) NULL,   -- 文件路径
                                 web_url     VARCHAR(255) NULL,   -- 前端地址
                                 add_user    VARCHAR(255) NULL,   -- 创建人
                                 add_time    TIMESTAMP NULL,      -- 创建时间
                                 update_user VARCHAR(255) NULL,   -- 修改人
                                 update_time TIMESTAMP NULL        -- 修改时间
);

COMMENT ON TABLE cavy.t_sys_file IS '文件管理表';
COMMENT ON COLUMN cavy.t_sys_file.id IS 'id';
COMMENT ON COLUMN cavy.t_sys_file.file_code IS '文件编号';
COMMENT ON COLUMN cavy.t_sys_file.file_name IS '文件名称';
CREATE TABLE cavy.t_sys_menu (
                                 id          SERIAL PRIMARY KEY,  -- id
                                 menu_id     INT NULL,             -- 菜单id
                                 menu_code   VARCHAR(255) NULL,    -- 菜单编号
                                 menu_name   VARCHAR(255) NOT NULL, -- 菜单名称
                                 parent_id   INT DEFAULT 0 NULL,    -- 父id
                                 menu_type   CHAR NULL,             -- 菜单类型 M：目录；C：菜单；F：按钮
                                 icon        VARCHAR(255) NULL,     -- 图标
                                 url         VARCHAR(255) NULL,     -- 地址
                                 hidden      INT NULL,              -- 是否隐藏
                                 weight      INT NULL,              -- 权重
                                 sort        INT NULL,              -- 排序
                                 is_default  INT NULL,              -- 是否默认
                                 add_time    TIMESTAMP NULL,        -- 创建时间
                                 add_user    VARCHAR(255) NULL,     -- 创建人
                                 update_time TIMESTAMP NULL,        -- 修改人
                                 update_user VARCHAR(255) NULL,     -- 修改时间
                                 status      INT NULL                -- 菜单状态 1启用0禁用
);

COMMENT ON TABLE cavy.t_sys_menu IS '系统菜单表';
COMMENT ON COLUMN cavy.t_sys_menu.id IS 'id';
COMMENT ON COLUMN cavy.t_sys_menu.menu_id IS '菜单id';
COMMENT ON COLUMN cavy.t_sys_menu.menu_code IS '菜单编号';
COMMENT ON COLUMN cavy.t_sys_menu.menu_name IS '菜单名称';
COMMENT ON COLUMN cavy.t_sys_menu.parent_id IS '父id';
COMMENT ON COLUMN cavy.t_sys_menu.menu_type IS '菜单类型 M：目录；C：菜单；F：按钮';
COMMENT ON COLUMN cavy.t_sys_menu.icon IS '图标';
COMMENT ON COLUMN cavy.t_sys_menu.url IS '地址';
COMMENT ON COLUMN cavy.t_sys_menu.hidden IS '是否隐藏';
COMMENT ON COLUMN cavy.t_sys_menu.weight IS '权重';
COMMENT ON COLUMN cavy.t_sys_menu.sort IS '排序';
COMMENT ON COLUMN cavy.t_sys_menu.is_default IS '是否默认';
COMMENT ON COLUMN cavy.t_sys_menu.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_sys_menu.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_sys_menu.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_sys_menu.update_user IS '修改人';
COMMENT ON COLUMN cavy.t_sys_menu.status IS '菜单状态 1启用0禁用';

create index t_sys_menu_index
    on cavy.t_sys_menu (status, add_time, menu_code, menu_type, parent_id, sort, weight);
CREATE TABLE cavy.t_sys_menu_resource (
                                          id          BIGSERIAL PRIMARY KEY,   -- 主键
                                          menu_id     BIGINT NOT NULL,         -- 菜单表主键
                                          resource_id BIGINT NOT NULL,         -- 资源表主键
                                          add_time    TIMESTAMP NULL,          -- 创建时间
                                          add_user    VARCHAR(255) NULL,       -- 创建人
                                          update_time TIMESTAMP NULL,          -- 修改时间
                                          update_user VARCHAR(255) NULL,       -- 修改人
                                          CONSTRAINT t_sys_menu_resource_UN UNIQUE (menu_id, resource_id) -- 唯一约束
);

COMMENT ON TABLE cavy.t_sys_menu_resource IS '菜单与资源关系表';
COMMENT ON COLUMN cavy.t_sys_menu_resource.id IS '主键';
COMMENT ON COLUMN cavy.t_sys_menu_resource.menu_id IS '菜单表主键';
COMMENT ON COLUMN cavy.t_sys_menu_resource.resource_id IS '资源表主键';
COMMENT ON COLUMN cavy.t_sys_menu_resource.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_sys_menu_resource.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_sys_menu_resource.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_sys_menu_resource.update_user IS '修改人';

---

CREATE TABLE cavy.t_sys_message (
                                    id          SERIAL PRIMARY KEY,      -- id
                                    receiver    VARCHAR(100) NOT NULL,   -- 接收人账号
                                    title       VARCHAR(255) NULL,       -- 标题
                                    content     VARCHAR(500) NOT NULL,   -- 内容
                                    msg_level   CHAR NOT NULL,           -- 消息等级 1：提醒；2：一般；3：重要；
                                    add_time    TIMESTAMP NULL,          -- 创建时间
                                    add_user    VARCHAR(255) NULL,       -- 创建人
                                    update_time TIMESTAMP NULL,          -- 修改时间
                                    update_user VARCHAR(255) NULL,       -- 修改人
                                    status      CHAR DEFAULT '0' NULL    -- 状态 1已读；0未读
);

COMMENT ON TABLE cavy.t_sys_message IS '系统消息表';
COMMENT ON COLUMN cavy.t_sys_message.id IS 'id';
COMMENT ON COLUMN cavy.t_sys_message.receiver IS '接收人账号';
COMMENT ON COLUMN cavy.t_sys_message.title IS '标题';
COMMENT ON COLUMN cavy.t_sys_message.content IS '内容';
COMMENT ON COLUMN cavy.t_sys_message.msg_level IS '消息等级 1：提醒；2：一般；3：重要；';
COMMENT ON COLUMN cavy.t_sys_message.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_sys_message.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_sys_message.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_sys_message.update_user IS '修改人';
COMMENT ON COLUMN cavy.t_sys_message.status IS '状态 1已读；0未读';

---

CREATE TABLE cavy.t_sys_resource (
                                     id            BIGSERIAL PRIMARY KEY,  -- 主键
                                     resource_code VARCHAR(255) NOT NULL,   -- 资源编码
                                     resource_name VARCHAR(255) NOT NULL,   -- 资源名称
                                     resource_path VARCHAR(255) NOT NULL,   -- 资源路径
                                     add_time      TIMESTAMP NULL,          -- 创建时间
                                     add_user      VARCHAR(255) NULL,       -- 创建人
                                     update_time   TIMESTAMP NULL,          -- 修改时间
                                     update_user   VARCHAR(255) NULL,       -- 修改人
                                     CONSTRAINT t_sys_resource_UN UNIQUE (resource_code) -- 唯一约束
);

COMMENT ON TABLE cavy.t_sys_resource IS '系统资源表';
COMMENT ON COLUMN cavy.t_sys_resource.id IS '主键';
COMMENT ON COLUMN cavy.t_sys_resource.resource_code IS '资源编码';
COMMENT ON COLUMN cavy.t_sys_resource.resource_name IS '资源名称';
COMMENT ON COLUMN cavy.t_sys_resource.resource_path IS '资源路径';
COMMENT ON COLUMN cavy.t_sys_resource.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_sys_resource.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_sys_resource.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_sys_resource.update_user IS '修改人';
CREATE TABLE cavy.t_sys_role (
                                 id          SERIAL PRIMARY KEY,         -- id
                                 role_name   VARCHAR(255) NULL,          -- 角色名称
                                 add_time    TIMESTAMP NULL,             -- 创建时间
                                 add_user    VARCHAR(255) NULL,          -- 创建人
                                 update_time TIMESTAMP NULL,             -- 修改时间
                                 update_user VARCHAR(255) NULL           -- 修改人
);

COMMENT ON TABLE cavy.t_sys_role IS '系统角色表';
COMMENT ON COLUMN cavy.t_sys_role.id IS 'id';
COMMENT ON COLUMN cavy.t_sys_role.role_name IS '角色名称';
COMMENT ON COLUMN cavy.t_sys_role.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_sys_role.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_sys_role.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_sys_role.update_user IS '修改人';

---

CREATE TABLE cavy.t_sys_role_menu (
                                      id          BIGSERIAL PRIMARY KEY,      -- 主键
                                      role_id     BIGINT NOT NULL,            -- 角色表主键
                                      menu_id     BIGINT NOT NULL,            -- 菜单表主键
                                      add_time    TIMESTAMP NULL,             -- 创建时间
                                      add_user    VARCHAR(255) NULL,          -- 创建人
                                      update_time TIMESTAMP NULL,             -- 修改时间
                                      update_user VARCHAR(255) NULL,          -- 修改人
                                      CONSTRAINT t_sys_role_menu_UN UNIQUE (role_id, menu_id)  -- 唯一约束
);

COMMENT ON TABLE cavy.t_sys_role_menu IS '角色与菜单关系表';
COMMENT ON COLUMN cavy.t_sys_role_menu.id IS '主键';
COMMENT ON COLUMN cavy.t_sys_role_menu.role_id IS '角色表主键';
COMMENT ON COLUMN cavy.t_sys_role_menu.menu_id IS '菜单表主键';
COMMENT ON COLUMN cavy.t_sys_role_menu.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_sys_role_menu.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_sys_role_menu.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_sys_role_menu.update_user IS '修改人';

---

create table cavy.t_sys_user
(
    id           serial
        primary key,
    user_name    varchar(255),
    pwd          varchar(255),
    openid       varchar(255)
        constraint t_sys_user_pk
            unique,
    real_name    varchar(255),
    gender       integer,
    phone        varchar(255),
    email        varchar(255),
    status       char default '0'::bpchar not null,
    add_time     timestamp,
    add_user     varchar(255),
    update_time  timestamp,
    update_user  varchar(255),
    default_user char default '0'::bpchar,
    channel      char,
    address      varchar(255)
);

comment on table cavy.t_sys_user is '用户信息表';
comment on column cavy.t_sys_user.id is 'id';
comment on column cavy.t_sys_user.user_name is '账号';
comment on column cavy.t_sys_user.pwd is '密码';
comment on column cavy.t_sys_user.openid is '微信openid';
comment on column cavy.t_sys_user.real_name is '真实姓名';
comment on column cavy.t_sys_user.gender is '性别';
comment on column cavy.t_sys_user.phone is '电话';
comment on column cavy.t_sys_user.email is '邮件';
comment on column cavy.t_sys_user.status is '状态 0已新建;1已使用;2已禁用;3已冻结';
comment on column cavy.t_sys_user.add_time is '创建时间';
comment on column cavy.t_sys_user.add_user is '创建人';
comment on column cavy.t_sys_user.update_time is '修改时间';
comment on column cavy.t_sys_user.update_user is '修改人';
comment on column cavy.t_sys_user.default_user is '默认用户 0否;1是';
comment on column cavy.t_sys_user.channel is '渠道来源';
comment on column cavy.t_sys_user.address is '地址';
alter table cavy.t_sys_user
    owner to postgres;
create index t_sys_user_index
    on cavy.t_sys_user (real_name, pwd, status, phone, add_time);



create index t_sys_user_index
    on cavy.t_sys_user (real_name, pwd, status, phone, add_time);
CREATE TABLE cavy.t_sys_user_menu (
                                      id          SERIAL PRIMARY KEY,         -- id
                                      user_id     INT NULL,                   -- 用户id
                                      menu_id     INT NULL,                   -- 菜单id
                                      add_time    TIMESTAMP NULL,             -- 创建时间
                                      add_user    VARCHAR(255) NULL,          -- 创建人
                                      update_time TIMESTAMP NULL,             -- 修改时间
                                      update_user VARCHAR(255) NULL,          -- 修改人
                                      CONSTRAINT t_sys_user_menu_user_id_menu_id_uindex UNIQUE (user_id, menu_id)  -- 唯一约束
);

COMMENT ON TABLE cavy.t_sys_user_menu IS '用户与菜单关系表';
COMMENT ON COLUMN cavy.t_sys_user_menu.id IS 'id';
COMMENT ON COLUMN cavy.t_sys_user_menu.user_id IS '用户id';
COMMENT ON COLUMN cavy.t_sys_user_menu.menu_id IS '菜单id';
COMMENT ON COLUMN cavy.t_sys_user_menu.add_time IS '创建时间';
COMMENT ON COLUMN cavy.t_sys_user_menu.add_user IS '创建人';
COMMENT ON COLUMN cavy.t_sys_user_menu.update_time IS '修改时间';
COMMENT ON COLUMN cavy.t_sys_user_menu.update_user IS '修改人';

---

CREATE TABLE cavy.t_sys_user_role (
                                      id      SERIAL PRIMARY KEY,             -- id
                                      user_id INT NULL,                       -- 用户id
                                      role_id INT NULL,                       -- 角色id
                                      CONSTRAINT t_sys_user_role_user_id_role_id_uindex UNIQUE (user_id, role_id)  -- 唯一约束
);

COMMENT ON TABLE cavy.t_sys_user_role IS '用户与角色关系表';
COMMENT ON COLUMN cavy.t_sys_user_role.id IS 'id';
COMMENT ON COLUMN cavy.t_sys_user_role.user_id IS '用户id';
COMMENT ON COLUMN cavy.t_sys_user_role.role_id IS '角色id';





CREATE CAST (character varying AS bigint) WITH INOUT AS IMPLICIT;


DROP CAST  IF EXISTS  (character varying AS bigint)