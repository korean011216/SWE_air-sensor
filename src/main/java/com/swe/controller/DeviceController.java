package com.swe.controller;

import com.swe.dto.DeviceCreateReq;
import com.swe.dto.DeviceRes;
import com.swe.dto.DeviceUpdateReq;
import com.swe.service.DeviceManageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceManageService deviceManageService;

    @PostMapping
    public ResponseEntity<DeviceRes> registerDevice(@Valid @RequestBody DeviceCreateReq req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deviceManageService.registerDevice(req));
    }

    @GetMapping
    public ResponseEntity<List<DeviceRes>> getDevices(@RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(deviceManageService.getDevices(userId));
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<DeviceRes> getDevice(@PathVariable Long deviceId) {
        return ResponseEntity.ok(deviceManageService.getDevice(deviceId));
    }

    @PatchMapping("/{deviceId}")
    public ResponseEntity<DeviceRes> updateDeviceName(
            @PathVariable Long deviceId,
            @Valid @RequestBody DeviceUpdateReq req) {
        return ResponseEntity.ok(deviceManageService.updateDeviceName(deviceId, req));
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long deviceId) {
        deviceManageService.deleteDevice(deviceId);
        return ResponseEntity.noContent().build();
    }
}