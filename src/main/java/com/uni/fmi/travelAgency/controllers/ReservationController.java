package com.uni.fmi.travelAgency.controllers;

import com.uni.fmi.travelAgency.dtos.reservationDtos.CreateReservationDTO;
import com.uni.fmi.travelAgency.dtos.reservationDtos.ResponseReservationDTO;
import com.uni.fmi.travelAgency.dtos.reservationDtos.UpdateReservationDTO;
import com.uni.fmi.travelAgency.entities.Reservation;
import com.uni.fmi.travelAgency.services.HolidayService;
import com.uni.fmi.travelAgency.services.ReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reservations")
@Tag(name = "Reservation service", description = "Provides Reservation service API's")
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ResponseReservationDTO> createReservation(@Valid  @RequestBody CreateReservationDTO reservationDTO){
        ResponseReservationDTO reservation = reservationService.create(reservationDTO);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{reservationId}")
    public boolean deleteReservation(@PathVariable Long reservationId){
        if(!reservationService.existsById(reservationId)){
            return true;
        }
        reservationService.deleteById(reservationId);
        return !reservationService.existsById(reservationId);
    }

    @GetMapping
    public ResponseEntity<List<ResponseReservationDTO>> getAllReservationS(){
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ResponseReservationDTO> getReservationById(@PathVariable Long reservationId){
        if(!reservationService.existsById(reservationId)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationService.findById(reservationId));
    }

    @PutMapping
    public ResponseEntity<ResponseReservationDTO> updateReservation(@Valid @RequestBody UpdateReservationDTO reservationDTO){
        ResponseReservationDTO updatedReservation = reservationService. update(reservationDTO);
        if(updatedReservation == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedReservation);
    }
}
