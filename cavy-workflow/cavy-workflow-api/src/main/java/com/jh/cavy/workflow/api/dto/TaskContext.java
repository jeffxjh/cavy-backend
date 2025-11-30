package com.jh.cavy.workflow.api.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class TaskContext<T> {
    /**
     * 交易类型 流程key
     */
    private String txnType;
    /**
     * 步骤号 节点编码
     */
    private String stepNo;

    /**
     *
     *@see com.jh.cavy.workflow.api.dto.OperateType
     */
    private OperateType operateType;
    /**
     * 交易业务数据
     */
    private TradeData<T> tradeData;
    /**
     * 流程配置数据
     */
    private Map<String, Object> flowConfig;
    private Map<String, Object> params;
    private Map<String, Object> results;
    private boolean success;
    private String errorMessage;

    public TaskContext(TradeData<T> tradeData) {
        this.txnType = tradeData.getTxnType();
        this.stepNo = tradeData.getStepNo();
        this.operateType = tradeData.getOperateType();
        this.tradeData = tradeData;
        this.params = new HashMap<>();
        this.results = new HashMap<>();

        // 将扩展参数合并到params中
        if (tradeData.getExtParams() != null) {
            this.params.putAll(tradeData.getExtParams());
        }
    }

    public FlowData getFlowData() {
        return tradeData.getFlowData();
    }
    public String getTxnCode() {
        return tradeData.getTxnCode();
    }

    public Object getFlowExtData(String key) {
        if (tradeData.getFlowData() != null && tradeData.getFlowData().getExtFlowData() != null) {
            return tradeData.getFlowData().getExtFlowData().get(key);
        }
        return null;
    }

    public void putParam(String key, Object value) {
        params.put(key, value);
    }

    public Object getParam(String key) {
        return params.get(key);
    }

    public void putResult(String key, Object value) {
        results.put(key, value);
    }

    public Object getResult(String key) {
        return results.get(key);
    }
}