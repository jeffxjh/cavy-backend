package com.jh.cavy.favour.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
@Setter
@Getter
public class FavourRelativeRecordDetailVO implements Serializable {
    private BigDecimal totalInAmt=BigDecimal.ZERO;
    private Long totalInNum=0L;
    private BigDecimal totalOutAmt=BigDecimal.ZERO;
    private Long totalOutNum=0L;
    private List<FavourRecordVO> giftOutList;
    private List<FavourRecordVO> giftReceiveList;
    private List<FavourRecordVO> recordList;
}
