package com.jh.cavy.workflow.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum OperateType {
    LOAD("load", "加载"),
    COMMIT("commit", "提交"),
    SAVE("save", "保存"),
    CANCEL("cancel", "取消"),
    APPROVE("approve", "审批"),
    REJECT("reject", "拒绝");

    private final String code;
    @Getter
    private final String description;

    OperateType(String code, String description) {
        this.code = code;
        this.description = description;
    }
    // JSON 反序列化
    @JsonCreator
    public static OperateType fromCode(String code) {
        for (OperateType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的操作类型: " + code);
    }
    // JSON 序列化
    @JsonValue
    public String getCode() {
        return code;
    }

    private static final Map<String, OperateType> CODE_MAP = new HashMap<>();
    private static final Map<String, OperateType> DESCRIPTION_MAP = new HashMap<>();

    static {
        // 初始化缓存
        for (OperateType type : values()) {
            CODE_MAP.put(type.code.toLowerCase(), type);
            DESCRIPTION_MAP.put(type.description, type);
        }
    }

    /**
     * 根据 code 获取枚举（高性能版本）
     */
    public static OperateType fromCodeFast(String code) {
        if (code == null) {
            return null;
        }
        return CODE_MAP.get(code.toLowerCase());
    }

    /**
     * 根据 description 获取枚举（高性能版本）
     */
    public static OperateType fromDescriptionFast(String description) {
        return DESCRIPTION_MAP.get(description);
    }
}