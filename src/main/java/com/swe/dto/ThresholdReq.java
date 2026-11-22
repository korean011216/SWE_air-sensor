package com.swe.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ThresholdReq(
        @NotNull(message = "주의 기준치(cautionPpm)는 필수입니다.")
        @Positive(message = "주의 기준치는 0보다 커야 합니다.")
        Double cautionPpm,

        @NotNull(message = "위험 기준치(dangerPpm)는 필수입니다.")
        @Positive(message = "위험 기준치는 0보다 커야 합니다.")
        Double dangerPpm
) {
    @AssertTrue(message = "주의 기준치는 위험 기준치보다 작아야 합니다.")
    public boolean isValidRange() {
        if (cautionPpm == null || dangerPpm == null) {
            return true; // @NotNull이 선행 처리
        }
        return cautionPpm < dangerPpm;
    }
}