package com.jh.cavy.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.domain.Answer;
import com.jh.cavy.manage.mapper.AnswerMapper;
import com.jh.cavy.manage.service.AnswerService;
import com.jh.cavy.manage.vo.AnswerVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {

    
    private final AnswerMapper answerMapper;

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


