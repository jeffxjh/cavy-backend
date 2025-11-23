package com.jh.cavy.workflow.core;

// 步骤号与类名映射规则
public class NodeNameConverter {

    /**
     * 根据步骤号生成对应的类名
     * 例如：步骤号 N0000 -> 类名 OrderN0000
     *       步骤号 V0001 -> 类名 OrderV0001
     */
    public static String getNodeClassName(String stepNo) {
        return "Order" + stepNo;
    }

    /**
     * 根据步骤号生成对应的Bean名称
     */
    public static String getNodeBeanName(String stepNo) {
        return "order" + stepNo.toLowerCase() + "Node";
    }
}