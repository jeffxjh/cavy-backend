package com.jh.cavy.favour.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
@Getter
@Setter
public class FavourInoutHeadVO implements Serializable {
    private BigDecimal totalInAmt;
    private Long totalInNum;
    private BigDecimal totalOutAmt;
    private Long totalOutNum;
}
