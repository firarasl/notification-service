package az.eurodesign.notification.processing.model;

import az.eurodesign.notification.processing.model.superclasses.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "archived_notifications")
@Getter @Setter @NoArgsConstructor
public class ArchivedNotification  extends Notification {
}
