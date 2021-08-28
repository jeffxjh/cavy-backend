create table t_bus_answer
(
    id          int auto_increment
        primary key,
    question_id int          null,
    content     text         null,
    adduser     varchar(255) null,
    addtime     datetime     null,
    updateuser  varchar(255) null,
    updatetime  datetime     null
)
    comment '回答表';

INSERT INTO cavy.t_bus_answer (id, question_id, content, adduser, addtime, updateuser, updatetime) VALUES (1, 1, '哈哈1', 'admin', '2021-01-01 00:00:00', null, null);
INSERT INTO cavy.t_bus_answer (id, question_id, content, adduser, addtime, updateuser, updatetime) VALUES (2, 1, '哈哈2', 'admin', '2021-01-01 00:00:00', null, null);
INSERT INTO cavy.t_bus_answer (id, question_id, content, adduser, addtime, updateuser, updatetime) VALUES (3, 1, '哈哈3', 'admin', '2021-01-01 00:00:00', null, null);
INSERT INTO cavy.t_bus_answer (id, question_id, content, adduser, addtime, updateuser, updatetime) VALUES (4, 1, '哈哈4', 'admin', '2021-01-01 00:00:00', null, null);
INSERT INTO cavy.t_bus_answer (id, question_id, content, adduser, addtime, updateuser, updatetime) VALUES (5, 1, '哈哈5', 'admin', '2021-01-01 00:00:00', null, null);
INSERT INTO cavy.t_bus_answer (id, question_id, content, adduser, addtime, updateuser, updatetime) VALUES (6, 1, '哈哈6', 'admin', '2021-01-01 00:00:00', null, null);