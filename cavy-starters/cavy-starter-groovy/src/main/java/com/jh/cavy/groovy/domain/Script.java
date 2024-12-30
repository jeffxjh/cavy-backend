package com.jh.cavy.groovy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Schema(name = "com.jh.cavy.groovy.domain.Script")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_sys_script")
public class Script implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 唯一标识
     */
    private String uniqueKey;

    /**
     * 脚本
     */
    private String script;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
