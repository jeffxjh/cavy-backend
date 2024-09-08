package com.jh.cavy.file.minio.vo;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 文件VO
    */
@Schema(name="com-jh-cavyfile-domain-File")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileVO {
    /**
    * id
    */
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

}