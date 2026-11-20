package com.swe.service;

import com.swe.entity.AlertHistory;
import com.swe.entity.Device;
import com.swe.entity.ThresholdConfig;
import com.swe.repository.AlertHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertHistoryRepository alertHistoryRepository;

    @Transactional
    public void evaluateAndRecordAlert(Device device, double currentPpm) {
        ThresholdConfig config = device.getThresholdConfig();

        // 임계치가 미설정된 기기거나 주의 수치 미만이면 즉시 종료
        if (config == null || currentPpm < config.getCautionPpm()) {
            return;
        }

        String alertLevel = (currentPpm >= config.getDangerPpm()) ? "DANGER" : "CAUTION";

        AlertHistory alert = AlertHistory.builder()
                .device(device)
                .alertLevel(alertLevel)
                .recordedPpm(currentPpm)
                .build();

        alertHistoryRepository.save(alert);
    }
}