package com.swe.repository;

import com.swe.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByMacAddress(String macAddress);

    boolean existsByMacAddress(String macAddress);

    @Query("SELECT d FROM Device d LEFT JOIN FETCH d.thresholdConfig WHERE d.id = :id")
    Optional<Device> findByIdWithThreshold(@Param("id") Long id);

    @Query("SELECT d FROM Device d LEFT JOIN FETCH d.thresholdConfig ORDER BY d.id ASC")
    List<Device> findAllWithThreshold();

    @Query("SELECT d FROM Device d LEFT JOIN FETCH d.thresholdConfig WHERE d.user.id = :userId ORDER BY d.id ASC")
    List<Device> findAllByUserIdWithThreshold(@Param("userId") Long userId);
}