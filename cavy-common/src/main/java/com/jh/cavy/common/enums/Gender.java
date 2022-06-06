package com.jh.cavy.common.enums;

public class Gender extends BaseEnum<Integer> {

    public Gender(Integer value, String desc) {
        super(value, desc);
    }
    public static Gender MALE = new Gender(1, "男");
    public static Gender FEMALE = new Gender(2, "女");



}
