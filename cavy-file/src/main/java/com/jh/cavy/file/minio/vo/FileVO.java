package com.jh.cavy.file.minio.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 文件VO
    */
@ApiModel(value="com-jh-cavyfile-domain-File")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileVO {
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

}