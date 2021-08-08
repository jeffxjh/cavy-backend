package com.jh.cavy.manage.service;

import com.jh.cavy.manage.domain.Answer;
import com.jh.cavy.manage.vo.AnswerVO;
import com.jh.cavy.common.Result.ResultPage;

public interface AnswerService {


    int deleteByPrimaryKey(Integer id);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKey(Answer record);

    ResultPage<AnswerVO> findAnswerByQuestionId(String questionId, Long pageIndex, Long pageSize);
}

