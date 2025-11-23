package com.jh.cavy.workflow.api.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class TaskContext<T> {
    private String txnType;
    private String stepNo;
    private OperateType operateType;
    private TradeDTO<T> tradeDTO;
    private Map<String, Object> params;
    private Map<String, Object> results;
    private boolean success;
    private String errorMessage;

    public TaskContext(TradeDTO<T> tradeDTO) {
        this.txnType = tradeDTO.getTxnType();
        this.stepNo = tradeDTO.getStepNo();
        this.operateType = tradeDTO.getOperateType();
        this.tradeDTO = tradeDTO;
        this.params = new HashMap<>();
        this.results = new HashMap<>();

        // 将扩展参数合并到params中
        if (tradeDTO.getExtParams() != null) {
            this.params.putAll(tradeDTO.getExtParams());
        }
    }

    // 便捷方法：获取流程数据
    public FlowData getFlowData() {
        return tradeDTO.getFlowData();
    }

    // 便捷方法：获取流程扩展数据
    public Object getFlowExtData(String key) {
        if (tradeDTO.getFlowData() != null && tradeDTO.getFlowData().getExtFlowData() != null) {
            return tradeDTO.getFlowData().getExtFlowData().get(key);
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