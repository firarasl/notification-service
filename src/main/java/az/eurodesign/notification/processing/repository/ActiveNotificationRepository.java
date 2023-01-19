package az.eurodesign.notification.processing.repository;

import az.eurodesign.notification.processing.model.ActiveNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveNotificationRepository extends JpaRepository<ActiveNotification, Long> {

}
