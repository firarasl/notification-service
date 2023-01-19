package az.eurodesign.notification.processing;

import az.eurodesign.notification.processing.payload.SmsReportsRequest;
import az.eurodesign.notification.processing.payload.SmsReportsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class NotificationProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationProcessingApplication.class, args);
	}

}
