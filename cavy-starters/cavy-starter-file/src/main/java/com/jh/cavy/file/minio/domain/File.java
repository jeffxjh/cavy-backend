package com.jh.cavy.file.minio.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
    * 文件表
    */
@Schema(name="com-jh-cavyfile-domain-File")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_file")
public class File {
    /**
    * id
    */
    @TableId(value = "id",type = IdType.AUTO)
    @Schema(name="id")
    private Integer id;

    /**
    * 文件编号
    */
    @Schema(name="文件编号")
    private String fileCode;

    /**
    * 文件名称
    */
    @Schema(name="文件名称")
    private String fileName;

    /**
    * 文件路径
    */
    @Schema(name="文件路径")
    private String filePath;

    /**
    * 前端地址
    */
    @Schema(name="前端地址")
    private String webUrl;

    /**
    * 创建人
    */
    @Schema(name="创建人")
    private String addUser;

    /**
    * 创建时间
    */
    @Schema(name="创建时间")
    private Date addTime;

    /**
    * 修改人
    */
    @Schema(name="修改人")
    private String updateUser;

    /**
    * 修改时间
    */
    @Schema(name="修改时间")
    private Date updateTime;
}