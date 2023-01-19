package az.eurodesign.notification.processing.dto;

import az.eurodesign.notification.processing.model.ActiveNotification;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ActiveNotificationDTO {

    private Long id;

    private String text;

    private String link;

    private String uid;

    public ActiveNotificationDTO(ActiveNotification activeNotification) {
        this.id = activeNotification.getId();
        this.text = activeNotification.getText();
        this.link = activeNotification.getLink();
        this.uid = activeNotification.getUid();
    }
}
