//package az.eurodesign.notification.processing.service.implementation;
//
//import az.eurodesign.notification.processing.service.SseEmitterService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//class SseEmitterServiceImplTest {
//
//    @Autowired
//    private SseEmitterService sseEmitterService;
//
//    @Test
//    void create_NotNull() {
//        SseEmitter sseEmitter = sseEmitterService.create("uid");
//        assertNotNull(sseEmitter);
//    }
//}