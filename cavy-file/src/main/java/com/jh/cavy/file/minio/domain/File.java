package com.jh.cavy.file.minio.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="")
    private String fileCode;

    @ApiModelProperty(value="")
    private String fileName;

    @ApiModelProperty(value="")
    private String filePath;

    @ApiModelProperty(value="")
    private String webUrl;
}