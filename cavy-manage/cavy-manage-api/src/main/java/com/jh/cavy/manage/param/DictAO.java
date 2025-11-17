package com.jh.cavy.manage.param;

import com.jh.cavy.common.Resquest.BaseParam;
import com.jh.cavy.manage.vo.DictItemVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DictAO extends BaseParam {
    private List<String> codeList;
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

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

    private List<DictItemVO> children;
}
