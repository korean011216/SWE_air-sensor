package com.swe.service;

import com.swe.dto.AlertHistoryRes;
import com.swe.dto.DeviceStatsRes;
import com.swe.dto.SensorLogRes;
import com.swe.repository.AlertHistoryRepository;
import com.swe.repository.DeviceRepository;
import com.swe.repository.SensorLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardStatsService {

    private final DeviceRepository deviceRepository;
    private final SensorLogRepository sensorLogRepository;
    private final AlertHistoryRepository alertHistoryRepository;

    public List<SensorLogRes> getRecentLogs(Long deviceId, int limit) {
        validateDeviceExists(deviceId);
        int safeLimit = Math.min(Math.max(limit, 1), 200);

        List<SensorLogRes> logs = new ArrayList<>(
                sensorLogRepository.findRecentLogsByDeviceId(deviceId, PageRequest.of(0, safeLimit))
        );
        // 프론트엔드 시계열 차트에 바로 그릴 수 있도록 시간 오름차순(과거 -> 최신)으로 정렬
        Collections.reverse(logs);
        return logs;
    }

    public Page<AlertHistoryRes> getAlertHistory(Long deviceId, int page, int size) {
        validateDeviceExists(deviceId);
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 200);

        return alertHistoryRepository.findAlertsByDeviceId(deviceId, PageRequest.of(safePage, safeSize));
    }

    public DeviceStatsRes getDeviceStats(Long deviceId, int hours) {
        validateDeviceExists(deviceId);
        int safeHours = Math.min(Math.max(hours, 1), 200); // 최대 30일
        LocalDateTime since = LocalDateTime.now().minusHours(safeHours);

        DeviceStatsRes stats = sensorLogRepository.findStatsByDeviceIdSince(deviceId, since);
        return stats.withContext(deviceId, safeHours);
    }

    private void validateDeviceExists(Long deviceId) {
        if (!deviceRepository.existsById(deviceId)) {
            throw new IllegalArgumentException("존재하지 않는 기기 ID입니다: " + deviceId);
        }
    }
}