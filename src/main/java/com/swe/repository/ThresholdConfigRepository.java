package com.swe.repository;

import com.swe.entity.ThresholdConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ThresholdConfigRepository extends JpaRepository<ThresholdConfig, Long> {
    Optional<ThresholdConfig> findByDeviceId(Long deviceId);
}