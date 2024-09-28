package com.jh.cavy.favour.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.favour.ao.FavourBookAO;
import com.jh.cavy.favour.domain.FavourBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jh.cavy.favour.vo.FavourBookVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author jeffx
* @description 针对表【t_bus_favour_book(人情管理-礼薄表)】的数据库操作Mapper
* @createDate 2024-09-22 17:50:16
* @Entity com.jh.favour.domain.FavourBook
*/
public interface FavourBookMapper extends BaseMapper<FavourBook> {

    Page<FavourBookVO> queryPage(Page<FavourBookAO> favourBookAOPage,@Param(Constants.WRAPPER) LambdaQueryWrapper<FavourBook> queryWrapper);

    List<FavourBookVO> bookList(@Param(Constants.WRAPPER) LambdaQueryWrapper<FavourBook> objectLambdaQueryWrapper);
}




