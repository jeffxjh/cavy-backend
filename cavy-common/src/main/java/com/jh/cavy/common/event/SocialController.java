package com.jh.cavy.common.event;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialController {

    @GetMapping("/event")
    public void event() {
        // 事件源绑定监听器
        Social social = new Social();
        social.addListener(new SocialEventListener());
        // 触发事件
        social.emitEvent(new SocialEvent(social, SocialEvent.WECHAT_UNBIND));
    }
}
