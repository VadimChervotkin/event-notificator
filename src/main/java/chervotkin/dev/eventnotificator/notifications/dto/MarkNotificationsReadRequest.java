package chervotkin.dev.eventnotificator.notifications.dto;

import java.util.List;

public class MarkNotificationsReadRequest {
    private List<Long> notificationIds;

    public List<Long> getNotificationIds() {
        return notificationIds;
    }

    public void setNotificationIds(List<Long> notificationIds) {
        this.notificationIds = notificationIds;
    }
}

