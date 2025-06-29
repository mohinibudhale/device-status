package com.ubiety.device_status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@Transactional
public class DeviceStatusApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final String API_KEY_VALUE = "AGLYVBT44473677THXCT";

    @Test
    public void testPostStatus_shouldReturnCreated() throws Exception {
        String json = """
        {
            "deviceId": "sensor-api-001",
            "timestamp": "2025-06-09T18:00:00Z",
            "batteryLevel": 88,
            "rssi": -50,
            "online": true
        }
        """;

        mockMvc.perform(post("/status")
                .header(API_KEY_HEADER, API_KEY_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.deviceId").value("sensor-api-001"));
    }

    @Test
    public void testGetStatus_shouldReturnLastKnownStatus() throws Exception {
        String json = """
        {
            "deviceId": "sensor-api-002",
            "timestamp": "2025-06-09T19:00:00Z",
            "batteryLevel": 55,
            "rssi": -60,
            "online": false
        }
        """;

        mockMvc.perform(post("/status")
                .header(API_KEY_HEADER, API_KEY_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/status/sensor-api-002")
                .header(API_KEY_HEADER, API_KEY_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batteryLevel").value(55))
                .andExpect(jsonPath("$.online").value(false));
    }

    @Test
    public void testSummary_shouldReturnLatestStatusPerDevice() throws Exception {
        String[] records = {
            """
            {
                "deviceId": "sensor-summary-001",
                "timestamp": "2025-06-09T12:00:00Z",
                "batteryLevel": 75,
                "rssi": -40,
                "online": true
            }
            """,
            """
            {
                "deviceId": "sensor-summary-001",
                "timestamp": "2025-06-09T13:00:00Z",
                "batteryLevel": 70,
                "rssi": -41,
                "online": false
            }
            """,
            """
            {
                "deviceId": "sensor-summary-002",
                "timestamp": "2025-06-09T14:00:00Z",
                "batteryLevel": 90,
                "rssi": -30,
                "online": true
            }
            """
        };

        for (String record : records) {
            mockMvc.perform(post("/status")
                    .header(API_KEY_HEADER, API_KEY_VALUE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(record))
                    .andExpect(status().isCreated());
        }

        mockMvc.perform(get("/status/summary")
                .header(API_KEY_HEADER, API_KEY_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
