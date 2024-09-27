package com.jh.cavy.manage.api;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.domain.DictItem;
import com.jh.cavy.manage.param.DictAO;
import com.jh.cavy.manage.param.DictItemAO;
import com.jh.cavy.manage.service.DictItemService;
import com.jh.cavy.manage.service.DictService;
import com.jh.cavy.manage.vo.DictItemVO;
import com.jh.cavy.manage.vo.DictStoreVO;
import com.jh.cavy.manage.vo.DictVO;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/dict")
@RestController
@RequiredArgsConstructor
public class DictController {
    private final DictService dictService;
    private final DictItemService dictItemService;

    @GetMapping("/{id}")
    public ResultPage<DictVO> getDict(@PathVariable String id) {
        return dictService.getDict(id);
    }

    @PostMapping("/getDictItem")
    public List<DictItemVO> getDictItem(@RequestBody DictAO dictAO) {
        return BeanUtil.copyToList(dictItemService.list(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDicId, dictAO.getId())), DictItemVO.class);
    }
    @PostMapping("/getDictItemMap")
    public List<Map<String,String>> getDictItemMap(@RequestBody DictAO dictAO) {
        return dictItemService.list(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDicId, dictAO.getId())).stream().parallel().map(
                a->new HashMap<String,String>() {{put("id",a.getItem());put("name",a.getLabel());}}
        ).collect(Collectors.toUnmodifiableList());
    }

    @PostMapping("/store")
    public List<DictStoreVO> store(@RequestBody DictAO dictAO) {
        return dictService.store(dictAO);
    }

    @PostMapping
    public ResultPage<DictVO> queryDictPage(@RequestBody DictAO dictAO) {
        return new ResultPage<>(dictService.queryDictPage(dictAO));
    }

    @PostMapping("add")
    public void addDict(@RequestBody DictAO dictAO) {
        dictService.addDict(dictAO);
    }

    @DeleteMapping("del/{id}")
    public void delDict(@PathVariable String id) {
        dictService.delDict(id);
    }

    @PostMapping("update")
    public void updateDict(@RequestBody DictAO dictAO) {
        dictService.updateDict(dictAO);
    }

    @PostMapping("item/add")
    public void addDictItem(@RequestBody DictItemAO dictItem) {
        dictService.addDictItem(dictItem);
    }

    @DeleteMapping("item/del/{id}")
    public void delDictItem(@PathVariable String id) {
        dictService.delDictItem(id);
    }
}
