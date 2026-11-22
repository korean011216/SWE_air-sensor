package com.swe.service;

import com.swe.dto.ThresholdReq;
import com.swe.dto.ThresholdRes;
import com.swe.entity.Device;
import com.swe.entity.ThresholdConfig;
import com.swe.repository.DeviceRepository;
import com.swe.repository.ThresholdConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ThresholdConfigService {

    private final DeviceRepository deviceRepository;
    private final ThresholdConfigRepository thresholdConfigRepository;

    @Transactional(readOnly = true)
    public ThresholdRes getThreshold(Long deviceId) {
        return thresholdConfigRepository.findByDeviceId(deviceId)
                .map(config -> ThresholdRes.from(deviceId, config))
                .orElseGet(() -> {
                    validateDeviceExists(deviceId);
                    return ThresholdRes.unconfigured(deviceId);
                });
    }

    @Transactional
    public ThresholdRes upsertThreshold(Long deviceId, ThresholdReq req) {
        return thresholdConfigRepository.findByDeviceId(deviceId)
                .map(existing -> {
                    existing.updateThreshold(req.cautionPpm(), req.dangerPpm());
                    return ThresholdRes.from(deviceId, existing);
                })
                .orElseGet(() -> {
                    validateDeviceExists(deviceId);
                    Device deviceRef = deviceRepository.getReferenceById(deviceId);
                    ThresholdConfig saved = thresholdConfigRepository.save(
                            ThresholdConfig.builder()
                                    .device(deviceRef)
                                    .cautionPpm(req.cautionPpm())
                                    .dangerPpm(req.dangerPpm())
                                    .build()
                    );
                    return ThresholdRes.from(deviceId, saved);
                });
    }

    private void validateDeviceExists(Long deviceId) {
        if (!deviceRepository.existsById(deviceId)) {
            throw new IllegalArgumentException("존재하지 않는 기기 ID입니다: " + deviceId);
        }
    }
}