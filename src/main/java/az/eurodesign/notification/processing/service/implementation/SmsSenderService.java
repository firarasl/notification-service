package az.eurodesign.notification.processing.service.implementation;

import az.eurodesign.notification.processing.configuration.ApplicationConfiguration;
import az.eurodesign.notification.processing.dto.ActionToPerform;
import az.eurodesign.notification.processing.dto.ActiveNotificationExtender;
import az.eurodesign.notification.processing.model.ActiveNotification;
import az.eurodesign.notification.processing.model.enums.NotificationStatus;
import az.eurodesign.notification.processing.payload.BulkSmsRequest;
import az.eurodesign.notification.processing.payload.BulksSmsResponse;
import az.eurodesign.notification.processing.payload.SmsReportsRequest;
import az.eurodesign.notification.processing.payload.SmsReportsResponse;
import az.eurodesign.notification.processing.repository.ActiveNotificationRepository;
import az.eurodesign.notification.processing.service.SenderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service("smsSenderService")
@Slf4j
public class SmsSenderService implements SenderService {

    @Autowired
    private ActiveNotificationRepository activeNotificationRepository;

    private final ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();


    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${application.sms.sender.login}")
    private String login;

    @Value("${application.sms.sender.url}")
    private String url;

    @Value("${application.sms.sender.password}")
    private String password;


    public SmsSenderService() {
    }


    @Override
    public List<ActiveNotificationExtender> send(List<ActiveNotification> notifications) {
        log.debug("Notification-Processing service. debug at sendSms method");

        ExecutorService executor = Executors.newFixedThreadPool(notifications.size());
        List<Future<ActiveNotificationExtender>> futures = new ArrayList<>();
        List<ActiveNotificationExtender> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        notifications.forEach(not -> {
            BulkSmsRequest req = new BulkSmsRequest();
            LocalDateTime now = LocalDateTime.now();
            String formatDateTime = now.format(formatter);
            BulkSmsRequest.Head head = new BulkSmsRequest.Head();
            head.setOperation("submit");
            head.setTitle(login);
            head.setPassword(password);
            head.setIsbulk("false");
            head.setScheduled(formatDateTime);
            head.setControlid("544375" + (int) (Math.random() * 1000000));
            head.setLogin(login);
            BulkSmsRequest.Body body = new BulkSmsRequest.Body();
            body.setMessage(not.getText());
            body.setMsisdn(not.getTarget());
            BulkSmsRequest.Body[] bodies = new BulkSmsRequest.Body[1];
            bodies[0] = body;

            req.setHead(head);
            req.setBodies(bodies);

            futures.add(executor.submit(() -> send(req, not)));
        });


        futures.forEach(x -> {
            try {
                result.add(x.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();
        return result;
    }


    public ActiveNotificationExtender send(BulkSmsRequest request, ActiveNotification notification) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, String.class);

        ObjectMapper mapper = new XmlMapper();
        BulksSmsResponse realResponse = null;
        try {
            realResponse = mapper.readValue(response.getBody(), BulksSmsResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String responsecode = realResponse.getHead().getResponsecode();

        if (responsecode.equals("002") ||
                responsecode.equals("235") ||
                responsecode.equals("2XX") ||
                responsecode.equals("300")) {
            notification.setStatus(NotificationStatus.FAILED);
        } else if (responsecode.equals("000")) {
            notification.setStatus(NotificationStatus.DONE);
            String taskId = realResponse.getBody()[0].getTaskid();
            notification.setTaskId(taskId);
            SmsReportsRequest reportReq = new SmsReportsRequest();
            SmsReportsRequest.Head head = new SmsReportsRequest.Head();
            head.setOperation("report");
            head.setLogin(login);
            head.setPassword(password);
            head.setTaskid(taskId);
            reportReq.setHead(head);

            HttpEntity entityTwo = new HttpEntity<>(reportReq, headers);
            ResponseEntity<String> reportResponse = restTemplate.exchange(
                    url, HttpMethod.POST, entityTwo, String.class);
            ObjectMapper mapperForReport = new XmlMapper();
            SmsReportsResponse reportToXml = null;
            try {
                reportToXml = mapperForReport.readValue(reportResponse.getBody(), SmsReportsResponse.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            String reportResponseCode = reportToXml.getHead().getResponsecode();
            if (reportResponseCode.equals("000")) {
                int isDelivered = reportToXml.getBody().getDelivered();
                int isError = reportToXml.getBody().getError();
                int isFailed = reportToXml.getBody().getFailed();
                int isPending = reportToXml.getBody().getPending();
                int isRemoved = reportToXml.getBody().getRemoved();
                if (isError == 1) {
                    notification.setStatus(NotificationStatus.ERROR);
                }
                if (isDelivered == 1) {
                    notification.setStatus(NotificationStatus.DELIVERED);
                }
                if (isFailed == 1) {
                    notification.setStatus(NotificationStatus.FAILED);
                }
                if (isPending == 1) {
                    notification.setStatus(NotificationStatus.PENDING);
                }
                if (isRemoved == 1) {
                    notification.setStatus(NotificationStatus.REMOVED);
                }
            } else if (reportResponseCode.equals("002") ||
                    reportResponseCode.equals("235") ||
                    reportResponseCode.equals("2XX") ||
                    reportResponseCode.equals("300")) {
                notification.setStatus(NotificationStatus.FAILED);
            } else {
                for (int i = 100; i <= 118; i++) {
                    if (reportResponseCode.equals("" + i)) {
                        notification.setStatus(NotificationStatus.FAILED);
                    }
                }
            }
        } else {
            for (int i = 100; i <= 118; i++) {
                if (responsecode.equals("" + i)) {
                    notification.setStatus(NotificationStatus.FAILED);
                }
            }
        }
        LocalDateTime now = LocalDateTime.now();
        notification.setSentDateTime(now);
        notification.setCountOfSending(notification.getCountOfSending() + 1);

        ActiveNotificationExtender activeNotificationExtender = new ActiveNotificationExtender();

        if (notification.getStatus().equals(NotificationStatus.FAILED) ||
                notification.getStatus().equals(NotificationStatus.ERROR)
        ) {
            if (notification.getCountOfSending() < notification.getMaxCountOfSending()) {
                notification.setStatus(NotificationStatus.READY);

                activeNotificationExtender.setActiveNotification(notification);
                activeNotificationExtender.setActionToPerform(ActionToPerform.SAVE);
            } else {
                activeNotificationExtender.setActiveNotification(notification);
                activeNotificationExtender.setActionToPerform(ActionToPerform.ARCHIVE);
            }
        } else if (notification.getStatus().equals(NotificationStatus.DONE) ||
                notification.getStatus().equals(NotificationStatus.DELIVERED) ||
                notification.getStatus().equals(NotificationStatus.PENDING) ||
                notification.getStatus().equals(NotificationStatus.REMOVED)
        ) {
            activeNotificationExtender.setActiveNotification(notification);
            activeNotificationExtender.setActionToPerform(ActionToPerform.ARCHIVE);
        }
        return activeNotificationExtender;
    }

}
