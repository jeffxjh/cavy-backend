package com.jh.cavy.favour.controller;

import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.favour.ao.FavourBookAO;
import com.jh.cavy.favour.ao.FavourBookGiftAO;
import com.jh.cavy.favour.ao.FavourRelativeAO;
import com.jh.cavy.favour.service.FavourBookService;
import com.jh.cavy.favour.service.FavourRelativeService;
import com.jh.cavy.favour.vo.FavourBookGiftVO;
import com.jh.cavy.favour.vo.FavourBookVO;
import com.jh.cavy.favour.vo.FavourRelativeVO;
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

    @PostMapping("/relate")
    public ResultPage<FavourRelativeVO> queryDictPage(@RequestBody FavourRelativeAO favourRelativeAO) {
        return new ResultPage<>(favourRelativeService.queryPage(favourRelativeAO));
    }

    @PostMapping("/relate/add")
    public void addDict(@RequestBody FavourRelativeAO favourRelativeAO) {
        favourRelativeService.add(favourRelativeAO);
    }

    @DeleteMapping("/relate/del")
    public void delDict(@RequestParam("ids") List<Integer> ids) {
        favourRelativeService.del(ids);
    }

    @PostMapping("/relate/update")
    public void updateDict(@RequestBody FavourRelativeAO favourRelativeAO) {
        favourRelativeService.modify(favourRelativeAO);
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
}
