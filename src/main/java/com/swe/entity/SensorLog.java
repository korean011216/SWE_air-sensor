package com.swe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "idx_device_created", columnList = "device_id, createdAt")
})
public class SensorLog extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(nullable = false)
    private double ppm; // 보정 완료된 가스 수치

    private double temperature; // 온도

    private double humidity; // 습도

    @Builder
    public SensorLog(Device device, double ppm, double temperature, double humidity) {
        this.device = device;
        this.ppm = ppm;
        this.temperature = temperature;
        this.humidity = humidity;
    }
}