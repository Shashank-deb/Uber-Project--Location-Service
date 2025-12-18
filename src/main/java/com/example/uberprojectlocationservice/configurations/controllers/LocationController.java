package com.example.uberprojectlocationservice.configurations.controllers;

import com.example.uberprojectlocationservice.configurations.DTO.NearbyDriversRequestDTO;
import com.example.uberprojectlocationservice.configurations.DTO.SaveDriverLocationRequestDTO;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("/api/v1/location")
public class LocationController {

    private StringRedisTemplate stringRedisTemplate;

    private static final String DRIVER_GEO_OPS_KEY = "drivers";
    private static final Double SEARCH_RADIUS = 5.4;

    public LocationController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @PostMapping("/drivers")
    public ResponseEntity<?> saveDriverLocation(
            @RequestBody SaveDriverLocationRequestDTO saveDriverLocationRequestDTO
    ) {

        try {
            GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();
            geoOps.add(
                    DRIVER_GEO_OPS_KEY,
                    new RedisGeoCommands.GeoLocation<>(
                            saveDriverLocationRequestDTO.getDriverId(),
                            new Point(saveDriverLocationRequestDTO.getLatitude(),
                                    saveDriverLocationRequestDTO.getLongitude())));
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/nearby/drivers")
    public ResponseEntity<List<String>> getNearbyDrivers(
            @RequestBody NearbyDriversRequestDTO nearbyDriversRequestDTO
    ) {
        try {
            GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();
            Distance radius = new Distance(SEARCH_RADIUS, Metrics.KILOMETERS);
            Circle within = new Circle(new Point(nearbyDriversRequestDTO.getLatitude(), nearbyDriversRequestDTO.getLongitude()), radius);
            GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOps.radius(DRIVER_GEO_OPS_KEY, within);

            List<String> drivers = new ArrayList<>();
            for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
                drivers.add(result.getContent().getName());

            }
            return new ResponseEntity<>(drivers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
