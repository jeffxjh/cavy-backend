package com.jh.cavy.common.Resquest;

import java.io.Serializable;

public class BaseParam implements Serializable {
    private Long pageIndex=1L;
    private Long pageSize=10L;

    public Long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }


}
