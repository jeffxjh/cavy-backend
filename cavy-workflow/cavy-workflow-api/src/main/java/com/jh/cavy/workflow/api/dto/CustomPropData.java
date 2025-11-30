package com.jh.cavy.workflow.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomPropData implements Serializable {
    private Map<String,String> processProps;
    private Map<String,String> activityProps;
}
