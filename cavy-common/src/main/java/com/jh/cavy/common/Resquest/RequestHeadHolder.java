package com.jh.cavy.common.Resquest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestHeadHolder {
    private final static InheritableThreadLocal<String> account = new InheritableThreadLocal<>();
    private final static InheritableThreadLocal<String> realName = new InheritableThreadLocal<>();
    private final static InheritableThreadLocal<Long> userId = new InheritableThreadLocal<>();

    public static String getAccount() {
        return RequestHeadHolder.account.get();
    }

    public static void setAccount(String account) {
        RequestHeadHolder.account.set(account);
    }
    public static Long getUserId() {
        return RequestHeadHolder.userId.get();
    }
    public static String getUserIdString() {
        return String.valueOf(RequestHeadHolder.userId.get());
    }

    public static void setUserId(Long userId) {
        RequestHeadHolder.userId.set(userId);
    }

    public static void clearAccount() {
        RequestHeadHolder.account.set("");
    }

    public static String getRealName() {
        return RequestHeadHolder.realName.get();
    }

    public static void setRealName(String account) {
        RequestHeadHolder.realName.set(account);
    }

    public static void clearRealName() {
        RequestHeadHolder.realName.set("");
    }
}
