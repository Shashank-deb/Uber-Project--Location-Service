package com.example.uberprojectlocationservice.service;

import com.example.uberprojectlocationservice.configurations.DTO.DriverLocationDTO;

import java.util.List;

public interface LocationService {
    Boolean saveDriverLocation(String driverId,Double latitude,Double longitude);
    List<DriverLocationDTO> getNearbyDrivers(Double latitude, Double longitude);
}
