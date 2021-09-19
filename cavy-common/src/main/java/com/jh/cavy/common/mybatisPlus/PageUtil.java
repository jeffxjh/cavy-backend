package com.jh.cavy.common.mybatisPlus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.common.Resquest.BaseParam;

public class PageUtil {
    private final static Long pageIndex = 1L;
    private final static Long pageSize = 10L;
    public static <T extends BaseParam> Page<T>  newPage(T t){
        return new Page<>(t.getPageIndex()==0?pageIndex:t.getPageIndex(),t.getPageSize()==0?pageSize:t.getPageSize());
    }
}
