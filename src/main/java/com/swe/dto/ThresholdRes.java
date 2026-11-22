package com.swe.dto;

import com.swe.entity.ThresholdConfig;

public record ThresholdRes(
        Long deviceId,
        Double cautionPpm,
        Double dangerPpm,
        boolean configured
) {
    public static ThresholdRes unconfigured(Long deviceId) {
        return new ThresholdRes(deviceId, null, null, false);
    }

    public static ThresholdRes from(Long deviceId, ThresholdConfig config) {
        return new ThresholdRes(deviceId, config.getCautionPpm(), config.getDangerPpm(), true);
    }
}