package az.eurodesign.notification.processing.scheduler;

import az.eurodesign.notification.processing.dto.ActionToPerform;
import az.eurodesign.notification.processing.dto.ActiveNotificationExtender;
import az.eurodesign.notification.processing.model.ActiveNotification;
import az.eurodesign.notification.processing.model.enums.NotificationType;
import az.eurodesign.notification.processing.model.superclasses.Notification;
import az.eurodesign.notification.processing.repository.ActiveNotificationBufferedReader;
import az.eurodesign.notification.processing.repository.ActiveNotificationRepository;
import az.eurodesign.notification.processing.repository.ArchivedNotificationRepository;
import az.eurodesign.notification.processing.service.SenderService;
import az.eurodesign.notification.processing.util.NotificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Component
@Transactional
@ConditionalOnProperty(name = "application.enable-schedulers")
@RequiredArgsConstructor
public class ProcessingScheduler {

    private final ActiveNotificationRepository activeNotificationRepository;

    private final ActiveNotificationBufferedReader activeNotificationBufferedReader;

    @Qualifier("smsSenderService")
    private final SenderService smsSenderService;

    private final ArchivedNotificationRepository archivedNotificationRepository;

    @Scheduled(fixedDelayString = "${application.processor-scheduler-fixed-delay}")
    public void process() {
        save(process(activeNotificationBufferedReader.read()));
    }

    private List<ActiveNotificationExtender> process(List<ActiveNotification> notifications) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        List<Future<List<ActiveNotificationExtender>>> futures = new ArrayList<>();
        List<ActiveNotificationExtender> result = new ArrayList<>();

        Map<NotificationType, List<ActiveNotification>> map = notifications.stream()
                .collect(Collectors.groupingBy(Notification::getType));

        if (map.containsKey(NotificationType.SMS)) {
            futures.add(executor.submit(() -> smsSenderService.send(map.get(NotificationType.SMS))));

        }


        futures.forEach(x -> {
            try {
                result.addAll(x.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();
        return result;
    }

    private void save(List<ActiveNotificationExtender> result) {
        Map<ActionToPerform, List<ActiveNotificationExtender>> actionMap = result.stream()
                .collect(Collectors.groupingBy(ActiveNotificationExtender::getActionToPerform));

        if (actionMap.containsKey(ActionToPerform.SAVE)) {
            activeNotificationRepository.saveAll(
                    actionMap.get(ActionToPerform.SAVE).stream()
                            .map(ActiveNotificationExtender::getActiveNotification)
                            .collect(Collectors.toList())
            );
        }

        if (actionMap.containsKey(ActionToPerform.ARCHIVE)) {
            List<ActiveNotification> list = actionMap.get(ActionToPerform.ARCHIVE).stream()
                    .map(ActiveNotificationExtender::getActiveNotification)
                    .collect(Collectors.toList());
            activeNotificationRepository.deleteAll(list);
            archivedNotificationRepository.saveAll(NotificationUtil.archive(list));
        }
    }
}
