package com.jh.cavy.manage.client;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.api.DictClient;
import com.jh.cavy.manage.domain.DictItem;
import com.jh.cavy.manage.param.DictAO;
import com.jh.cavy.manage.param.DictItemAO;
import com.jh.cavy.manage.service.DictItemService;
import com.jh.cavy.manage.service.DictService;
import com.jh.cavy.manage.vo.DictItemVO;
import com.jh.cavy.manage.vo.DictStoreVO;
import com.jh.cavy.manage.vo.DictVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public record DictClientImpl(DictService dictService, DictItemService dictItemService) implements DictClient {
    @Override
    public ResultPage<DictVO> getDict(String id) {
        return dictService.getDict(id);
    }

    @Override
    public List<DictItemVO> getDictItem(DictAO dictAO) {
        return BeanUtil.copyToList(dictItemService.list(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDicId, dictAO.getId())), DictItemVO.class);
    }

    @Override
    public List<Map<String, String>> getDictItemMap(DictAO dictAO) {
        return dictItemService.list(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDicId, dictAO.getId())).stream().parallel().map(
                a -> new HashMap<String, String>() {{
                    put("id", a.getItem());
                    put("name", a.getLabel());
                }}
        ).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<DictStoreVO> store(DictAO dictAO) {
        return dictService.store(dictAO);
    }

    @Override
    public ResultPage<DictVO> queryDictPage(DictAO dictAO) {
        return new ResultPage<>(dictService.queryDictPage(dictAO));
    }

    @Override
    public void addDict(DictAO dictAO) {
        dictService.addDict(dictAO);
    }

    @Override
    public void delDict(String id) {
        dictService.delDict(id);
    }

    @Override
    public void updateDict(DictAO dictAO) {
        dictService.updateDict(dictAO);
    }

    @Override
    public void addDictItem(DictItemAO dictItem) {
        dictService.addDictItem(dictItem);
    }

    @Override
    public void delDictItem(String id) {
        dictService.delDictItem(id);
    }
}
