package com.jh.cavymanage.api;

import com.jh.cavymanage.service.QuestionService;
import com.jh.cavymanage.vo.QuestionVO;
import com.jh.cavymanage.web.Result.ResultPage;
import com.jh.cavymanage.web.param.QuestionParam;
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
