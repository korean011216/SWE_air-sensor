package com.swe.controller;

import com.swe.dto.ThresholdReq;
import com.swe.dto.ThresholdRes;
import com.swe.service.ThresholdConfigService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices/{deviceId}/threshold")
@RequiredArgsConstructor
public class ThresholdController {

    private final ThresholdConfigService thresholdConfigService;

    @GetMapping
    public ResponseEntity<ThresholdRes> getThreshold(@PathVariable Long deviceId) {
        return ResponseEntity.ok(thresholdConfigService.getThreshold(deviceId));
    }

    @PutMapping
    public ResponseEntity<ThresholdRes> updateThreshold(
            @PathVariable Long deviceId,
            @Valid @RequestBody ThresholdReq req) {
        return ResponseEntity.ok(thresholdConfigService.upsertThreshold(deviceId, req));
    }
}