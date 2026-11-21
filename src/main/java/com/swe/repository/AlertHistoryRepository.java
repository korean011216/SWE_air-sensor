package com.swe.repository;

import com.swe.entity.AlertHistory;
import com.swe.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlertHistoryRepository extends JpaRepository<AlertHistory, Long> {
    Optional<AlertHistory> findTopByDeviceOrderByCreatedAtDesc(Device device);
}
