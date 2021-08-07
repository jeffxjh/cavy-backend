package com.jh.cavy.manage.api;

import com.jh.cavy.manage.service.AnswerService;
import com.jh.cavy.manage.vo.AnswerVO;
import com.jh.cavy.jwt.common.Result.ResultPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RequestMapping("/answer")
@RestController
public class AnswerApi {
    @Resource
    private AnswerService answerService;

    @GetMapping
    public ResultPage<AnswerVO> findAnswerByQuestionId(@RequestParam String questionId, @RequestParam Long pageIndex,@RequestParam Long pageSize) {
        return answerService.findAnswerByQuestionId(questionId,pageIndex,pageSize);
    }

}
