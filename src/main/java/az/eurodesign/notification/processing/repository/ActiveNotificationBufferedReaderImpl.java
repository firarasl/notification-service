package az.eurodesign.notification.processing.repository;


import az.eurodesign.notification.processing.configuration.ApplicationConfiguration;
import az.eurodesign.notification.processing.model.ActiveNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ActiveNotificationBufferedReaderImpl implements ActiveNotificationBufferedReader {

    private final EntityManager entityManager;

    private final ApplicationConfiguration applicationConfiguration;

    @Autowired
    public ActiveNotificationBufferedReaderImpl(EntityManager entityManager,
                                                ApplicationConfiguration applicationConfiguration) {
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public List<ActiveNotification> read() {

        return entityManager.createQuery(
                "SELECT a FROM ActiveNotification a " +
                        "WHERE a.scheduledDateTime <= :datetime OR a.scheduledDateTime IS NULL " +
                        "ORDER BY a.scheduledDateTime NULLS FIRST",
                ActiveNotification.class
            )
            .setParameter("datetime", LocalDateTime.now())
            .setMaxResults(applicationConfiguration.getBufferSize())
            .getResultList();
    }
}
