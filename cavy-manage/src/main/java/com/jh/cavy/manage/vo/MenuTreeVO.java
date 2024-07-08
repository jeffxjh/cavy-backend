package com.jh.cavy.manage.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class MenuTreeVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String menuName;
    private String url;
    private String icon;
    private String code;
    private String parentId;
    private String weight;
    private List<MenuTreeVO> children;


}
