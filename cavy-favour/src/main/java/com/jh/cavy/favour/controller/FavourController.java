package com.jh.cavy.favour.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.favour.ao.FavourBookAO;
import com.jh.cavy.favour.ao.FavourBookGiftAO;
import com.jh.cavy.favour.ao.FavourInoutAO;
import com.jh.cavy.favour.ao.FavourRelativeAO;
import com.jh.cavy.favour.service.FavourBookService;
import com.jh.cavy.favour.service.FavourRecordService;
import com.jh.cavy.favour.service.FavourRelativeService;
import com.jh.cavy.favour.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/favour")
@RestController
@RequiredArgsConstructor
public class FavourController {
    private final FavourRelativeService favourRelativeService;
    private final FavourBookService favourBookService;
    private final FavourRecordService favourRecordService;

    @PostMapping("/relate")
    public ResultPage<FavourRelativeVO> queryDictPage(@RequestBody FavourRelativeAO favourRelativeAO) {
        return new ResultPage<>(favourRelativeService.queryPage(favourRelativeAO));
    }

    @PostMapping("/relate/add")
    public FavourRelativeVO addRelate(@RequestBody FavourRelativeAO favourRelativeAO) {
        return favourRelativeService.add(favourRelativeAO);
    }

    @DeleteMapping("/relate/del")
    public void delRelate(@RequestParam("ids") List<Integer> ids) {
         favourRelativeService.del(ids);
    }

    @PutMapping("/relate/update")
    public FavourRelativeVO updateRelate(@RequestBody FavourRelativeAO favourRelativeAO) {
        return favourRelativeService.modify(favourRelativeAO);
    }

    @GetMapping("/relate")
    public FavourRelativeVO getRelate(@RequestParam("id") String id) {
        return favourRelativeService.getRelate(id);
    }

    @PostMapping("/book")
    public ResultPage<FavourBookVO> bookPage(@RequestBody FavourBookAO favourBookAO) {
        return new ResultPage<>(favourBookService.queryPage(favourBookAO));
    }

    @PostMapping("/book/giftReceive/queryPage")
    public ResultPage<FavourBookGiftVO> giftReceivePage(@RequestBody FavourBookAO favourBookAO) {
        return new ResultPage<>(favourBookService.giftReceivePage(favourBookAO));
    }

    @PostMapping("/book/updateBookGift")
    public void updateBookGift(@RequestBody FavourBookGiftAO favourBookGiftAO) {
        favourBookService.updateBookGift(favourBookGiftAO);
    }

    @PostMapping("/book/add")
    public void addBook(@RequestBody FavourBookAO favourBookAO) {
        favourBookService.addBook(favourBookAO);
    }

    @DeleteMapping("/book/del")
    public void delBook(@RequestParam("ids") List<Integer> ids) {
        favourBookService.delBook(ids);
    }

    @PostMapping("/book/update")
    public void modifyBook(@RequestBody FavourBookAO favourBookAO) {
        favourBookService.modifyBook(favourBookAO);
    }

    @PostMapping("/inout/inoutPage")
    public ResultPage<FavourInoutPageVO> inoutPage(@RequestBody FavourInoutAO favourInoutAO) {
        return new ResultPage<>(favourRecordService.inoutPage(favourInoutAO));
    }

    @PostMapping("/inout/inoutHead")
    public FavourInoutHeadVO inoutHead(@RequestBody FavourInoutHeadVO favourInoutHeadVO) {
        return favourRecordService.inoutHead(favourInoutHeadVO);
    }

    @PostMapping("/relative/list")
    public List<FavourRelativeVO> listRelative(@RequestBody FavourRelativeAO favourRelativeAO) {
        return favourRelativeService.listRelative(favourRelativeAO);
    }

    @PostMapping("/relative/listRecord")
    public FavourRelativeRecordDetailVO listRecord(@RequestBody FavourRelativeAO favourRelativeAO) {
        return favourRelativeService.listRecord(favourRelativeAO);
    }
}
