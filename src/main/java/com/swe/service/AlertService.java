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
    public String evaluateAndRecordAlert(Device device, double currentPpm) {
        ThresholdConfig config = device.getThresholdConfig();

        if (config == null || currentPpm < config.getCautionPpm()) {
            return "NORMAL";
        }

        String alertLevel = (currentPpm >= config.getDangerPpm()) ? "DANGER" : "CAUTION";

        AlertHistory alert = AlertHistory.builder()
                .device(device)
                .alertLevel(alertLevel)
                .recordedPpm(currentPpm)
                .build();

        alertHistoryRepository.save(alert);
        return alertLevel;
    }
}