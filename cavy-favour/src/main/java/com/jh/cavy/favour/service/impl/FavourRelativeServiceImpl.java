package com.jh.cavy.favour.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.promeg.pinyinhelper.Pinyin;
import com.jh.cavy.common.Resquest.RequestHeadHolder;
import com.jh.cavy.common.exception.APIException;
import com.jh.cavy.favour.ao.FavourRelativeAO;
import com.jh.cavy.favour.domain.FavourRecord;
import com.jh.cavy.favour.domain.FavourRelative;
import com.jh.cavy.favour.mapper.FavourRecordMapper;
import com.jh.cavy.favour.mapper.FavourRelativeMapper;
import com.jh.cavy.favour.service.FavourBookService;
import com.jh.cavy.favour.service.FavourRelativeService;
import com.jh.cavy.favour.vo.FavourInoutHeadVO;
import com.jh.cavy.favour.vo.FavourRecordVO;
import com.jh.cavy.favour.vo.FavourRelativeRecordDetailVO;
import com.jh.cavy.favour.vo.FavourRelativeVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jeffx
 * @description 针对表【t_bus_favour_relative(人情管理-亲友表)】的数据库操作Service实现
 * @createDate 2024-09-20 22:36:16
 */
@Service
@RequiredArgsConstructor
public class FavourRelativeServiceImpl extends ServiceImpl<FavourRelativeMapper, FavourRelative> implements FavourRelativeService {
    final private FavourRelativeMapper favourRelativeMapper;
    final private FavourRecordMapper favourRecordMapper;
    final private FavourBookService favourBookService;

    @Override
    public Page<FavourRelativeVO> queryPage(FavourRelativeAO favourRelativeAO) {
        return null;
    }

    @Override
    public FavourRelativeVO add(FavourRelativeAO favourRelativeAO) {
        FavourRelative favourRelative = BeanUtil.copyProperties(favourRelativeAO, FavourRelative.class);
        favourRelative.setUserId(RequestHeadHolder.getUserId().intValue());
        favourRelative.setRelateType(Optional.ofNullable(favourRelative.getRelateType()).orElse(1));
        favourRelativeMapper.insert(favourRelative);
        return BeanUtil.copyProperties(favourRelative, FavourRelativeVO.class);
    }

    @Override
    public void del(List<Integer> ids) {
         favourRelativeMapper.deleteByIds(ids);
    }

    @Override
    public FavourRelativeVO modify(FavourRelativeAO favourRelativeAO) {
        FavourRelative favourRelative = favourRelativeMapper.selectById(favourRelativeAO.getId());
        if (favourRelative == null) {
            throw new APIException("更新的数据不存在");
        }
        BeanUtil.copyProperties(favourRelativeAO, favourRelative);
        favourRelativeMapper.updateById(favourRelative);
        return null;
    }

    @Override
    public List<FavourRelativeVO> listRelative(FavourRelativeAO favourRelativeAO) {
        LambdaQueryWrapper<FavourRelative> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(FavourRelative::getUserId, RequestHeadHolder.getUserId());
        List<FavourRelativeVO> favourRelativeList = favourRelativeMapper.listRelative(lambdaQuery);
        return favourRelativeList.stream().filter(Objects::nonNull)
                       .peek(a -> a.setRelateUserNameFirstChar(Pinyin.toPinyin(a.getRealName().charAt(0)).substring(0, 1))
                       ).collect(Collectors.toList());
    }

    @Override
    public FavourRelativeRecordDetailVO listRecord(FavourRelativeAO favourRelativeAO) {
        if (favourRelativeAO.getId() == null) {
            throw new RuntimeException("favourRelativeAO id is null");
        }
        QueryWrapper<FavourRelative> lambdaQuery = Wrappers.query();
        lambdaQuery.eq("user_id", RequestHeadHolder.getUserId());
        lambdaQuery.eq("b.id", favourRelativeAO.getId());
        List<FavourRecordVO> favourRecordVOList = favourRelativeMapper.listRecord(lambdaQuery);
        List<FavourRecordVO> giftByUserId = favourBookService.getGiftByUserId(favourRelativeAO.getId());
        favourRecordVOList.addAll(giftByUserId);
        Map<String, List<FavourRecordVO>> collect = favourRecordVOList.stream().collect(Collectors.groupingBy(FavourRecordVO::getTradeType));
        List<FavourRecordVO> outRecordList = collect.get("1");
        List<FavourRecordVO> intRecordList = collect.get("0");
        FavourRelativeRecordDetailVO favourRelativeRecordDetailVO = new FavourRelativeRecordDetailVO();
        if (CollectionUtil.isNotEmpty(outRecordList)) {
            favourRelativeRecordDetailVO.setTotalOutAmt(outRecordList.stream().map(FavourRecordVO::getAmt)
                                                                .filter(Objects::nonNull)
                                                                .reduce(BigDecimal.ZERO, BigDecimal::add));
            favourRelativeRecordDetailVO.setTotalOutNum((long) outRecordList.size());
        }
        if (CollectionUtil.isNotEmpty(intRecordList)) {
            favourRelativeRecordDetailVO.setTotalInAmt(intRecordList.stream().map(FavourRecordVO::getAmt)
                                                               .filter(Objects::nonNull)
                                                               .reduce(BigDecimal.ZERO, BigDecimal::add));
            favourRelativeRecordDetailVO.setTotalInNum((long) intRecordList.size());
        }
        favourRelativeRecordDetailVO.setRecordList(favourRecordVOList);
        return favourRelativeRecordDetailVO;
    }

    @Override
    public FavourRelativeVO getRelate(String id) {
        return favourRelativeMapper.getRelate(id);
    }
}




