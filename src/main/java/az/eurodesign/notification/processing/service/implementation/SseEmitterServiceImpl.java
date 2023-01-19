package az.eurodesign.notification.processing.service.implementation;

import az.eurodesign.notification.processing.dto.ActiveNotificationDTO;
import az.eurodesign.notification.processing.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class SseEmitterServiceImpl implements SseEmitterService {

    private final ConcurrentMap<String, CopyOnWriteArrayList<SseEmitter>> emitterMap = new ConcurrentHashMap<>();
//
//    @Override
//    public SseEmitter create(String uid) {
//        SseEmitter emitter = new SseEmitter();
//
//        emitterMap.putIfAbsent(uid, new CopyOnWriteArrayList<>());
//        CopyOnWriteArrayList<SseEmitter> emitters = emitterMap.get(uid);
//        emitters.add(emitter);
//
//        emitter.onCompletion(() -> {
//            emitterMap.get(uid).remove(emitter);
//            if (emitterMap.get(uid).isEmpty()) emitterMap.remove(uid);
//        });
//
//        emitter.onTimeout(() -> {
//            emitter.complete();
//            emitterMap.get(uid).remove(emitter);
//            if (emitterMap.get(uid).isEmpty()) emitterMap.remove(uid);
//        });
//
//        return emitter;
//    }
//
//    @EventListener
//    public void onCustom(ActiveNotificationDTO custom) {
//        if (!emitterMap.containsKey(custom.getUid())) return;
//
//        CopyOnWriteArrayList<SseEmitter> emitters = emitterMap.get(custom.getUid());
//
//        if (emitters.isEmpty()) return;
//
//
//        for (SseEmitter emitter : emitters) {
//            try {
//                emitter.send(custom);
//            } catch (Exception e) {
//                emitters.remove(emitter);
//            }
//        }
//
//        if (emitters.isEmpty()) emitterMap.remove(custom.getUid());
//    }
}
