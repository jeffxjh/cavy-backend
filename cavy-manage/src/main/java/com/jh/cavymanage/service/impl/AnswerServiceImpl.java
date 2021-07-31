package com.jh.cavymanage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavymanage.domain.Answer;
import com.jh.cavymanage.mapper.AnswerMapper;
import com.jh.cavymanage.service.AnswerService;
import com.jh.cavymanage.vo.AnswerVO;
import com.jh.cavymanage.web.Result.ResultPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Resource
    private AnswerMapper answerMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return answerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Answer record) {
        return answerMapper.insert(record);
    }

    @Override
    public int insertSelective(Answer record) {
        return answerMapper.insertSelective(record);
    }

    @Override
    public Answer selectByPrimaryKey(Integer id) {
        return answerMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Answer record) {
        return answerMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Answer record) {
        return answerMapper.updateByPrimaryKey(record);
    }

    @Override
    public ResultPage<AnswerVO> findAnswerByQuestionId(String questionId, Long pageIndex, Long pageSize) {

        LambdaQueryWrapper<Answer> lambdaQuery = Wrappers.lambdaQuery(Answer.class);
        lambdaQuery.eq(StrUtil.isNotEmpty(questionId), Answer::getQuestionId, questionId);
        Page<AnswerVO> questionPage = answerMapper.findAnswerByQuestionId(new Page<>(pageIndex, pageSize), lambdaQuery);
        return new ResultPage<>(questionPage);
    }

}

