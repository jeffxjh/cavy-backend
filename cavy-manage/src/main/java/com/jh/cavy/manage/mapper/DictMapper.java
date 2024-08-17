package com.jh.cavy.manage.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.manage.domain.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jh.cavy.manage.domain.User;
import com.jh.cavy.manage.param.UserParam;
import com.jh.cavy.manage.vo.DictStoreVO;
import com.jh.cavy.manage.vo.DictVO;
import com.jh.cavy.manage.vo.QuestionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author jeffx
* @description 针对表【t_sys_dict(数据字典表)】的数据库操作Mapper
* @createDate 2024-08-11 17:50:14
* @Entity com.jh.cavy.manage.domain.Dict
*/
public interface DictMapper extends BaseMapper<Dict> {

    Page<DictVO> queryDictPage(Page<Dict> page, @Param(Constants.WRAPPER)LambdaQueryWrapper<Dict> queryWrapper);

    List<DictStoreVO> store(@Param(Constants.WRAPPER)LambdaQueryWrapper<Dict> queryWrapper);
}




