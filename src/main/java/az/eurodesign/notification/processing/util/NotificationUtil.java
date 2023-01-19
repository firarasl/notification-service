package az.eurodesign.notification.processing.util;

import az.eurodesign.notification.processing.model.ActiveNotification;
import az.eurodesign.notification.processing.model.ArchivedNotification;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationUtil {

    public static List<ArchivedNotification> archive(List<ActiveNotification> activeNotifications) {
        return activeNotifications.stream().map(NotificationUtil::archive).collect(Collectors.toList());
    }

    private static ArchivedNotification archive(ActiveNotification activeNotification) {
        ModelMapper mapper = new ModelMapper();
        ArchivedNotification archivedNotification = mapper.map(activeNotification, ArchivedNotification.class);
        archivedNotification.setId(null);
        return new ModelMapper().map(activeNotification, ArchivedNotification.class);
    }
}
