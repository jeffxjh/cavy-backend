package com.jh.cavy.manage.api;

import com.jh.cavy.manage.service.QuestionService;
import com.jh.cavy.manage.vo.QuestionVO;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.param.QuestionParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RequestMapping("/question")
@RestController
public class QuestApi {
    @Resource
    private QuestionService questionService;

    @GetMapping()
    public ResultPage<QuestionVO> questionList(@ModelAttribute QuestionParam questionParam) {
        return questionService.questionVOList(questionParam);
    }

    @PostMapping()
    public void addQuestion(@RequestBody QuestionParam questionParam) {
        questionService.addQuestion(questionParam);
    }

    @PutMapping("")
    public String update(@RequestBody QuestionParam questionParam) {
        return questionService.update(questionParam);
    }
}
