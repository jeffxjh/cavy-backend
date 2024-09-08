package com.jh.cavy.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.manage.domain.Question;
import com.jh.cavy.manage.mapper.QuestionMapper;
import com.jh.cavy.manage.service.QuestionService;
import com.jh.cavy.manage.vo.QuestionVO;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.param.QuestionParam;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionMapper questionMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return questionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Question record) {
        return questionMapper.insert(record);
    }

    @Override
    public int insertSelective(Question record) {
        return questionMapper.insertSelective(record);
    }

    @Override
    public Question selectByPrimaryKey(Integer id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Question record) {
        return questionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Question record) {
        return questionMapper.updateByPrimaryKey(record);
    }

    @Override
    @SuppressWarnings({"serial", "unchecked"})
    public ResultPage<QuestionVO> questionVOList(QuestionParam questionParam) {
        LambdaQueryWrapper<Question> lambdaQuery = Wrappers.lambdaQuery(Question.class);
        lambdaQuery.eq(StrUtil.isNotEmpty(questionParam.getTitle()), Question::getTitle, questionParam.getTitle());
        lambdaQuery.orderBy(true, false, Question::getAddTime, Question::getId);
        Page<QuestionVO> questionPage = questionMapper.questionVOList(new Page<>(questionParam.getPageIndex(), questionParam.getPageSize()), lambdaQuery);
        return new ResultPage<>(questionPage);
    }

    @Override
    public String update(QuestionParam questionParam) {
        LambdaUpdateWrapper<Question> lambdaUpdate = Wrappers.lambdaUpdate(Question.class);
        lambdaUpdate.eq(Question::getId, questionParam.getId());
        Question question = new Question();
        question.setTitle(questionParam.getTitle());
        question.setContent(questionParam.getContent());
        return questionMapper.update(question, lambdaUpdate) + "";
    }

    @Override
    public void addQuestion(QuestionParam questionParam) {
        Question question = BeanUtil.copyProperties(questionParam, Question.class);
        questionMapper.insert(question);
    }

}

