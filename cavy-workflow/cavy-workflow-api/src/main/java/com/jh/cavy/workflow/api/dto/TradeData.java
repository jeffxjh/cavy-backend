package com.jh.cavy.workflow.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class TradeData<T> implements Serializable {
    /**
     * 交易类型
     */
    private String txnType;

    /**
     * 步骤编号
     */
    private String stepNo;

    /**
     * 操作类型
     */
    private OperateType operateType;
    /**
     * 业务动作类型
     */
    private BusinessAction businessAction;

    /**
     * 流程数据
     */
    private FlowData flowData;

    /**
     * 表单数据
     */
    private T formData;

    /**
     * 扩展参数
     */
    private Map<String, Object> extParams;

    public String getTxnCode() {
        return flowData.getTxnCode();
    }
    /**
     * 获取完整的操作描述
     */
    public String getFullOperation() {
        if (operateType != null && businessAction != null) {
            return businessAction.getDescription() + operateType.getDescription();
        } else if (operateType != null) {
            return operateType.getDescription();
        } else if (businessAction != null) {
            return businessAction.getDescription();
        }
        return "未知操作";
    }
}
