package com.swe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Device extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String macAddress; // 기기 고유 식별자

    private String name; // 장소

    @OneToOne(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ThresholdConfig thresholdConfig;

    @Builder
    public Device(User user, String macAddress, String name) {
        this.user = user;
        this.macAddress = macAddress;
        this.name = name;
    }
}