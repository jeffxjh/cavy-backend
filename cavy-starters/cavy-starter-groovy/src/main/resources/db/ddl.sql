CREATE TABLE t_sys_script (
                              id          SERIAL PRIMARY KEY, -- 主键
                              unique_key varchar(32) NULL, -- 唯一标识
                              script     TEXT NULL, -- 脚本内容
                              add_user    VARCHAR(255) NULL, -- 创建人
                              add_time    TIMESTAMP(0) NULL, -- 创建时间
                              update_user VARCHAR(255) NULL, -- 修改人
                              update_time TIMESTAMP(0) NULL  -- 修改时间
);


COMMENT ON TABLE t_sys_script IS '脚本表';
COMMENT ON COLUMN t_sys_script.id IS 'id';
COMMENT ON COLUMN t_sys_script.unique_key IS '唯一标识';
COMMENT ON COLUMN t_sys_script.script IS '脚本内容';
COMMENT ON COLUMN t_sys_script.add_user IS '创建人';
COMMENT ON COLUMN t_sys_script.add_time IS '创建时间';
COMMENT ON COLUMN t_sys_script.update_user IS '修改人';
COMMENT ON COLUMN t_sys_script.update_time IS '修改时间';