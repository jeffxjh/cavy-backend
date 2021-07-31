package com.jh.cavymanage.api;

import com.jh.cavymanage.service.AnswerService;
import com.jh.cavymanage.vo.AnswerVO;
import com.jh.cavymanage.web.Result.ResultPage;
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
