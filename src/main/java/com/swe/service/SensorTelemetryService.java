package com.swe.service;

import com.swe.dto.TelemetryBroadcastDto;
import com.swe.dto.TelemetryReq;
import com.swe.entity.Device;
import com.swe.entity.SensorLog;
import com.swe.enums.AlertLevel;
import com.swe.event.TelemetryBroadcastEvent;
import com.swe.repository.DeviceRepository;
import com.swe.repository.SensorLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SensorTelemetryService {

    private final SensorLogRepository sensorLogRepository;
    private final DeviceRepository deviceRepository;
    private final AlertService alertService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void processTelemetry(TelemetryReq req) {
        Device device = deviceRepository.findByMacAddress(req.getMacAddress())
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 기기입니다: " + req.getMacAddress()));

        SensorLog log = SensorLog.builder()
                .device(device)
                .ppm(req.getPpm())
                .temperature(req.getTemperature())
                .humidity(req.getHumidity())
                .build();

        SensorLog savedLog = sensorLogRepository.save(log);
        AlertLevel alertLevel = alertService.evaluateAndRecordAlert(device, req.getPpm());

        TelemetryBroadcastDto payload = TelemetryBroadcastDto.builder()
                .deviceId(device.getId())
                .deviceName(device.getName())
                .macAddress(device.getMacAddress())
                .ppm(savedLog.getPpm())
                .temperature(savedLog.getTemperature())
                .humidity(savedLog.getHumidity())
                .alertLevel(alertLevel)
                .timestamp(savedLog.getCreatedAt())
                .build();

        eventPublisher.publishEvent(new TelemetryBroadcastEvent(payload));
    }
}