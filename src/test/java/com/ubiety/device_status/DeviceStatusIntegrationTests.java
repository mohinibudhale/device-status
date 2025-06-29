package com.ubiety.device_status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class DeviceStatusIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final String API_KEY_VALUE = "AGLYVBT44473677THXCT";

    @Test
    public void testPostAndGetStatusIntegration() throws Exception {
        String json = """
        {
            "deviceId": "sensor-integration-001",
            "timestamp": "2025-06-09T17:00:00Z",
            "batteryLevel": 78,
            "rssi": -58,
            "online": true
        }
        """;

        mockMvc.perform(post("/status")
                .header(API_KEY_HEADER, API_KEY_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/status/sensor-integration-001")
                .header(API_KEY_HEADER, API_KEY_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batteryLevel").value(78))
                .andExpect(jsonPath("$.online").value(true));
    }
}
