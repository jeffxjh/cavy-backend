package com.jh.cavymanage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavymanage.domain.Answer;
import com.jh.cavymanage.vo.AnswerVO;
import org.apache.ibatis.annotations.Param;

public interface AnswerMapper extends BaseMapper<Answer> {
    int deleteByPrimaryKey(Integer id);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKey(Answer record);

    Page<AnswerVO> findAnswerByQuestionId(Page<AnswerVO> objectPage, @Param(Constants.WRAPPER) Wrapper<Answer> lambdaQuery);
}