package az.eurodesign.notification.processing.model.superclasses;


import az.eurodesign.notification.processing.model.enums.NotificationStatus;
import az.eurodesign.notification.processing.model.enums.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor
public class Notification extends Base {

    @Column(name = "text")
    private String text;

    @Column(name = "link")
    private String link;

    @Column(name = "uid")
    private String uid;

    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "report")
    private String report;

    @Column(name = "count_of_sending", nullable = false)
    private long countOfSending;

    @Column(name = "max_count_of_sending", nullable = false)
    private long maxCountOfSending;

    @Column(name = "target")
    private String target;

    @Column(name = "watched")
    private Boolean watched;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotificationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;

    @Column(name = "scheduled_date_time")
    private LocalDateTime scheduledDateTime;

    @Column(name="sent_date_time")
    private LocalDateTime sentDateTime;

    @Column(name="taskId")
    private String taskId;

}
