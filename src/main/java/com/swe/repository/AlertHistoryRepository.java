package com.swe.repository;

import com.swe.dto.AlertHistoryRes;
import com.swe.entity.AlertHistory;
import com.swe.entity.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("""
            SELECT new com.swe.dto.AlertHistoryRes(a.id, a.alertLevel, a.recordedPpm, a.createdAt)
            FROM AlertHistory a
            WHERE a.device.id = :deviceId
            ORDER BY a.id DESC
            """)
    Page<AlertHistoryRes> findAlertsByDeviceId(@Param("deviceId") Long deviceId, Pageable pageable);
}
