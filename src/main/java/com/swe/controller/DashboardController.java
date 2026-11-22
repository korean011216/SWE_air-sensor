package com.swe.controller;

import com.swe.dto.AlertHistoryRes;
import com.swe.dto.DeviceStatsRes;
import com.swe.dto.SensorLogRes;
import com.swe.service.DashboardStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard/devices/{deviceId}")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardStatsService dashboardStatsService;

    @GetMapping("/logs")
    public ResponseEntity<List<SensorLogRes>> getRecentLogs(
            @PathVariable Long deviceId,
            @RequestParam(defaultValue = "20") int limit) {
        return ResponseEntity.ok(dashboardStatsService.getRecentLogs(deviceId, limit));
    }

    @GetMapping("/alerts")
    public ResponseEntity<Page<AlertHistoryRes>> getAlertHistory(
            @PathVariable Long deviceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(dashboardStatsService.getAlertHistory(deviceId, page, size));
    }

    @GetMapping("/stats")
    public ResponseEntity<DeviceStatsRes> getDeviceStats(
            @PathVariable Long deviceId,
            @RequestParam(defaultValue = "24") int hours) {
        return ResponseEntity.ok(dashboardStatsService.getDeviceStats(deviceId, hours));
    }
}