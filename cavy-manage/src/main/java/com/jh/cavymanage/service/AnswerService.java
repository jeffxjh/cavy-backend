package com.jh.cavymanage.service;

import com.jh.cavymanage.domain.Answer;
import com.jh.cavymanage.vo.AnswerVO;
import com.jh.cavycore.common.Result.ResultPage;

public interface AnswerService {


    int deleteByPrimaryKey(Integer id);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKey(Answer record);

    ResultPage<AnswerVO> findAnswerByQuestionId(String questionId, Long pageIndex, Long pageSize);
}

