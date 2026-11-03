package com.swe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlertHistory extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(nullable = false)
    private String alertLevel; // "CAUTION" 또는 "DANGER"

    @Column(nullable = false)
    private double recordedPpm; // 경보 발생 당시의 수치

    @Builder
    public AlertHistory(Device device, String alertLevel, double recordedPpm) {
        this.device = device;
        this.alertLevel = alertLevel;
        this.recordedPpm = recordedPpm;
    }
}