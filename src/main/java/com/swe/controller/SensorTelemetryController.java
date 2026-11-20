package com.swe.controller;

import com.swe.dto.TelemetryReq;
import com.swe.service.SensorTelemetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorTelemetryController {

    private final SensorTelemetryService telemetryService;

    @PostMapping("/data")
    public ResponseEntity<String> receiveData(@RequestBody TelemetryReq req) {
        // Service로 데이터를 넘겨 DB에 저장 및 임계치 판정
        telemetryService.processTelemetry(req);
        return ResponseEntity.ok("Data received and saved successfully");
    }
}