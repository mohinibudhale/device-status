package com.ubiety.device_status.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ubiety.device_status.model.DeviceStatus;
import com.ubiety.device_status.service.DeviceStatusService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/status")
public class DeviceStatusController {

    private final DeviceStatusService service;

    public DeviceStatusController(DeviceStatusService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DeviceStatus> postStatus(@RequestBody @Valid DeviceStatus status) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(status));
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<DeviceStatus> getStatus(@PathVariable String deviceId) {
        return service.getLatest(deviceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/summary")
    public List<DeviceStatus> getSummary() {
        return service.getAllLatest();
    }
    
    
    @GetMapping("/history/{deviceId}")
    public ResponseEntity<List<DeviceStatus>> getDeviceHistory(@PathVariable String deviceId) {
        List<DeviceStatus> history = service.getHistory(deviceId);
        if (history.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(history);
    }
}
