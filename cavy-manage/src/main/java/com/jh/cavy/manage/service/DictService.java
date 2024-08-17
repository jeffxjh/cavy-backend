package com.jh.cavy.manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.domain.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jh.cavy.manage.param.DictAO;
import com.jh.cavy.manage.param.DictItemAO;
import com.jh.cavy.manage.vo.DictStoreVO;
import com.jh.cavy.manage.vo.DictVO;

import java.util.List;

/**
* @author jeffx
* @description 针对表【t_sys_dict(数据字典表)】的数据库操作Service
* @createDate 2024-08-11 17:50:14
*/
public interface DictService extends IService<Dict> {

    ResultPage<DictVO> getDict(String id);

    Page<DictVO> queryDictPage(DictAO dictAO);

    void addDict(DictAO dictAO);

    void delDict(String id);

    void updateDict(DictAO dictAO);

    void addDictItem(DictItemAO dictItem);

    void delDictItem(String id);

    List<DictStoreVO> store(DictAO dictAO);

}
