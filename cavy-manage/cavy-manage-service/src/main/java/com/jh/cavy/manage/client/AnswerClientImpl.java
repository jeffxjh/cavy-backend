package com.jh.cavy.manage.client;

import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.common.utils.classScan.ClassScanUtil;
import com.jh.cavy.manage.api.AnswerClient;
import com.jh.cavy.manage.service.AnswerService;
import com.jh.cavy.manage.vo.AnswerVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Slf4j
@Service
public record AnswerClientImpl(AnswerService answerService) implements AnswerClient {


    @Override
    public ResultPage<AnswerVO> findAnswerByQuestionId(String questionId, Long pageIndex, Long pageSize) {
        return answerService.findAnswerByQuestionId(questionId, pageIndex, pageSize);
    }

    @SneakyThrows
    @Override
    public void testClassLoad() {
        Set<Class<?>> classByAnnotate = ClassScanUtil.getClassByAnnotateInMethod("com.jh.cavy.manage", GetMapping.class);
        for (Class<?> aClass : classByAnnotate) {
            log.info(aClass.getName());
        }
    }
}


