package com.jh.cavy.favour.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.favour.ao.FavourBookAO;
import com.jh.cavy.favour.domain.FavourBookGift;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jh.cavy.favour.dto.FavourBookParams;
import com.jh.cavy.favour.vo.FavourBookGiftVO;
import com.jh.cavy.favour.vo.FavourBookVO;
import com.jh.cavy.favour.vo.FavourRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jeffx
 * @description 针对表【t_bus_favour_book_gift(人情管理-礼薄明细表)】的数据库操作Mapper
 * @createDate 2024-09-22 18:42:00
 * @Entity com.jh.cavy.favour.domain.FavourBookGift
 */
public interface FavourBookGiftMapper extends BaseMapper<FavourBookGift> {

    Page<FavourBookGiftVO> queryPage(Page<FavourBookParams> page, @Param(Constants.WRAPPER) Wrapper<FavourBookGift> wrapper);

    List<FavourRecordVO> getGiftByUserId(@Param(Constants.WRAPPER) QueryWrapper<FavourBookGift> queryWrapper);

}




