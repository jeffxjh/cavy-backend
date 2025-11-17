package com.jh.cavy.manage.api;

import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.param.DictAO;
import com.jh.cavy.manage.param.DictItemAO;
import com.jh.cavy.manage.vo.DictItemVO;
import com.jh.cavy.manage.vo.DictStoreVO;
import com.jh.cavy.manage.vo.DictVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequestMapping("/dict")
@RestController
@FeignClient(name = "cavy-manage", contextId = "dictClient", path = "dict")
public interface DictClient {

    @GetMapping("/{id}")
    ResultPage<DictVO> getDict(@PathVariable String id);

    @PostMapping("/getDictItem")
    List<DictItemVO> getDictItem(@RequestBody DictAO dictAO);

    @PostMapping("/getDictItemMap")
    List<Map<String, String>> getDictItemMap(@RequestBody DictAO dictAO);

    @PostMapping("/store")
    List<DictStoreVO> store(@RequestBody DictAO dictAO);

    @PostMapping
    ResultPage<DictVO> queryDictPage(@RequestBody DictAO dictAO);

    @PostMapping("add")
    void addDict(@RequestBody DictAO dictAO);

    @DeleteMapping("del/{id}")
    void delDict(@PathVariable String id);

    @PostMapping("update")
    void updateDict(@RequestBody DictAO dictAO);

    @PostMapping("item/add")
    void addDictItem(@RequestBody DictItemAO dictItem);

    @DeleteMapping("item/del/{id}")
    void delDictItem(@PathVariable String id);
}
