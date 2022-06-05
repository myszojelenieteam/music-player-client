package eu.myszojelenie.music.player.client.utils;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {

    private List<Listener> listeners = new ArrayList<>();

    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public void notify(NotificationType notificationType, Object object) {
        listeners.forEach(l -> l.onMessage(notificationType, object));
    }

}
