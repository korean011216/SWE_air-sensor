package com.swe.repository;

import com.swe.entity.SensorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorLogRepository extends JpaRepository<SensorLog, Long> {
}
