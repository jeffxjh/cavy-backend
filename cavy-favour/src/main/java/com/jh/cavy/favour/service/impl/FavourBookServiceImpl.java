package com.jh.cavy.favour.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.cavy.common.Resquest.RequestHeadHolder;
import com.jh.cavy.common.exception.APIException;
import com.jh.cavy.common.mybatisPlus.PageUtil;
import com.jh.cavy.favour.ao.FavourBookAO;
import com.jh.cavy.favour.ao.FavourBookGiftAO;
import com.jh.cavy.favour.domain.FavourBook;
import com.jh.cavy.favour.domain.FavourBookGift;
import com.jh.cavy.favour.dto.FavourBookParams;
import com.jh.cavy.favour.mapper.FavourBookGiftMapper;
import com.jh.cavy.favour.mapper.FavourBookMapper;
import com.jh.cavy.favour.service.FavourBookService;
import com.jh.cavy.favour.vo.FavourBookGiftVO;
import com.jh.cavy.favour.vo.FavourBookVO;
import com.jh.cavy.favour.vo.FavourRecordVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public Page<FavourBookVO> queryPage(FavourBookParams favourBookParams) {
        LambdaQueryWrapper<FavourBook> queryWrapper = Wrappers.<FavourBook>lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(favourBookParams.getBussType()), FavourBook::getBussType, favourBookParams.getBussType());
        queryWrapper.eq(FavourBook::getCurrentUserId, RequestHeadHolder.getUserId());
        Page<FavourBookParams> page = PageUtil.newPage(favourBookParams);
        return favourBookMapper.queryPage(page, queryWrapper);
    }

    @Override
    public FavourBookVO addBook(FavourBookAO favourBookAO) {
        FavourBook favourBook = BeanUtil.copyProperties(favourBookAO, FavourBook.class);
        favourBook.setCurrentUserId(RequestHeadHolder.getUserId().intValue());
        favourBook.setAmt(BigDecimal.ZERO);
        favourBookMapper.insert(favourBook);
        return BeanUtil.copyProperties(favourBook, FavourBookVO.class);
    }

    @Override
    public void delBook(List<Integer> ids) {
        favourBookMapper.deleteByIds(ids);
        favourBookGiftMapper.delete(Wrappers.<FavourBookGift>lambdaUpdate().in(FavourBookGift::getFavourBookId, ids));
    }

    @Override
    public void modifyBook(FavourBookAO favourBookAO) {
        FavourBook favourBook = favourBookMapper.selectById(favourBookAO.getId());
        if (favourBook == null) {
            throw new APIException("更新的数据不存在");
        }
        BeanUtil.copyProperties(favourBookAO, favourBook);
        favourBookMapper.updateById(favourBook);
    }

    @Override
    public Page<FavourBookGiftVO> giftReceivePage(FavourBookParams favourBookParams) {
        LambdaQueryWrapper<FavourBookGift> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(favourBookParams.getId() != null, FavourBookGift::getFavourBookId, favourBookParams.getId());
        Page<FavourBookParams> page = PageUtil.newPage(favourBookParams);
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

    @Override
    public List<FavourRecordVO> getGiftByUserId(Integer id) {
        QueryWrapper<FavourBookGift> queryWrapper = Wrappers.query();
        queryWrapper.eq("b.user_id", RequestHeadHolder.getUserId());
        queryWrapper.eq(id != null, "b.id", id);
        return favourBookGiftMapper.getGiftByUserId(queryWrapper);
    }

    @Override
    public List<FavourBookVO> bookList(FavourBookParams favourBookParams) {
        return favourBookMapper.bookList(Wrappers.lambdaQuery());
    }

    @Override
    public void addBookGift(FavourBookGiftAO favourBookGiftAO) {
        FavourBookGift favourBookGift = BeanUtil.copyProperties(favourBookGiftAO, FavourBookGift.class);
        favourBookGiftMapper.insert(favourBookGift);
    }

    @Override
    public void delBookGift(List<Integer> ids) {
        favourBookGiftMapper.deleteByIds(ids);
    }
}




