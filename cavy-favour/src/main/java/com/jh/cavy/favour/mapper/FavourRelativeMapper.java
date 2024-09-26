package com.jh.cavy.favour.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jh.cavy.favour.domain.FavourRelative;
import com.jh.cavy.favour.vo.FavourRecordVO;
import com.jh.cavy.favour.vo.FavourRelativeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author jeffx
* @description 针对表【t_bus_favour_relative(人情管理-亲友表)】的数据库操作Mapper
* @createDate 2024-09-20 22:36:16
* @Entity com.jh.cavy.manage.domain.TBusFavourRelative
*/
public interface FavourRelativeMapper extends BaseMapper<FavourRelative> {

    List<FavourRelativeVO> listRelative(@Param(Constants.WRAPPER)LambdaQueryWrapper<FavourRelative> lambdaQuery);

    List<FavourRecordVO> listRecord(@Param(Constants.WRAPPER) QueryWrapper<FavourRelative> queryWrapper);
}




