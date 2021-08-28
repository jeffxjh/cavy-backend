create table t_sys_file
(
    id         int auto_increment
        primary key,
    file_code  varchar(255) null,
    file_name  varchar(255) null,
    file_path  varchar(255) null,
    web_url    varchar(255) null,
    adduser    varchar(255) null,
    addtime    datetime     null,
    updateuser varchar(255) null,
    updatetime datetime     null
)
    comment '文件表';

INSERT INTO cavy.t_sys_file (id, file_code, file_name, file_path, web_url, adduser, addtime, updateuser, updatetime) VALUES (1, '022a0c0c18505a1290081bfc06cf45af152261ab972b27a7f46d79b828169480', '15358071_large.png', '2021/31/31/15358071_large.png', 'http://193.149.161.195:9000/2021/31/31/15358071_large.png', null, null, null, null);