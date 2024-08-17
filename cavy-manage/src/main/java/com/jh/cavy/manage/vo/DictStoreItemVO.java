package com.jh.cavy.manage.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class DictStoreItemVO implements Serializable {
    /**
     * key
     */
    private String item;

    /**
     * value
     */
    private String label;
}
