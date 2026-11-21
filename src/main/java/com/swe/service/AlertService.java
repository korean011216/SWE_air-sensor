package com.swe.service;

import com.swe.entity.AlertHistory;
import com.swe.entity.Device;
import com.swe.entity.ThresholdConfig;
import com.swe.enums.AlertLevel;
import com.swe.repository.AlertHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertHistoryRepository alertHistoryRepository;

    @Transactional
    public AlertLevel evaluateAndRecordAlert(Device device, double currentPpm) {
        ThresholdConfig config = device.getThresholdConfig();

        AlertLevel alertLevel;
        if (config == null || currentPpm < config.getCautionPpm()) {
            alertLevel = AlertLevel.NORMAL;
        } else {
            alertLevel = (currentPpm >= config.getDangerPpm()) ? AlertLevel.DANGER : AlertLevel.CAUTION;
        }

        AlertLevel lastLevel = alertHistoryRepository.findTopByDeviceOrderByCreatedAtDesc(device)
                .map(AlertHistory::getAlertLevel)
                .orElse(AlertLevel.NORMAL);

        // 실제 경보(CAUTION/DANGER)로 상태가 바뀔 때만 이력 저장 (매 수신마다 중복 적재 방지)
        if (alertLevel != AlertLevel.NORMAL && !alertLevel.equals(lastLevel)) {
            AlertHistory alert = AlertHistory.builder()
                    .device(device)
                    .alertLevel(alertLevel)
                    .recordedPpm(currentPpm)
                    .build();
            alertHistoryRepository.save(alert);
        }

        return alertLevel;
    }
}