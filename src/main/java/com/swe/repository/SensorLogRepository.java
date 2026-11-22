package com.swe.repository;

import com.swe.dto.DeviceStatsRes;
import com.swe.dto.SensorLogRes;
import com.swe.entity.SensorLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorLogRepository extends JpaRepository<SensorLog, Long> {
    @Modifying
    @Query("DELETE FROM SensorLog s WHERE s.device.id = :deviceId")
    void deleteAllByDeviceId(@Param("deviceId") Long deviceId);

    @Query("""
            SELECT new com.swe.dto.SensorLogRes(s.id, s.ppm, s.temperature, s.humidity, s.createdAt)
            FROM SensorLog s
            WHERE s.device.id = :deviceId
            ORDER BY s.id DESC
            """)
    List<SensorLogRes> findRecentLogsByDeviceId(@Param("deviceId") Long deviceId, Pageable pageable);

    @Query("""
            SELECT new com.swe.dto.DeviceStatsRes(
                COUNT(s.id),
                AVG(s.ppm),
                MAX(s.ppm),
                MIN(s.ppm),
                AVG(s.temperature),
                AVG(s.humidity)
            )
            FROM SensorLog s
            WHERE s.device.id = :deviceId
              AND s.createdAt >= :since
            """)
    DeviceStatsRes findStatsByDeviceIdSince(
            @Param("deviceId") Long deviceId,
            @Param("since") LocalDateTime since
    );
}
