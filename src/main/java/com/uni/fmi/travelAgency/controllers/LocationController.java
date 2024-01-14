package com.uni.fmi.travelAgency.controllers;

import com.uni.fmi.travelAgency.dtos.locationDtos.CreateLocationDTO;
import com.uni.fmi.travelAgency.dtos.locationDtos.ResponseLocationDTO;
import com.uni.fmi.travelAgency.dtos.locationDtos.UpdateLocationDTO;
import com.uni.fmi.travelAgency.entities.Location;
import com.uni.fmi.travelAgency.services.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/locations")
@Tag(name = "Location service", description = "Provides Location service API's")
public class LocationController {

    @Autowired
    LocationService locationService;

    public LocationController(){
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseLocationDTO> createLocation(@Valid @RequestBody CreateLocationDTO locationDTO){
            return ResponseEntity.ok(locationService.create(locationDTO));
    }

    @DeleteMapping("/{locationId}")
    public boolean deleteLocation(@Valid @PathVariable Long locationId){
        if(!locationService.existsById(locationId)){
            return true;
        }
        locationService.deleteById(locationId);
        return !locationService.existsById(locationId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseLocationDTO>> getAllLocation(){
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping(value = "/{locationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseLocationDTO> getLocationById(@Valid @PathVariable Long locationId){
        if(locationService.findById(locationId) == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(locationService.findById(locationId));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseLocationDTO> updateLocation(@Valid @RequestBody UpdateLocationDTO locationDTO){
            ResponseLocationDTO updatedLocation = locationService.update(locationDTO);
                if(updatedLocation == null){
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(updatedLocation);

    }
}
