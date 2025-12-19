package com.example.uberprojectlocationservice.configurations.controllers;

import com.example.uberprojectlocationservice.configurations.DTO.DriverLocationDTO;
import com.example.uberprojectlocationservice.configurations.DTO.NearbyDriversRequestDTO;
import com.example.uberprojectlocationservice.configurations.DTO.SaveDriverLocationRequestDTO;
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
    public ResponseEntity<?> saveDriverLocation(
            @RequestBody SaveDriverLocationRequestDTO saveDriverLocationRequestDTO
    ) {

        try {
            Boolean response=locationService.saveDriverLocation(saveDriverLocationRequestDTO.getDriverId(), saveDriverLocationRequestDTO.getLatitude(), saveDriverLocationRequestDTO.getLongitude());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/nearby/drivers")
    public ResponseEntity<List<DriverLocationDTO>> getNearbyDrivers(
            @RequestBody NearbyDriversRequestDTO nearbyDriversRequestDTO
    ) {
      try {
          List<DriverLocationDTO> drivers=locationService.getNearbyDrivers(nearbyDriversRequestDTO.getLatitude(), nearbyDriversRequestDTO.getLongitude());
          return new ResponseEntity<>(drivers,HttpStatus.OK);
      }
      catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
}
