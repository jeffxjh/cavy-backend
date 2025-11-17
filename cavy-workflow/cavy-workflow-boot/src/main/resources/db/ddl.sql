-- 扩展表存储流程定义
CREATE TABLE custom_process_definition (
                                           id VARCHAR(64) PRIMARY KEY,
                                           name VARCHAR(255) NOT NULL,
                                           key VARCHAR(255) NOT NULL,
                                           bpmn_xml TEXT NOT NULL,
                                           version INT DEFAULT 1,
                                           status VARCHAR(50) DEFAULT 'active',
                                           created_by VARCHAR(255),
                                           created_time DATETIME,
                                           updated_time DATETIME
);

-- 业务数据表
CREATE TABLE biz_leave_apply (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 process_instance_id VARCHAR(64),
                                 applicant VARCHAR(255),
                                 manager VARCHAR(255),
                                 start_date DATETIME,
                                 end_date DATETIME,
                                 days INT,
                                 reason VARCHAR(500),
                                 status VARCHAR(50),
                                 form_data JSON,
                                 created_time DATETIME
);