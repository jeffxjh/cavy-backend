package com.jh.cavy.manage.api;

import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.vo.AnswerVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/answer")
@RestController
@FeignClient(name = "cavy-manage", contextId = "answerClient", path = "answer")
public interface AnswerClient {


    @GetMapping
    ResultPage<AnswerVO> findAnswerByQuestionId(@RequestParam("questionId") String questionId, @RequestParam("pageIndex") Long pageIndex, @RequestParam("pageSize") Long pageSize);

    @GetMapping("/testclassload")
    void testClassLoad();
}
