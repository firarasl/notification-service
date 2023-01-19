package az.eurodesign.notification.processing.model;

import az.eurodesign.notification.processing.model.superclasses.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "active_notifications")
@Getter @Setter @NoArgsConstructor
@ToString
public class ActiveNotification extends Notification {
}
