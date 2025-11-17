package com.jh.cavy.manage.service;

import com.jh.cavy.manage.domain.Question;
import com.jh.cavy.manage.vo.QuestionVO;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.param.QuestionParam;

public interface QuestionService {


    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);

    ResultPage<QuestionVO> questionVOList(QuestionParam questionParam);


    String update(QuestionParam questionParam);

    void addQuestion(QuestionParam questionParam);
}

