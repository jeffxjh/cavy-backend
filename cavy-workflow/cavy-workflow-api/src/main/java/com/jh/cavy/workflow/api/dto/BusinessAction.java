package com.jh.cavy.workflow.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务动作类型枚举
 */
public enum BusinessAction {
    CREATE("create", "创建"),
    UPDATE("update", "修改"),
    DELETE("delete", "删除"),
    QUERY("query", "查询"),
    IMPORT("import", "导入"),
    EXPORT("export", "导出"),
    VIEW("view", "查看"),
    AUDIT("audit", "审核"),
    PUBLISH("publish", "发布"),
    CANCEL("cancel", "作废");

    private final String code;
    private final String description;

    BusinessAction(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator
    public static BusinessAction fromCode(String code) {
        for (BusinessAction action : values()) {
            if (action.code.equalsIgnoreCase(code)) {
                return action;
            }
        }
        throw new IllegalArgumentException("无效的业务动作: " + code);
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    private static final Map<String, BusinessAction> CODE_MAP = new HashMap<>();

    static {
        for (BusinessAction action : values()) {
            CODE_MAP.put(action.code.toLowerCase(), action);
        }
    }

    public static BusinessAction fromCodeFast(String code) {
        if (code == null) {
            return null;
        }
        return CODE_MAP.get(code.toLowerCase());
    }

    /**
     * 判断是否为数据操作动作
     */
    public boolean isDataOperation() {
        return this == CREATE || this == UPDATE || this == DELETE || this == QUERY;
    }

    /**
     * 判断是否为业务处理动作
     */
    public boolean isBusinessProcess() {
        return this == IMPORT || this == EXPORT || this == AUDIT || this == PUBLISH;
    }
}