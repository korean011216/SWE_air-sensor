package com.swe.event;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class TelemetryBroadcastListener {

    private final SimpMessagingTemplate messagingTemplate;

    // DB 트랜잭션이 커밋된 뒤에만 실시간 브로드캐스트 (롤백 시 전송 안 함)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onTelemetryBroadcast(TelemetryBroadcastEvent event) {
        Long deviceId = event.payload().getDeviceId();
        messagingTemplate.convertAndSend("/topic/telemetry/" + deviceId, event.payload());
    }
}
