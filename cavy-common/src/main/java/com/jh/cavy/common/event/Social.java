package com.jh.cavy.common.event;

import java.util.ArrayList;
import java.util.List;

public class Social {

    private List<SocialEventListener> listeners;

    public void addListener(SocialEventListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    public void removeListener(SocialEventListener listener) {
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    public void emitEvent(SocialEvent event) {
        for (SocialEventListener listener : listeners) {
            listener.onSocialChanged(event);
        }
    }

    public List<SocialEventListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<SocialEventListener> listeners) {
        this.listeners = listeners;
    }
}