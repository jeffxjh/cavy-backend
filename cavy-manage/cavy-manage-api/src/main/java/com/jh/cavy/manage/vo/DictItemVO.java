package com.jh.cavy.manage.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class DictItemVO {
    /**
     * id
     */
    private Long id;

    /**
     * 主表id
     */
    private Long dicId;

    /**
     * key
     */
    private String item;

    /**
     * value
     */
    private String label;

    /**
     * 创建时间
     */
    private Date addTime;

    /**
     * 创建人
     */
    private String addUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;
}
