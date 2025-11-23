package com.jh.cavy.workflow.core;

import com.jh.cavy.workflow.api.dto.TaskContext;

// 任务节点接口
public interface TaskNode<T>{

    /**
     * 节点初始化
     */
    default void init(TaskContext<T> context) {
        // 默认空实现
    }

    /**
     * 数据加载
     */
    default void load(TaskContext<T> context) {
        // 默认空实现
    }

    /**
     * 提交前校验
     */
    default boolean beforeCommitValidate(TaskContext<T> context) {
        return true;
    }

    /**
     * 提交前处理
     */
    default void beforeCommit(TaskContext<T> context) {
        // 默认空实现
    }

    /**
     * 提交处理
     */
    default void commit(TaskContext<T> context) {
        // 默认空实现
    }

    /**
     * 完成处理
     */
    default void finish(TaskContext<T> context) {
        // 默认空实现
    }

    /**
     * 取消处理
     */
    default void cancel(TaskContext<T> context) {
        // 默认空实现
    }

    /**
     * 审批处理
     */
    default void approve(TaskContext<T> context) {
        // 默认空实现
    }

    /**
     * 拒绝处理
     */
    default void reject(TaskContext<T> context) {
        // 默认空实现
    }

}
