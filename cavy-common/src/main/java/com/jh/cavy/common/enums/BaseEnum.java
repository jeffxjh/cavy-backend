package com.jh.cavy.common.enums;

public abstract class BaseEnum<T> implements EnumAble<T> {
    private T value;
    private String desc;
    public BaseEnum(T value, String desc) {
        this.value = value;
        this.desc = desc;
        EnumFactory.add(this);
    }
    @Override
    public T getValue() {
        return this.value;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }
    //添加这个方法
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[value=" + value + ", desc=" + desc + "]";
    }
}
