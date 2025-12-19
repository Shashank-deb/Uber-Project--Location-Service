package com.example.uberprojectlocationservice.controllers;


import com.example.uberprojectlocationservice.DTO.DriverLocationDTO;
import com.example.uberprojectlocationservice.DTO.NearbyDriversRequestDTO;
import com.example.uberprojectlocationservice.DTO.SaveDriverLocationRequestDTO;
import com.example.uberprojectlocationservice.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/drivers")
    public ResponseEntity<Boolean> saveDriverLocation(@RequestBody SaveDriverLocationRequestDTO saveDriverLocationRequestDto) {
        try {
            Boolean response = locationService.saveDriverLocation(saveDriverLocationRequestDto.getDriverId(), saveDriverLocationRequestDto.getLatitude(), saveDriverLocationRequestDto.getLongitude());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @GetMapping ("/nearby/drivers")
    public ResponseEntity<List<DriverLocationDTO>> getNearbyDrivers(@RequestBody NearbyDriversRequestDTO nearbyDriversRequestDto) {
        try {
            List<DriverLocationDTO> drivers = locationService.getNearByDrivers(nearbyDriversRequestDto.getLatitude(), nearbyDriversRequestDto.getLongitude());
            return new ResponseEntity<>(drivers, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}

