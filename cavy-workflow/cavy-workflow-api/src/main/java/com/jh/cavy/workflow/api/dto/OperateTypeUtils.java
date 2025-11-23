//package com.jh.cavy.workflow.api.dto;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class OperateTypeUtils {
//
//    /**
//     * 安全的枚举转换
//     */
//    public static OperateType parse(String operateTypeStr) {
//        return parse(operateTypeStr, null);
//    }
//
//    /**
//     * 安全的枚举转换，带默认值
//     */
//    public static OperateType parse(String operateTypeStr, OperateType defaultValue) {
//        if (operateTypeStr == null || operateTypeStr.trim().isEmpty()) {
//            return defaultValue;
//        }
//
//        // 尝试多种方式转换
//        OperateType result;
//
//        // 1. 尝试按 code 转换（不区分大小写）
//        result = OperateType.fromCodeFast(operateTypeStr);
//        if (result != null) {
//            return result;
//        }
//
//        // 2. 尝试按 description 转换
//        result = OperateType.fromDescriptionFast(operateTypeStr);
//        if (result != null) {
//            return result;
//        }
//
//        // 3. 尝试严格匹配枚举名称
//        try {
//            result = OperateType.valueOf(operateTypeStr.toUpperCase());
//            return result;
//        } catch (IllegalArgumentException e) {
//            return defaultValue;
//        }
//    }
//
//    /**
//     * 检查字符串是否为有效的操作类型
//     */
//    public static boolean isValid(String operateTypeStr) {
//        return parse(operateTypeStr) != null;
//    }
//
//    /**
//     * 获取所有操作类型的 code 列表
//     */
//    public static List<String> getAllCodes() {
//        return Arrays.stream(OperateType.values())
//                       .map(OperateType::getCode)
//                       .collect(Collectors.toList());
//    }
//
//    /**
//     * 获取所有操作类型的描述列表
//     */
//    public static List<String> getAllDescriptions() {
//        return Arrays.stream(OperateType.values())
//                       .map(OperateType::getDescription)
//                       .collect(Collectors.toList());
//    }
//}