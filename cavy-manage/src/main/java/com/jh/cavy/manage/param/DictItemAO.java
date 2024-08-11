package com.jh.cavy.manage.param;

import com.jh.cavy.manage.domain.DictItem;
import lombok.Data;

import java.util.List;
@Data
public class DictItemAO {
    private Long dicId;
    private List<DictItem> items;
}
