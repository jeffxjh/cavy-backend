package com.jh.cavymanage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavymanage.domain.Question;
import com.jh.cavymanage.vo.QuestionVO;
import org.apache.ibatis.annotations.Param;

public interface QuestionMapper extends BaseMapper<Question> {
    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);

    Page<QuestionVO> questionVOList(Page<Question> page, @Param(Constants.WRAPPER) Wrapper<Question> queryWrapper);
}