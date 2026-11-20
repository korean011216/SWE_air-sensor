package com.swe.service;

import com.swe.dto.TelemetryReq;
import com.swe.entity.Device;
import com.swe.entity.SensorLog;
import com.swe.repository.DeviceRepository;
import com.swe.repository.SensorLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SensorTelemetryService {

    private final SensorLogRepository sensorLogRepository;
    private final DeviceRepository deviceRepository;

    @Transactional
    public void processTelemetry(TelemetryReq req) {
        Device device = deviceRepository.findByMacAddress(req.getMacAddress())
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 기기입니다."));

        SensorLog log = SensorLog.builder()
                .device(device)
                .ppm(req.getPpm())
                .temperature(req.getTemperature())
                .humidity(req.getHumidity())
                .build();

        sensorLogRepository.save(log);
    }
}