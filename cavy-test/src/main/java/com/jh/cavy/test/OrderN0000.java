package com.jh.cavy.test;

import com.jh.cavy.workflow.api.dto.TaskContext;
import com.jh.cavy.workflow.core.TaskNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

// 步骤号 N0000 对应的节点实现
@Component // 指定Bean名称
@Slf4j
public class OrderN0000 implements TaskNode<OrderDTO> {


    @Override
    public void init(TaskContext<OrderDTO> context) {
        log.info("N0000节点初始化 - 订单信息校验");
    }

    @Override
    public void load(TaskContext<OrderDTO> context) {
        log.info("N0000节点加载");
        OrderDTO formData = new OrderDTO();
        formData.setApplicant("张站");
        formData.setReason("成功了");
        context.getTradeData().setFormData(formData);

    }

    @Override
    public boolean beforeCommitValidate(TaskContext<OrderDTO> context) {
        String orderNo = (String) context.getParam("orderNo");

        if (StringUtils.isBlank(orderNo)) {
            context.setErrorMessage("N0000: 订单号不能为空");
            return false;
        }

        // 检查订单格式
        if (!orderNo.matches("ORD\\d{8}")) {
            context.setErrorMessage("N0000: 订单号格式不正确");
            return false;
        }

        return true;
    }

    @Override
    public void commit(TaskContext<OrderDTO> context) {
        log.info("N0000节点提交 - 创建订单基本信息");
        // 创建订单基础信息逻辑
        context.putResult("step", "N0000");
        context.putResult("status", "SUCCESS");
    }


}