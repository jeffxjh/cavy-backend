package com.jh.cavy.manage.api;

import com.jh.cavy.manage.vo.QuestionVO;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.param.QuestionParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/question")
@RestController
@FeignClient(name = "cavy-manage", contextId = "questionClient", path = "question")
public interface QuestClient {
    @GetMapping()
    ResultPage<QuestionVO> questionList(@ModelAttribute QuestionParam questionParam);

    @PostMapping()
    void addQuestion(@RequestBody QuestionParam questionParam);

    @PutMapping("")
    String update(@RequestBody QuestionParam questionParam);
}
