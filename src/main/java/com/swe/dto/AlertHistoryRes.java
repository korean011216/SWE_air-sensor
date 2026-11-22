package com.swe.dto;

import com.swe.enums.AlertLevel;
import java.time.LocalDateTime;

public record AlertHistoryRes(
        Long alertId,
        AlertLevel alertLevel,
        double recordedPpm,
        LocalDateTime createdAt
) {}