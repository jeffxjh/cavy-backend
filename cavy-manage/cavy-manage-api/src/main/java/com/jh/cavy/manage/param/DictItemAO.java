package com.jh.cavy.manage.param;

import com.jh.cavy.manage.dto.DictItemDTO;
import lombok.Data;

import java.util.List;
@Data
public class DictItemAO {
    private Long dicId;
    private List<DictItemDTO> items;
}
