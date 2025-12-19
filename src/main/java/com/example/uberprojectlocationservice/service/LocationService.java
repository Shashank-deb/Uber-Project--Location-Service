package com.example.uberprojectlocationservice.service;

import com.example.uberprojectlocationservice.DTO.DriverLocationDTO;


import java.util.List;

public interface LocationService {

    Boolean saveDriverLocation(String driverId, Double latitude, Double Longitude);

    List<DriverLocationDTO> getNearByDrivers(Double latitude, Double Longitude);

}
