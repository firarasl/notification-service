package az.eurodesign.notification.processing.repository;

import az.eurodesign.notification.processing.model.ActiveNotification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = {"classpath:sql/create-active-notifications-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:sql/create-active-notifications-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ActiveNotificationBufferedReaderTest {

}