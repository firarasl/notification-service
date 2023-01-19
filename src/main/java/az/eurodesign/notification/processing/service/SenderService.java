package az.eurodesign.notification.processing.service;

import az.eurodesign.notification.processing.dto.ActiveNotificationExtender;
import az.eurodesign.notification.processing.model.ActiveNotification;

import java.util.List;

public interface SenderService {
    List<ActiveNotificationExtender> send(List<ActiveNotification> notifications);
}