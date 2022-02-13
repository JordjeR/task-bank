package com.homework.task.ui.util;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class Notice {

    private Notification notification;

    public Notice() {
    }

    public Notice(Notification notification) {
        this.notification = notification;
    }

    public void setSuccessNotice(String text) {
        Notification notice = Notification.show(text);
        notice.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notice.setPosition(Notification.Position.TOP_END);
    }

    public void setFailedNotice(String text) {
        Notification notice = Notification.show(text);
        notice.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notice.setPosition(Notification.Position.TOP_CENTER);
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
