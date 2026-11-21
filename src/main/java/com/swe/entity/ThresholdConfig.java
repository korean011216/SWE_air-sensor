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
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(nullable = false)
    private double cautionPpm; // 주의 수치 (예: 500)

    @Column(nullable = false)
    private double dangerPpm;  // 위험 수치 (예: 1000)

    @Builder
    public ThresholdConfig(Device device, double cautionPpm, double dangerPpm) {
        this.device = device;
        this.cautionPpm = cautionPpm;
        this.dangerPpm = dangerPpm;
    }

    // 수치 변경 편의 메서드
    public void updateThreshold(double cautionPpm, double dangerPpm) {
        this.cautionPpm = cautionPpm;
        this.dangerPpm = dangerPpm;
    }
}