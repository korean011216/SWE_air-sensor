package com.swe.dto;

import java.time.LocalDateTime;

public record SensorLogRes(Long logId,
        double ppm,
        double temperature,
        double humidity,
        LocalDateTime createdAt) {
}