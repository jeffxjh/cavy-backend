package com.jh.cavy.file.minio.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
    * 文件表
    */
@ApiModel(value="com-jh-cavyfile-domain-File")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_file")
public class File {
    /**
    * id
    */
    @ApiModelProperty(value="id")
    private Integer id;

    /**
    * 文件编号
    */
    @ApiModelProperty(value="文件编号")
    private String fileCode;

    /**
    * 文件名称
    */
    @ApiModelProperty(value="文件名称")
    private String fileName;

    /**
    * 文件路径
    */
    @ApiModelProperty(value="文件路径")
    private String filePath;

    /**
    * 前端地址
    */
    @ApiModelProperty(value="前端地址")
    private String webUrl;

    /**
    * 创建人
    */
    @ApiModelProperty(value="创建人")
    private String addUser;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date addTime;

    /**
    * 修改人
    */
    @ApiModelProperty(value="修改人")
    private String updateUser;

    /**
    * 修改时间
    */
    @ApiModelProperty(value="修改时间")
    private Date updateTime;
}