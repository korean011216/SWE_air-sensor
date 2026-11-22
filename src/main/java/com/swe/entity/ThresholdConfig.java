package com.swe.entity;

import jakarta.persistence.*;
import lombok.*;


// 임계치 설정
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThresholdConfig {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", unique = true, nullable = false, insertable = false)
    private Device device;

    @Column(nullable = false)
    private double cautionPpm; // 주의 수치 (예: 500)

    @Column(nullable = false)
    private double dangerPpm;  // 위험 수치 (예: 1000)

    @Builder
    public ThresholdConfig(Device device, double cautionPpm, double dangerPpm) {
        validateRange(cautionPpm, dangerPpm);
        this.device = device;
        this.cautionPpm = cautionPpm;
        this.dangerPpm = dangerPpm;
    }

    public void updateThreshold(double cautionPpm, double dangerPpm) {
        validateRange(cautionPpm, dangerPpm);
        this.cautionPpm = cautionPpm;
        this.dangerPpm = dangerPpm;
    }

    private void validateRange(double cautionPpm, double dangerPpm) {
        if (cautionPpm <= 0 || dangerPpm <= 0 || cautionPpm >= dangerPpm) {
            throw new IllegalArgumentException("기준치는 0보다 크고 위험 기준치보다 작아야 합니다.");
        }
    }
}