package com.swe.dto;

import jakarta.validation.constraints.NotBlank;

public record DeviceUpdateReq(
        @NotBlank(message = "변경할 기기 이름은 필수입니다.")
        String name
) {}