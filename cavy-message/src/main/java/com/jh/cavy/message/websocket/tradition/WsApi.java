package com.jh.cavy.message.websocket.tradition;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

//@RestController
//@RequestMapping("api")
public class WsApi {
    @Resource
    @Qualifier(value = "WebSocketTradition")
    private WebSocket webSocket;

    @RequestMapping("/sendAllWebSocket")
    public String test() {
        webSocket.sendAllMessage("清晨起来打开窗，心情美美哒~");
        return "websocket群体发送！";
    }

    @RequestMapping("/sendOneWebSocket")
    public String sendOneWebSocket() {
        webSocket.sendOneMessage("DPS007", "只要你乖给你买条gai！");
        return "websocket单人发送";
    }
}
