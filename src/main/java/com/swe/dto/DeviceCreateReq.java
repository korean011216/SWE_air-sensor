package com.swe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeviceCreateReq(
        @NotNull(message = "사용자 ID는 필수입니다.")
        Long userId,

        @NotBlank(message = "MAC 주소는 필수입니다.")
        String macAddress,

        @NotBlank(message = "기기 이름(설치 장소)은 필수입니다.")
        String name
) {}