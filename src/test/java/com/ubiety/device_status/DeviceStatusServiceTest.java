package com.ubiety.device_status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.ubiety.device_status.service.DeviceStatusService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class DeviceStatusServiceTest {

    @Autowired
    private DeviceStatusService service;

    @Test
    public void testEvaluateBatteryHealth() {
        assertEquals("Good", service.evaluateBatteryHealth(85));
        assertEquals("Moderate", service.evaluateBatteryHealth(55));
        assertEquals("Low", service.evaluateBatteryHealth(30));
    }
}

