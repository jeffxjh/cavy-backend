package com.jh.cavy.common.event;

import java.util.EventListener;

public class SocialEventListener implements EventListener {
    public void onSocialChanged(SocialEvent event) {
        switch (event.getSocialType()) {
            case SocialEvent.WECHAT_BIND:
                System.out.println("Wechat bind.");
                break;
            case SocialEvent.WECHAT_UNBIND:
                System.out.println("Wechat unbind.");
                break;
            case SocialEvent.WEIBO_BIND:
                System.out.println("Weibo bind.");
                break;
            case SocialEvent.WEIBO_UNBIND:
                System.out.println("Weibo unbind.");
                break;
            default:
                System.out.println("Bad social type.");
                break;
        }
    }
}