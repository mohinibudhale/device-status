package com.ubiety.device_status.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.ubiety.device_status.model.DeviceStatus;

import java.util.*;

@Repository
public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long> {

    Optional<DeviceStatus> findTopByDeviceIdOrderByTimestampDesc(String deviceId);
    
    @Query("SELECT ds FROM DeviceStatus ds WHERE ds.timestamp = (" +
           "SELECT MAX(d.timestamp) FROM DeviceStatus d WHERE d.deviceId = ds.deviceId" +
           ")")
    List<DeviceStatus> findLatestStatusPerDevice();

    
    List<DeviceStatus> findByDeviceIdOrderByTimestampDesc(String deviceId);

}
