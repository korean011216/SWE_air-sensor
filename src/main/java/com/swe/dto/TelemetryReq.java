package com.swe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TelemetryReq {
    private String macAddress; // 어떤 기기에서 보냈는지 식별
    private double ppm;        // 가스 농도
    private double temperature;// 온도
    private double humidity;   // 습도
}