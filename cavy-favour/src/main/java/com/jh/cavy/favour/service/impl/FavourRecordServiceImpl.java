package com.jh.cavy.favour.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.cavy.common.Resquest.RequestHeadHolder;
import com.jh.cavy.common.mybatisPlus.PageUtil;
import com.jh.cavy.favour.ao.FavourInoutAO;
import com.jh.cavy.favour.ao.FavourRecordAO;
import com.jh.cavy.favour.domain.FavourBook;
import com.jh.cavy.favour.domain.FavourRecord;
import com.jh.cavy.favour.mapper.FavourRecordMapper;
import com.jh.cavy.favour.service.FavourRecordService;
import com.jh.cavy.favour.vo.FavourBookVO;
import com.jh.cavy.favour.vo.FavourInoutHeadVO;
import com.jh.cavy.favour.vo.FavourInoutPageVO;
import com.jh.cavy.favour.vo.FavourRecordVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author jeffx
 * @description 针对表【t_bus_favour_record(人情管理-人情记录表)】的数据库操作Service实现
 * @createDate 2024-09-20 22:36:16
 */
@Service
@RequiredArgsConstructor
public class FavourRecordServiceImpl extends ServiceImpl<FavourRecordMapper, FavourRecord> implements FavourRecordService {
    private final FavourRecordMapper favourRecordMapper;

    @Override
    public Page<FavourInoutPageVO> inoutPage(FavourInoutAO favourInoutAO) {
        Long userId = RequestHeadHolder.getUserId();
        LambdaQueryWrapper<FavourRecord> queryWrapper = Wrappers.lambdaQuery(FavourRecord.class);
        queryWrapper.eq(FavourRecord::getCurrentUserId, userId).orderByDesc(FavourRecord::getAddTime);
        return favourRecordMapper.inoutPage(PageUtil.newPage(favourInoutAO), queryWrapper);
    }

    @Override
    public FavourInoutHeadVO inoutHead(FavourInoutHeadVO favourInoutHeadVO) {
        Long userId = RequestHeadHolder.getUserId();
        LambdaQueryWrapper<FavourRecord> queryWrapper = Wrappers.lambdaQuery(FavourRecord.class);
        queryWrapper.eq(FavourRecord::getCurrentUserId, userId);
        List<FavourRecord> favourRecordList = favourRecordMapper.inoutHead(queryWrapper);
        Map<String, List<FavourRecord>> inoutMap = favourRecordList.stream().collect(Collectors.groupingBy(FavourRecord::getTradeType));
        List<FavourRecord> outRecordList = inoutMap.get("1");
        List<FavourRecord> intRecordList = inoutMap.get("0");
        favourInoutHeadVO = new FavourInoutHeadVO();
        if (CollectionUtil.isNotEmpty(outRecordList)) {
            favourInoutHeadVO.setTotalOutAmt(outRecordList.stream().map(FavourRecord::getAmt)
                                                 .filter(Objects::nonNull)
                                                 .reduce(BigDecimal.ZERO, BigDecimal::add));
            favourInoutHeadVO.setTotalOutNum((long) outRecordList.size());
        }
        if (CollectionUtil.isNotEmpty(intRecordList)) {
            favourInoutHeadVO.setTotalInAmt(intRecordList.stream().map(FavourRecord::getAmt)
                                                .filter(Objects::nonNull)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add));
            favourInoutHeadVO.setTotalInNum((long) intRecordList.size());
        }
        return favourInoutHeadVO;
    }

    @Override
    public FavourRecordVO updateRecord(FavourRecordAO favourRecordAO) {
        return null;
    }

    @Override
    public FavourRecordVO addRecord(FavourRecordAO favourRecordAO) {
        FavourRecord favourRecord = BeanUtil.copyProperties(favourRecordAO, FavourRecord.class);
        favourRecord.setCurrentUserId(RequestHeadHolder.getUserId().intValue());
        favourRecordMapper.insert(favourRecord);
        return BeanUtil.copyProperties(favourRecord, FavourRecordVO.class);
    }
}




