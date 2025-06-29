package com.ubiety.device_status.service;

import org.springframework.stereotype.Service;

import com.ubiety.device_status.model.DeviceStatus;
import com.ubiety.device_status.repository.DeviceStatusRepository;

import java.util.*;

@Service
public class DeviceStatusService {

    private final DeviceStatusRepository repository;

    public DeviceStatusService(DeviceStatusRepository repository) {
        this.repository = repository;
    }

    public DeviceStatus save(DeviceStatus status) {
        return repository.save(status);
    }

    public Optional<DeviceStatus> getLatest(String deviceId) {
        return repository.findTopByDeviceIdOrderByTimestampDesc(deviceId);
    }

    public List<DeviceStatus> getAllLatest() {
        return repository.findLatestStatusPerDevice();
    }
    
    
    public List<DeviceStatus> getHistory(String deviceId) {
        return repository.findByDeviceIdOrderByTimestampDesc(deviceId);
    }
    
    public String evaluateBatteryHealth(int batteryLevel) {
        if (batteryLevel >= 75) return "Good";
        if (batteryLevel >= 50) return "Moderate";
        return "Low";
    }
}
