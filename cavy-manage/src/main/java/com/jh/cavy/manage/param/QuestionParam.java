package com.jh.cavy.manage.param;

import com.jh.cavy.common.Resquest.BaseParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionParam extends BaseParam {
    private Long id;
    private String title;
    private String content;


}
