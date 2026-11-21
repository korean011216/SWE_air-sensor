package com.swe.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class TelemetryBroadcastDto {
    private final Long deviceId;
    private final String deviceName;
    private final String macAddress;
    private final double ppm;
    private final double temperature;
    private final double humidity;
    private final String alertLevel; // "NORMAL", "CAUTION", "DANGER"
    private final LocalDateTime timestamp;
}