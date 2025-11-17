package com.jh.cavy.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jh.cavy.common.mybatisPlus.BaseEntity;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@TableName(value = "t_wf_process_definition")
public class ProcessDef extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private String id;

    private String name;

    private String defKey;

    private String bpmnXml;

    private Integer version;

    private String status;
}
