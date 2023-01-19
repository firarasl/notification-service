package az.eurodesign.notification.processing.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "application")
@Getter @Setter @NoArgsConstructor
public class ApplicationConfiguration {

    private boolean enableSchedulers;

    private int processorSchedulerFixedDelay;

    private int bufferSize;

    private String smsSenderLogin;

    private String smsSenderPassword;

    private String smsSenderUrl;
}