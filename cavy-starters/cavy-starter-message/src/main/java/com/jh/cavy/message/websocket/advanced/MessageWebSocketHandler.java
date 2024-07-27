package com.jh.cavy.message.websocket.advanced;

import cn.hutool.json.JSONUtil;
import com.jh.cavy.common.Resquest.RequestHeadHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MessageWebSocketHandler implements WebSocketHandler {
    private static final Map<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();
    private static final Map<String, WebSocketSession> ACCOUNT_SESSIONS = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userName = "未知用户";
        String account = "未知用户";
        if (session.getAttributes().get("userName") != null) {
            userName = session.getAttributes().get("userName").toString();
            SESSIONS.put(userName, session);
            account = session.getAttributes().get("account").toString();
            ACCOUNT_SESSIONS.put(account, session);
        }
        log.info("==>用户[{}]成功建立{}连接", userName, MessageWebSocketHandler.class.getName());
    }

    @Override
    public void handleMessage(@NonNull WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String msg = message.getPayload().toString();
        log.info("==>收到消息:{}", msg);
    }

    @Override
    public void handleTransportError(WebSocketSession session, @NonNull Throwable exception) throws Exception {
        log.error("==>连接出错");
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus closeStatus) throws Exception {
        log.info("==>连接已关闭,状态码:{}", closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 指定发消息
     *
     * @param message
     */
    public static void sendMessage(String userName, String message,String bussType) {
        if (!SESSIONS.containsKey(RequestHeadHolder.getRealName())) return;
        WebSocketSession webSocketSession = SESSIONS.get(userName);
        if (webSocketSession == null || !webSocketSession.isOpen()) return;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("bussType", bussType);
            map.put("content", message);
            webSocketSession.sendMessage(new TextMessage(JSONUtil.toJsonStr(map)));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    /**
     * 指定发消息
     *
     * @param message
     */
    public static void sendMessageToAccount(String account, String message,String bussType) {
        if (account==null) {
            throw new NullPointerException();
        };
        WebSocketSession webSocketSession = ACCOUNT_SESSIONS.get(account);
        if (webSocketSession == null || !webSocketSession.isOpen()) return;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("bussType", bussType);
            map.put("content", message);
            webSocketSession.sendMessage(new TextMessage(JSONUtil.toJsonStr(map)));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    /**
     * 给当前用户发消息
     *
     * @param message
     */
    public static void sendMessageToAccount(String message,String bussType) {
        if (RequestHeadHolder.getAccount() == null || !SESSIONS.containsKey(RequestHeadHolder.getAccount())) return;
        WebSocketSession webSocketSession = ACCOUNT_SESSIONS.get(RequestHeadHolder.getAccount());
        if (webSocketSession == null || !webSocketSession.isOpen()) return;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("bussType", bussType);
            map.put("content", message);
            webSocketSession.sendMessage(new TextMessage(JSONUtil.toJsonStr(map)));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    /**
     * 给当前用户发消息
     *
     * @param message
     */
    public static void sendMessage(String message) {
        if (RequestHeadHolder.getRealName() == null || !SESSIONS.containsKey(RequestHeadHolder.getRealName())) return;
        WebSocketSession webSocketSession = SESSIONS.get(RequestHeadHolder.getRealName());
        if (webSocketSession == null || !webSocketSession.isOpen()) return;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("bussType", "default");
            map.put("content", message);
            webSocketSession.sendMessage(new TextMessage(JSONUtil.toJsonStr(map)));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    /**
     * 给当前用户发消息
     *
     * @param message
     */
    public static void sendMessage(String message,String bussType) {
        if (RequestHeadHolder.getRealName() == null || !SESSIONS.containsKey(RequestHeadHolder.getRealName())) return;
        WebSocketSession webSocketSession = SESSIONS.get(RequestHeadHolder.getRealName());
        if (webSocketSession == null || !webSocketSession.isOpen()) return;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("bussType", bussType);
            map.put("content", message);
            webSocketSession.sendMessage(new TextMessage(JSONUtil.toJsonStr(map)));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 群发消息
     *
     * @param message
     */
    public static void fanoutMessage(String message,String bussType) {
        SESSIONS.keySet().forEach(us -> sendMessage(us, message,bussType));
    }
}
