package com.jh.cavy.favour.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.favour.ao.FavourBookAO;
import com.jh.cavy.favour.ao.FavourBookGiftAO;
import com.jh.cavy.favour.domain.FavourBook;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jh.cavy.favour.dto.FavourBookParams;
import com.jh.cavy.favour.vo.FavourBookGiftVO;
import com.jh.cavy.favour.vo.FavourBookVO;
import com.jh.cavy.favour.vo.FavourRecordVO;

import java.util.List;

/**
* @author jeffx
* @description 针对表【t_bus_favour_book(人情管理-礼薄表)】的数据库操作Service
* @createDate 2024-09-22 17:50:16
*/
public interface FavourBookService extends IService<FavourBook> {


    Page<FavourBookVO> queryPage(FavourBookParams favourBookParams);

    List<FavourBookVO> bookList(FavourBookParams favourBookParams);

    FavourBookVO addBook(FavourBookAO favourBookAO);

    void delBook(List<Integer> ids);

    void modifyBook(FavourBookAO favourBookAO);

    Page<FavourBookGiftVO> giftReceivePage(FavourBookParams favourBookParams);

    void updateBookGift(FavourBookGiftAO favourBookGiftAO);

    List<FavourRecordVO> getGiftByUserId(Integer id);


    void addBookGift(FavourBookGiftAO favourBookGiftAO);

    void delBookGift(List<Integer> ids);
}
