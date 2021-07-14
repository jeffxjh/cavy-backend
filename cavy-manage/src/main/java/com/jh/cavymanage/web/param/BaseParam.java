package com.jh.cavymanage.web.param;

import java.io.Serializable;

public class BaseParam implements Serializable {
    private Long pageIndex;
    private Long pageSize;

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
