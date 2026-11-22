package com.swe.repository;

import com.swe.entity.SensorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SensorLogRepository extends JpaRepository<SensorLog, Long> {
    @Modifying
    @Query("DELETE FROM SensorLog s WHERE s.device.id = :deviceId")
    void deleteAllByDeviceId(@Param("deviceId") Long deviceId);
}
