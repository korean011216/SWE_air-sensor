package com.swe.dto;

import com.swe.entity.Device;
import com.swe.entity.ThresholdConfig;

public record DeviceRes(
        Long deviceId,
        Long userId,
        String macAddress,
        String name,
        Double cautionPpm,
        Double dangerPpm,
        boolean thresholdConfigured
) {
    public static DeviceRes from(Device device) {
        ThresholdConfig config = device.getThresholdConfig();
        Long ownerId = (device.getUser() != null) ? device.getUser().getId() : null;

        return new DeviceRes(
                device.getId(),
                ownerId,
                device.getMacAddress(),
                device.getName(),
                config != null ? config.getCautionPpm() : null,
                config != null ? config.getDangerPpm() : null,
                config != null
        );
    }
}