package com.jh.favour.controller;

import com.jh.cavy.common.Result.ResultPage;
import com.jh.favour.ao.FavourRelativeAO;
import com.jh.favour.service.FavourRelativeService;
import com.jh.favour.vo.FavourRelativeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/favour/relate")
@RestController
@RequiredArgsConstructor
public class FavourRelativeController {
    private final FavourRelativeService favourRelativeService;

    @PostMapping
    public ResultPage<FavourRelativeVO> queryDictPage(@RequestBody FavourRelativeAO favourRelativeAO) {
        return new ResultPage<>(favourRelativeService.queryPage(favourRelativeAO));
    }

    @PostMapping("add")
    public void addDict(@RequestBody FavourRelativeAO favourRelativeAO) {
        favourRelativeService.add(favourRelativeAO);
    }

    @DeleteMapping("del")
    public void delDict(@RequestParam("ids") List<Integer> ids) {
        favourRelativeService.del(ids);
    }

    @PostMapping("update")
    public void updateDict(@RequestBody FavourRelativeAO favourRelativeAO) {
        favourRelativeService.modify(favourRelativeAO);
    }

}
