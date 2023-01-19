package az.eurodesign.notification.processing.repository;

import az.eurodesign.notification.processing.model.ActiveNotification;

import java.util.List;

public interface ActiveNotificationBufferedReader {
    List<ActiveNotification> read();
}