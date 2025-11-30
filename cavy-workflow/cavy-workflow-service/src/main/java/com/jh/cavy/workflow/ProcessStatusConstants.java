package com.jh.cavy.workflow;

public class ProcessStatusConstants {
    /** 活跃状态 - 流程正在运行 */
    public static final String ACTIVE = "ACTIVE";

    /** 已完成 - 流程正常结束 */
    public static final String COMPLETED = "COMPLETED";

    /** 已挂起 - 流程暂停执行 */
    public static final String SUSPENDED = "SUSPENDED";

    /** 已终止 - 流程被手动终止 */
    public static final String TERMINATED = "TERMINATED";

    /** 已删除 - 流程实例已被删除 */
    public static final String DELETED = "DELETED";

    /** 不存在 - 流程实例不存在 */
    public static final String NOT_FOUND = "NOT_FOUND";
}