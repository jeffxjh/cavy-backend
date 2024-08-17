package com.jh.cavy.manage.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class DictStoreVO implements Serializable {
    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    private List<DictStoreItemVO> childrenData;
}
