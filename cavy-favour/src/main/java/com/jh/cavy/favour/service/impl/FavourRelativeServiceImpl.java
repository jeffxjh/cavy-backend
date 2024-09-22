package com.jh.cavy.favour.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.cavy.favour.ao.FavourRelativeAO;
import com.jh.cavy.favour.domain.FavourRelative;
import com.jh.cavy.favour.mapper.FavourRecordMapper;
import com.jh.cavy.favour.mapper.FavourRelativeMapper;
import com.jh.cavy.favour.service.FavourRelativeService;
import com.jh.cavy.favour.vo.FavourRelativeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Page<FavourRelativeVO> queryPage(FavourRelativeAO favourRelativeAO) {
        return null;
    }

    @Override
    public void add(FavourRelativeAO favourRelativeAO) {

    }

    @Override
    public void del(List<Integer> ids) {

    }

    @Override
    public void modify(FavourRelativeAO favourRelativeAO) {

    }
}




