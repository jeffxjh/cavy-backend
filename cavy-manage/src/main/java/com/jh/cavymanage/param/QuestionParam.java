package com.jh.cavymanage.param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionParam extends BaseParam {
    private Long id;
    private String title;
    private String content;


}
