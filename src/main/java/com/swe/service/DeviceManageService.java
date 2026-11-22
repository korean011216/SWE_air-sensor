package com.swe.service;

import com.swe.dto.DeviceCreateReq;
import com.swe.dto.DeviceRes;
import com.swe.dto.DeviceUpdateReq;
import com.swe.entity.Device;
import com.swe.entity.User;
import com.swe.repository.AlertHistoryRepository;
import com.swe.repository.DeviceRepository;
import com.swe.repository.SensorLogRepository;
import com.swe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceManageService {

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final SensorLogRepository sensorLogRepository;
    private final AlertHistoryRepository alertHistoryRepository;

    @Transactional
    public DeviceRes registerDevice(DeviceCreateReq req) {
        String normalizedMac = req.macAddress().trim().toUpperCase();

        if (deviceRepository.existsByMacAddress(normalizedMac)) {
            throw new IllegalArgumentException("이미 등록된 MAC 주소입니다: " + normalizedMac);
        }
        if (!userRepository.existsById(req.userId())) {
            throw new IllegalArgumentException("존재하지 않는 사용자 ID입니다: " + req.userId());
        }

        User userRef = userRepository.getReferenceById(req.userId());
        Device device = Device.builder()
                .user(userRef)
                .macAddress(normalizedMac)
                .name(req.name().trim())
                .build();

        return DeviceRes.from(deviceRepository.save(device));
    }

    @Transactional(readOnly = true)
    public List<DeviceRes> getDevices(Long userId) {
        List<Device> devices = (userId == null)
                ? deviceRepository.findAllWithThreshold()
                : deviceRepository.findAllByUserIdWithThreshold(userId);

        return devices.stream()
                .map(DeviceRes::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public DeviceRes getDevice(Long deviceId) {
        Device device = deviceRepository.findByIdWithThreshold(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기기 ID입니다: " + deviceId));

        return DeviceRes.from(device);
    }

    @Transactional
    public DeviceRes updateDeviceName(Long deviceId, DeviceUpdateReq req) {
        Device device = deviceRepository.findByIdWithThreshold(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기기 ID입니다: " + deviceId));

        device.updateName(req.name());
        return DeviceRes.from(device);
    }

    @Transactional
    public void deleteDevice(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기기 ID입니다: " + deviceId));

        sensorLogRepository.deleteAllByDeviceId(deviceId);
        alertHistoryRepository.deleteAllByDeviceId(deviceId);
        deviceRepository.delete(device); // ThresholdConfig는 CascadeType.ALL로 함께 삭제
    }
}