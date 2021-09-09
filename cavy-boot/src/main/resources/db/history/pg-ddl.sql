
-- ----------------------------
-- Table structure for t_bus_answer
-- -- 2021-09-09 23:19:51
-- -- 回答表
-- ----------------------------

CREATE SEQUENCE "t_bus_answer_id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE IF EXISTS "t_bus_answer";

CREATE TABLE "t_bus_answer"(
                               "id" integer NOT NULL DEFAULT nextval('"t_bus_answer_id_seq"'::regclass),
                               "question_id" integer DEFAULT NULL,
                               "content" text COLLATE "default",
                               "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                               "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                               "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                               "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                               CONSTRAINT t_bus_answer_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_bus_answer" IS '回答表';

COMMENT ON COLUMN "t_bus_answer"."id" IS 'id';
COMMENT ON COLUMN "t_bus_answer"."question_id" IS '问题id';
COMMENT ON COLUMN "t_bus_answer"."content" IS '内容';

-- ----------------------------
-- Table structure for t_bus_question
-- -- 2021-09-09 23:19:51
-- -- 问题表
-- ----------------------------

CREATE SEQUENCE "t_bus_question_id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE IF EXISTS "t_bus_question";

CREATE TABLE "t_bus_question"(
                                 "id" integer NOT NULL DEFAULT nextval('"t_bus_question_id_seq"'::regclass),
                                 "title" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                                 "content" text COLLATE "default",
                                 "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                                 "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                                 "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                                 "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                                 CONSTRAINT t_bus_question_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_bus_question" IS '问题表';

COMMENT ON COLUMN "t_bus_question"."id" IS 'id';
COMMENT ON COLUMN "t_bus_question"."title" IS '标题';
COMMENT ON COLUMN "t_bus_question"."content" IS '内容';
COMMENT ON COLUMN "t_bus_question"."add_time" IS '创建时间';
COMMENT ON COLUMN "t_bus_question"."add_user" IS '创建人';
COMMENT ON COLUMN "t_bus_question"."update_time" IS '修改时间';
COMMENT ON COLUMN "t_bus_question"."update_user" IS '修改人';

-- ----------------------------
-- Table structure for t_sys_config
-- -- 2021-09-09 23:19:51
-- -- 系统配置表
-- ----------------------------

CREATE SEQUENCE "t_sys_config_id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE IF EXISTS "t_sys_config";

CREATE TABLE "t_sys_config"(
                               "id" integer NOT NULL DEFAULT nextval('"t_sys_config_id_seq"'::regclass),
                               "code" character varying(255) COLLATE "default" NOT NULL,
                               "code_value" character varying(255) COLLATE "default" NOT NULL,
                               "name" character varying(255) COLLATE "default" NOT NULL,
                               "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                               "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                               "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                               "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                               CONSTRAINT t_sys_config_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_sys_config" IS '系统配置表';

COMMENT ON COLUMN "t_sys_config"."id" IS 'id';
COMMENT ON COLUMN "t_sys_config"."code" IS '编号';
COMMENT ON COLUMN "t_sys_config"."code_value" IS '编号名称';
COMMENT ON COLUMN "t_sys_config"."name" IS '名称';
COMMENT ON COLUMN "t_sys_config"."add_time" IS '创建时间';
COMMENT ON COLUMN "t_sys_config"."add_user" IS '创建人';
COMMENT ON COLUMN "t_sys_config"."update_time" IS '修改时间';
COMMENT ON COLUMN "t_sys_config"."update_user" IS '修改人';

-- ----------------------------
-- Table structure for t_sys_file
-- -- 2021-09-09 23:19:51
-- -- 文件表
-- ----------------------------

CREATE SEQUENCE "t_sys_file_id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE IF EXISTS "t_sys_file";

CREATE TABLE "t_sys_file"(
                             "id" integer NOT NULL DEFAULT nextval('"t_sys_file_id_seq"'::regclass),
                             "file_code" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "file_name" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "file_path" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "web_url" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             CONSTRAINT t_sys_file_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_sys_file" IS '文件表';

COMMENT ON COLUMN "t_sys_file"."id" IS 'id';
COMMENT ON COLUMN "t_sys_file"."file_code" IS '文件编号';
COMMENT ON COLUMN "t_sys_file"."file_name" IS '文件名称';
COMMENT ON COLUMN "t_sys_file"."file_path" IS '文件路径';
COMMENT ON COLUMN "t_sys_file"."web_url" IS '前端地址';
COMMENT ON COLUMN "t_sys_file"."add_user" IS '创建人';
COMMENT ON COLUMN "t_sys_file"."add_time" IS '创建时间';
COMMENT ON COLUMN "t_sys_file"."update_user" IS '修改人';
COMMENT ON COLUMN "t_sys_file"."update_time" IS '修改时间';

-- ----------------------------
-- Table structure for t_sys_menu
-- -- 2021-09-09 23:19:51
-- -- 菜单表
-- ----------------------------

CREATE SEQUENCE "t_sys_menu_id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE IF EXISTS "t_sys_menu";

CREATE TABLE "t_sys_menu"(
                             "id" integer NOT NULL DEFAULT nextval('"t_sys_menu_id_seq"'::regclass),
                             "menu_id" integer DEFAULT NULL,
                             "menu_code" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "menu_name" character varying(255) COLLATE "default" NOT NULL,
                             "parent_id" integer DEFAULT 0,
                             "icon" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "url" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "hidden" integer DEFAULT NULL,
                             "weight" integer DEFAULT NULL,
                             "sort" integer DEFAULT NULL,
                             "is_default" integer DEFAULT NULL,
                             "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             CONSTRAINT t_sys_menu_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_sys_menu" IS '菜单表';

COMMENT ON COLUMN "t_sys_menu"."id" IS 'id';
COMMENT ON COLUMN "t_sys_menu"."menu_id" IS '菜单id';
COMMENT ON COLUMN "t_sys_menu"."menu_code" IS '菜单编号';
COMMENT ON COLUMN "t_sys_menu"."menu_name" IS '菜单名称';
COMMENT ON COLUMN "t_sys_menu"."parent_id" IS '父id';
COMMENT ON COLUMN "t_sys_menu"."icon" IS '图标';
COMMENT ON COLUMN "t_sys_menu"."url" IS '地址';
COMMENT ON COLUMN "t_sys_menu"."hidden" IS '是否隐藏';
COMMENT ON COLUMN "t_sys_menu"."weight" IS '权重';
COMMENT ON COLUMN "t_sys_menu"."sort" IS '排序';
COMMENT ON COLUMN "t_sys_menu"."is_default" IS '是否默认';
COMMENT ON COLUMN "t_sys_menu"."add_time" IS '创建时间';
COMMENT ON COLUMN "t_sys_menu"."add_user" IS '创建人';
COMMENT ON COLUMN "t_sys_menu"."update_time" IS '修改人';
COMMENT ON COLUMN "t_sys_menu"."update_user" IS '修改时间';

-- ----------------------------
-- Table structure for t_sys_role
-- -- 2021-09-09 23:19:51
-- -- 角色表
-- ----------------------------

CREATE SEQUENCE "t_sys_role_id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE IF EXISTS "t_sys_role";

CREATE TABLE "t_sys_role"(
                             "id" integer NOT NULL DEFAULT nextval('"t_sys_role_id_seq"'::regclass),
                             "role_name" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "menu_id" integer DEFAULT NULL,
                             "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             CONSTRAINT t_sys_role_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_sys_role" IS '角色表';

COMMENT ON COLUMN "t_sys_role"."id" IS 'id';
COMMENT ON COLUMN "t_sys_role"."role_name" IS '角色名称';
COMMENT ON COLUMN "t_sys_role"."menu_id" IS '菜单id';
COMMENT ON COLUMN "t_sys_role"."add_time" IS '创建时间';
COMMENT ON COLUMN "t_sys_role"."add_user" IS '创建人';
COMMENT ON COLUMN "t_sys_role"."update_time" IS '修改时间';
COMMENT ON COLUMN "t_sys_role"."update_user" IS '修改人';

-- ----------------------------
-- Table structure for t_sys_user
-- -- 2021-09-09 23:19:51
-- -- 用户表
-- ----------------------------

CREATE SEQUENCE "t_sys_user_id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE IF EXISTS "t_sys_user";

CREATE TABLE "t_sys_user"(
                             "id" integer NOT NULL DEFAULT nextval('"t_sys_user_id_seq"'::regclass),
                             "user_name" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "password" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "real_name" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "gender" integer DEFAULT NULL,
                             "phone" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "email" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             CONSTRAINT t_sys_user_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_sys_user" IS '用户表';

COMMENT ON COLUMN "t_sys_user"."id" IS 'id';
COMMENT ON COLUMN "t_sys_user"."user_name" IS '账号';
COMMENT ON COLUMN "t_sys_user"."password" IS '密码';
COMMENT ON COLUMN "t_sys_user"."real_name" IS '真实姓名';
COMMENT ON COLUMN "t_sys_user"."gender" IS '性别';
COMMENT ON COLUMN "t_sys_user"."phone" IS '电话';
COMMENT ON COLUMN "t_sys_user"."email" IS '邮件';
COMMENT ON COLUMN "t_sys_user"."add_time" IS '创建时间';
COMMENT ON COLUMN "t_sys_user"."add_user" IS '创建人';
COMMENT ON COLUMN "t_sys_user"."update_time" IS '修改时间';
COMMENT ON COLUMN "t_sys_user"."update_user" IS '修改人';

-- ----------------------------
-- Table structure for t_sys_user_menu
-- -- 2021-09-09 23:19:51
-- -- 用户菜单表
-- ----------------------------

-- DROP TABLE IF EXISTS "t_sys_user_menu";

CREATE TABLE "t_sys_user_menu"(
                                  "id" integer DEFAULT NULL,
                                  "user_id" integer DEFAULT NULL,
                                  "menu_id" integer DEFAULT NULL,
                                  "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                                  "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                                  "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                                  "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_sys_user_menu" IS '用户菜单表';

COMMENT ON COLUMN "t_sys_user_menu"."id" IS 'id';
COMMENT ON COLUMN "t_sys_user_menu"."user_id" IS '用户id';
COMMENT ON COLUMN "t_sys_user_menu"."menu_id" IS '菜单id';
COMMENT ON COLUMN "t_sys_user_menu"."add_time" IS '创建时间';
COMMENT ON COLUMN "t_sys_user_menu"."add_user" IS '创建人';
COMMENT ON COLUMN "t_sys_user_menu"."update_time" IS '修改时间';
COMMENT ON COLUMN "t_sys_user_menu"."update_user" IS '修改人';

-- ----------------------------
-- Table structure for t_sys_user_role
-- -- 2021-09-09 23:19:51
-- -- 用户角色表
-- ----------------------------

-- DROP TABLE IF EXISTS "t_sys_user_role";

CREATE TABLE "t_sys_user_role"(
                                  "id" integer NOT NULL,
                                  "user_id" integer DEFAULT NULL,
                                  "role_id" integer DEFAULT NULL,
                                  CONSTRAINT t_sys_user_role_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_sys_user_role" IS '用户角色表';

COMMENT ON COLUMN "t_sys_user_role"."id" IS 'id';
COMMENT ON COLUMN "t_sys_user_role"."user_id" IS '用户id';
COMMENT ON COLUMN "t_sys_user_role"."role_id" IS '角色id';

-- ----------------------------
-- Table structure for t_bus_answer
-- -- 2021-09-09 23:19:51
-- -- 回答表
-- ----------------------------

CREATE SEQUENCE "t_bus_answer_id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE IF EXISTS "t_bus_answer";

CREATE TABLE "t_bus_answer"(
                               "id" integer NOT NULL DEFAULT nextval('"t_bus_answer_id_seq"'::regclass),
                               "question_id" integer DEFAULT NULL,
                               "content" text COLLATE "default",
                               CONSTRAINT t_bus_answer_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_bus_answer" IS '回答表';

COMMENT ON COLUMN "t_bus_answer"."id" IS 'id';
COMMENT ON COLUMN "t_bus_answer"."question_id" IS '问题id';
COMMENT ON COLUMN "t_bus_answer"."content" IS '内容';

-- ----------------------------
-- Table structure for t_bus_question
-- -- 2021-09-09 23:19:51
-- -- 问题表
-- ----------------------------

CREATE SEQUENCE "t_bus_question_id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE IF EXISTS "t_bus_question";

CREATE TABLE "t_bus_question"(
                                 "id" integer NOT NULL DEFAULT nextval('"t_bus_question_id_seq"'::regclass),
                                 "title" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                                 "content" text COLLATE "default",
                                 "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                                 "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                                 "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                                 "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                                 CONSTRAINT t_bus_question_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_bus_question" IS '问题表';

COMMENT ON COLUMN "t_bus_question"."id" IS 'id';
COMMENT ON COLUMN "t_bus_question"."title" IS '标题';
COMMENT ON COLUMN "t_bus_question"."content" IS '内容';
COMMENT ON COLUMN "t_bus_question"."add_time" IS '创建时间';
COMMENT ON COLUMN "t_bus_question"."add_user" IS '创建人';
COMMENT ON COLUMN "t_bus_question"."update_time" IS '修改时间';
COMMENT ON COLUMN "t_bus_question"."update_user" IS '修改人';

-- ----------------------------
-- Table structure for t_sys_config
-- -- 2021-09-09 23:19:51
-- -- 系统配置表
-- ----------------------------

CREATE SEQUENCE "t_sys_config_id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE IF EXISTS "t_sys_config";

CREATE TABLE "t_sys_config"(
                               "id" integer NOT NULL DEFAULT nextval('"t_sys_config_id_seq"'::regclass),
                               "code" character varying(255) COLLATE "default" NOT NULL,
                               "code_value" character varying(255) COLLATE "default" NOT NULL,
                               "name" character varying(255) COLLATE "default" NOT NULL,
                               "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                               "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                               "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                               "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                               CONSTRAINT t_sys_config_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_sys_config" IS '系统配置表';

COMMENT ON COLUMN "t_sys_config"."id" IS 'id';
COMMENT ON COLUMN "t_sys_config"."code" IS '编号';
COMMENT ON COLUMN "t_sys_config"."code_value" IS '编号名称';
COMMENT ON COLUMN "t_sys_config"."name" IS '名称';
COMMENT ON COLUMN "t_sys_config"."add_time" IS '创建时间';
COMMENT ON COLUMN "t_sys_config"."add_user" IS '创建人';
COMMENT ON COLUMN "t_sys_config"."update_time" IS '修改时间';
COMMENT ON COLUMN "t_sys_config"."update_user" IS '修改人';

-- ----------------------------
-- Table structure for t_sys_file
-- -- 2021-09-09 23:19:51
-- -- 文件表
-- ----------------------------

CREATE SEQUENCE "t_sys_file_id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE IF EXISTS "t_sys_file";

CREATE TABLE "t_sys_file"(
                             "id" integer NOT NULL DEFAULT nextval('"t_sys_file_id_seq"'::regclass),
                             "file_code" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "file_name" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "file_path" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "web_url" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             CONSTRAINT t_sys_file_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_sys_file" IS '文件表';

COMMENT ON COLUMN "t_sys_file"."id" IS 'id';
COMMENT ON COLUMN "t_sys_file"."file_code" IS '文件编号';
COMMENT ON COLUMN "t_sys_file"."file_name" IS '文件名称';
COMMENT ON COLUMN "t_sys_file"."file_path" IS '文件路径';
COMMENT ON COLUMN "t_sys_file"."web_url" IS '前端地址';
COMMENT ON COLUMN "t_sys_file"."add_user" IS '创建人';
COMMENT ON COLUMN "t_sys_file"."add_time" IS '创建时间';
COMMENT ON COLUMN "t_sys_file"."update_user" IS '修改人';
COMMENT ON COLUMN "t_sys_file"."update_time" IS '修改时间';

-- ----------------------------
-- Table structure for t_sys_menu
-- -- 2021-09-09 23:19:51
-- -- 菜单表
-- ----------------------------

CREATE SEQUENCE "t_sys_menu_id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE IF EXISTS "t_sys_menu";

CREATE TABLE "t_sys_menu"(
                             "id" integer NOT NULL DEFAULT nextval('"t_sys_menu_id_seq"'::regclass),
                             "menu_id" integer DEFAULT NULL,
                             "menu_code" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "menu_name" character varying(255) COLLATE "default" NOT NULL,
                             "parent_id" integer DEFAULT 0,
                             "icon" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "url" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "hidden" integer DEFAULT NULL,
                             "weight" integer DEFAULT NULL,
                             "sort" integer DEFAULT NULL,
                             "is_default" integer DEFAULT NULL,
                             "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             CONSTRAINT t_sys_menu_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_sys_menu" IS '菜单表';

COMMENT ON COLUMN "t_sys_menu"."id" IS 'id';
COMMENT ON COLUMN "t_sys_menu"."menu_id" IS '菜单id';
COMMENT ON COLUMN "t_sys_menu"."menu_code" IS '菜单编号';
COMMENT ON COLUMN "t_sys_menu"."menu_name" IS '菜单名称';
COMMENT ON COLUMN "t_sys_menu"."parent_id" IS '父id';
COMMENT ON COLUMN "t_sys_menu"."icon" IS '图标';
COMMENT ON COLUMN "t_sys_menu"."url" IS '地址';
COMMENT ON COLUMN "t_sys_menu"."hidden" IS '是否隐藏';
COMMENT ON COLUMN "t_sys_menu"."weight" IS '权重';
COMMENT ON COLUMN "t_sys_menu"."sort" IS '排序';
COMMENT ON COLUMN "t_sys_menu"."is_default" IS '是否默认';
COMMENT ON COLUMN "t_sys_menu"."add_time" IS '创建时间';
COMMENT ON COLUMN "t_sys_menu"."add_user" IS '创建人';
COMMENT ON COLUMN "t_sys_menu"."update_time" IS '修改人';
COMMENT ON COLUMN "t_sys_menu"."update_user" IS '修改时间';

-- ----------------------------
-- Table structure for t_sys_role
-- -- 2021-09-09 23:19:51
-- -- 角色表
-- ----------------------------

CREATE SEQUENCE "t_sys_role_id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE IF EXISTS "t_sys_role";

CREATE TABLE "t_sys_role"(
                             "id" integer NOT NULL DEFAULT nextval('"t_sys_role_id_seq"'::regclass),
                             "role_name" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "menu_id" integer DEFAULT NULL,
                             "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             CONSTRAINT t_sys_role_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_sys_role" IS '角色表';

COMMENT ON COLUMN "t_sys_role"."id" IS 'id';
COMMENT ON COLUMN "t_sys_role"."role_name" IS '角色名称';
COMMENT ON COLUMN "t_sys_role"."menu_id" IS '菜单id';
COMMENT ON COLUMN "t_sys_role"."add_time" IS '创建时间';
COMMENT ON COLUMN "t_sys_role"."add_user" IS '创建人';
COMMENT ON COLUMN "t_sys_role"."update_time" IS '修改时间';
COMMENT ON COLUMN "t_sys_role"."update_user" IS '修改人';

-- ----------------------------
-- Table structure for t_sys_user
-- -- 2021-09-09 23:19:51
-- -- 用户表
-- ----------------------------

CREATE SEQUENCE "t_sys_user_id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE IF EXISTS "t_sys_user";

CREATE TABLE "t_sys_user"(
                             "id" integer NOT NULL DEFAULT nextval('"t_sys_user_id_seq"'::regclass),
                             "user_name" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "password" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "real_name" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "gender" integer DEFAULT NULL,
                             "phone" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "email" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                             "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                             CONSTRAINT t_sys_user_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_sys_user" IS '用户表';

COMMENT ON COLUMN "t_sys_user"."id" IS 'id';
COMMENT ON COLUMN "t_sys_user"."user_name" IS '账号';
COMMENT ON COLUMN "t_sys_user"."password" IS '密码';
COMMENT ON COLUMN "t_sys_user"."real_name" IS '真实姓名';
COMMENT ON COLUMN "t_sys_user"."gender" IS '性别';
COMMENT ON COLUMN "t_sys_user"."phone" IS '电话';
COMMENT ON COLUMN "t_sys_user"."email" IS '邮件';
COMMENT ON COLUMN "t_sys_user"."add_time" IS '创建时间';
COMMENT ON COLUMN "t_sys_user"."add_user" IS '创建人';
COMMENT ON COLUMN "t_sys_user"."update_time" IS '修改时间';
COMMENT ON COLUMN "t_sys_user"."update_user" IS '修改人';

-- ----------------------------
-- Table structure for t_sys_user_menu
-- -- 2021-09-09 23:19:51
-- -- 用户菜单表
-- ----------------------------

-- DROP TABLE IF EXISTS "t_sys_user_menu";

CREATE TABLE "t_sys_user_menu"(
                                  "id" integer DEFAULT NULL,
                                  "user_id" integer DEFAULT NULL,
                                  "menu_id" integer DEFAULT NULL,
                                  "add_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                                  "add_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying,
                                  "update_time" timestamp with time zone DEFAULT NULL::timestamp with time zone,
                                  "update_user" character varying(255) COLLATE "default" DEFAULT NULL::character varying
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_sys_user_menu" IS '用户菜单表';

COMMENT ON COLUMN "t_sys_user_menu"."id" IS 'id';
COMMENT ON COLUMN "t_sys_user_menu"."user_id" IS '用户id';
COMMENT ON COLUMN "t_sys_user_menu"."menu_id" IS '菜单id';
COMMENT ON COLUMN "t_sys_user_menu"."add_time" IS '创建时间';
COMMENT ON COLUMN "t_sys_user_menu"."add_user" IS '创建人';
COMMENT ON COLUMN "t_sys_user_menu"."update_time" IS '修改时间';
COMMENT ON COLUMN "t_sys_user_menu"."update_user" IS '修改人';

-- ----------------------------
-- Table structure for t_sys_user_role
-- -- 2021-09-09 23:19:51
-- -- 用户角色表
-- ----------------------------

-- DROP TABLE IF EXISTS "t_sys_user_role";

CREATE TABLE "t_sys_user_role"(
                                  "id" integer NOT NULL,
                                  "user_id" integer DEFAULT NULL,
                                  "role_id" integer DEFAULT NULL,
                                  CONSTRAINT t_sys_user_role_pkey PRIMARY KEY ("id")
)
WITH (
    OIDS = FALSE
)
;

COMMENT ON TABLE "t_sys_user_role" IS '用户角色表';

COMMENT ON COLUMN "t_sys_user_role"."id" IS 'id';
COMMENT ON COLUMN "t_sys_user_role"."user_id" IS '用户id';
COMMENT ON COLUMN "t_sys_user_role"."role_id" IS '角色id';
