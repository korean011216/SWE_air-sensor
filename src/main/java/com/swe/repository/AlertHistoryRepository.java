package com.swe.repository;

import com.swe.entity.AlertHistory;
import com.swe.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AlertHistoryRepository extends JpaRepository<AlertHistory, Long> {
    Optional<AlertHistory> findTopByDeviceOrderByCreatedAtDesc(Device device);

    @Modifying
    @Query("DELETE FROM AlertHistory a WHERE a.device.id = :deviceId")
    void deleteAllByDeviceId(@Param("deviceId") Long deviceId);
}
