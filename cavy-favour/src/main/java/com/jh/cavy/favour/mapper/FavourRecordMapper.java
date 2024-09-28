package com.jh.cavy.favour.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.favour.ao.FavourInoutAO;
import com.jh.cavy.favour.domain.FavourRecord;
import com.jh.cavy.favour.vo.FavourInoutHeadVO;
import com.jh.cavy.favour.vo.FavourInoutPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jeffx
 * @description 针对表【t_bus_favour_record(人情管理-人情记录表)】的数据库操作Mapper
 * @createDate 2024-09-20 22:36:16
 * @Entity com.jh.cavy.manage.domain.TBusFavourRecord
 */
public interface FavourRecordMapper extends BaseMapper<FavourRecord> {

    Page<FavourInoutPageVO> inoutPage(Page<FavourInoutAO> favourInoutAOPage, @Param(Constants.WRAPPER) LambdaQueryWrapper<FavourRecord> queryWrapper);

    List<FavourRecord> inoutHead(@Param(Constants.WRAPPER) LambdaQueryWrapper<FavourRecord> queryWrapper);
}




