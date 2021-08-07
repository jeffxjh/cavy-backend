package com.jh.cavy.manage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "com-jh-cavymanage-domain-Answer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerVO implements Serializable {
    private String addUser;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private Date addTime;
    private String content;


}
