package com.jh.cavy.favour.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.cavy.common.mybatisPlus.PageUtil;
import com.jh.cavy.favour.ao.FavourBookAO;
import com.jh.cavy.favour.ao.FavourBookGiftAO;
import com.jh.cavy.favour.domain.FavourBook;
import com.jh.cavy.favour.domain.FavourBookGift;
import com.jh.cavy.favour.mapper.FavourBookGiftMapper;
import com.jh.cavy.favour.mapper.FavourBookMapper;
import com.jh.cavy.favour.service.FavourBookService;
import com.jh.cavy.favour.vo.FavourBookGiftVO;
import com.jh.cavy.favour.vo.FavourBookVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jeffx
 * @description 针对表【t_bus_favour_book(人情管理-礼薄表)】的数据库操作Service实现
 * @createDate 2024-09-22 17:50:16
 */
@Service
@RequiredArgsConstructor
public class FavourBookServiceImpl extends ServiceImpl<FavourBookMapper, FavourBook> implements FavourBookService {
    private final FavourBookMapper favourBookMapper;
    private final FavourBookGiftMapper favourBookGiftMapper;

    @Override
    public Page<FavourBookVO> queryPage(FavourBookAO favourBookAO) {
        LambdaQueryWrapper<FavourBook> queryWrapper = Wrappers.<FavourBook>lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(favourBookAO.getBussType()), FavourBook::getBussType, favourBookAO.getBussType());
        Page<FavourBookAO> page = PageUtil.newPage(favourBookAO);
        return favourBookMapper.queryPage(page, queryWrapper);
    }

    @Override
    public void addBook(FavourBookAO favourBookAO) {

    }

    @Override
    public void delBook(List<Integer> ids) {

    }

    @Override
    public void modifyBook(FavourBookAO favourBookAO) {

    }

    @Override
    public Page<FavourBookGiftVO> giftReceivePage(FavourBookAO favourBookAO) {
        LambdaQueryWrapper<FavourBookGift> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(favourBookAO.getId() != null, FavourBookGift::getFavourBookId, favourBookAO.getId());
        Page<FavourBookAO> page = PageUtil.newPage(favourBookAO);
        return favourBookGiftMapper.queryPage(page, queryWrapper);
    }

    @Override
    public void updateBookGift(FavourBookGiftAO favourBookGiftAO) {
        if (favourBookGiftAO.getId() == null) {
            return;
        }
        LambdaUpdateWrapper<FavourBookGift> updateWrapper = Wrappers.<FavourBookGift>lambdaUpdate()
                                                                    .eq(FavourBookGift::getId, favourBookGiftAO.getId());
        updateWrapper.set(FavourBookGift::getAmt, favourBookGiftAO.getAmt());
        updateWrapper.set(FavourBookGift::getRelateUserId, favourBookGiftAO.getRelateUserId());
        updateWrapper.set(FavourBookGift::getRemarks, favourBookGiftAO.getRemarks());
        favourBookGiftMapper.update(updateWrapper);
    }
}




