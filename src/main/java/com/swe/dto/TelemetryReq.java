package com.swe.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TelemetryReq {
    @NotBlank(message = "MAC 주소는 필수입니다.")
    private String macAddress;

    @NotNull(message = "PPM 수치가 누락되었습니다.")
    @PositiveOrZero(message = "PPM 수치는 0 이상이어야 합니다.")
    private Double ppm;        // double → Double

    @NotNull(message = "온도 수치가 누락되었습니다.")
    @Min(value = -50, message = "온도는 -50℃ 이상이어야 합니다.")
    @Max(value = 50, message = "온도는 50℃ 이하여야 합니다.")
    private Double temperature; // double → Double

    @NotNull(message = "습도 수치가 누락되었습니다.")
    @PositiveOrZero(message = "습도는 0 이상이어야 합니다.")
    @Max(value = 100, message = "습도는 100% 이하여야 합니다.")
    private Double humidity;    // double → Double
}
