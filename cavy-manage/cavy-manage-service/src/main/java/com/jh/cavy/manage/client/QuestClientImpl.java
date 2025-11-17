package com.jh.cavy.manage.client;

import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.api.QuestClient;
import com.jh.cavy.manage.param.QuestionParam;
import com.jh.cavy.manage.service.QuestionService;
import com.jh.cavy.manage.vo.QuestionVO;
import org.springframework.stereotype.Service;

@Service
public record QuestClientImpl(QuestionService questionService) implements QuestClient {
    @Override
    public ResultPage<QuestionVO> questionList(QuestionParam questionParam) {
        return questionService.questionVOList(questionParam);
    }

    @Override
    public void addQuestion(QuestionParam questionParam) {
        questionService.addQuestion(questionParam);
    }

    @Override
    public String update(QuestionParam questionParam) {
        return questionService.update(questionParam);
    }
}
