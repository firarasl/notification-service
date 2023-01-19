package az.eurodesign.notification.processing.dto;

import az.eurodesign.notification.processing.model.ActiveNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter @Setter @AllArgsConstructor
public class ActiveNotificationExtender {

    private ActiveNotification activeNotification;

    private ActionToPerform actionToPerform;

}
