package com.jh.cavymanage.service;

import com.jh.cavymanage.domain.Question;
import com.jh.cavymanage.vo.QuestionVO;
import com.jh.cavycore.common.Result.ResultPage;
import com.jh.cavymanage.param.QuestionParam;

public interface QuestionService{


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
