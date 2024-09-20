package com.jh.favour.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jh.favour.ao.FavourRelativeAO;
import com.jh.favour.domain.FavourRelative;
import com.jh.favour.vo.FavourRelativeVO;

import java.util.List;

/**
* @author jeffx
* @description 针对表【t_bus_favour_relative(人情管理-亲友表)】的数据库操作Service
* @createDate 2024-09-20 22:36:16
*/
public interface FavourRelativeService extends IService<FavourRelative> {

    Page<FavourRelativeVO> queryPage(FavourRelativeAO favourRelativeAO);

    void add(FavourRelativeAO favourRelativeAO);

    void del(List<Integer> ids);

    void modify(FavourRelativeAO favourRelativeAO);
}
