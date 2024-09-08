package com.jh.cavy.manage.api;

import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.common.utils.classScan.ClassScanUtil;
import com.jh.cavy.manage.service.AnswerService;
import com.jh.cavy.manage.vo.AnswerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.io.IOException;
import java.util.Set;

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

    @GetMapping("/testclassload")
    public void testClassLoad() throws IOException, ClassNotFoundException {
        Set<Class<?>> classByAnnotate = ClassScanUtil.getClassByAnnotateInMethod("com.jh.cavy.manage", GetMapping.class);
        for (Class<?> aClass : classByAnnotate) {
           log.info( aClass.getName());
        }
    }
}
