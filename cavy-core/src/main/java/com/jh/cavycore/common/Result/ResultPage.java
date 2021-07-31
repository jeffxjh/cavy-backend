package com.jh.cavycore.common.Result;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * T 是指定返回类型，P是原始类型
 *
 * @author xujiahao
 * @mail xujiahao@zving.com
 * @date 16:00 2021/7/4
 */
@Getter
public class ResultPage<T> implements Serializable {
    private List<T> data;
    private Long pageIndex;
    private Long pageSize;
    private Long total;

    public ResultPage() {
    }

    public ResultPage(Page<T> page) {
        this.data = page.getRecords();
        this.pageIndex = page.getCurrent();
        this.pageSize = page.getSize();
        this.total = page.getTotal();
    }

}
