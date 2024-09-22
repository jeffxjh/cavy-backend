package com.jh.cavy.favour.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.favour.ao.FavourBookAO;
import com.jh.cavy.favour.ao.FavourBookGiftAO;
import com.jh.cavy.favour.domain.FavourBook;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jh.cavy.favour.vo.FavourBookGiftVO;
import com.jh.cavy.favour.vo.FavourBookVO;

import java.util.List;

/**
* @author jeffx
* @description 针对表【t_bus_favour_book(人情管理-礼薄表)】的数据库操作Service
* @createDate 2024-09-22 17:50:16
*/
public interface FavourBookService extends IService<FavourBook> {


    Page<FavourBookVO> queryPage(FavourBookAO favourBookAO);

    void addBook(FavourBookAO favourBookAO);

    void delBook(List<Integer> ids);

    void modifyBook(FavourBookAO favourBookAO);

    Page<FavourBookGiftVO> giftReceivePage(FavourBookAO favourBookAO);

    void updateBookGift(FavourBookGiftAO favourBookGiftAO);
}
