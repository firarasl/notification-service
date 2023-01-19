package az.eurodesign.notification.processing.repository;

import az.eurodesign.notification.processing.model.ArchivedNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivedNotificationRepository extends JpaRepository<ArchivedNotification, Long> {
}
