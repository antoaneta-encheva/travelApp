package com.uni.fmi.travelAgency.controllers;

import com.uni.fmi.travelAgency.dtos.holidayDtos.CreateHolidayDTO;
import com.uni.fmi.travelAgency.dtos.holidayDtos.ResponseHolidayDTO;
import com.uni.fmi.travelAgency.dtos.holidayDtos.UpdateHolidayDTO;
import com.uni.fmi.travelAgency.entities.Holiday;
import com.uni.fmi.travelAgency.services.HolidayService;
import com.uni.fmi.travelAgency.services.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/holidays")
@Tag(name = "Holiday service", description = "Provides Holiday service API's")
public class HolidayController {

    @Autowired
    HolidayService holidayService;

    @PostMapping
    public ResponseEntity<ResponseHolidayDTO> createHoliday( @Valid @RequestBody CreateHolidayDTO holidayDTO){
        ResponseHolidayDTO holiday = holidayService.create(holidayDTO);
        return ResponseEntity.ok(holiday);
    }

    @DeleteMapping("/{holidayId}")
    public boolean deleteHoliday(@PathVariable Long holidayId){
        if(!holidayService.existsById(holidayId)){
            return true;
        }
        holidayService.deleteById(holidayId);
        return !holidayService.existsById(holidayId);
    }

    @GetMapping
    public ResponseEntity<List<ResponseHolidayDTO>> getAllHolidays(){
        return ResponseEntity.ok(holidayService.findAll());
    }

    @GetMapping("/{holidayId}")
    public ResponseEntity<ResponseHolidayDTO> getHolidayById(@PathVariable Long holidayId){
        if(!holidayService.existsById(holidayId)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(holidayService.findById(holidayId));
    }

    @PutMapping
    public ResponseEntity<ResponseHolidayDTO> updateHoliday(@Valid@RequestBody UpdateHolidayDTO holidayDTO){
        ResponseHolidayDTO updatedHoliday = holidayService.update(holidayDTO);
        if(updatedHoliday == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedHoliday);
    }
}
