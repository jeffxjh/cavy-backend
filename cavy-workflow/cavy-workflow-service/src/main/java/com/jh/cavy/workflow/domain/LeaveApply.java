package com.jh.cavy.workflow.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jh.cavy.common.mybatisPlus.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@TableName(value = "t_wf_leave_apply")
public class LeaveApply extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String processInstanceId;

    private String applicant;

    private String manager;

    private Date startDate;

    private Date endDate;

    private Integer days;

    private String reason;

    private String status;

    private String formData;

}