package com.uni.fmi.travelAgency.services;

import com.uni.fmi.travelAgency.dtos.reservationDtos.CreateReservationDTO;
import com.uni.fmi.travelAgency.dtos.reservationDtos.ResponseReservationDTO;
import com.uni.fmi.travelAgency.dtos.reservationDtos.UpdateReservationDTO;
import com.uni.fmi.travelAgency.entities.Holiday;
import com.uni.fmi.travelAgency.entities.Reservation;
import com.uni.fmi.travelAgency.exceptions.BadRequestException;
import com.uni.fmi.travelAgency.exceptions.NotFoundException;
import com.uni.fmi.travelAgency.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private HolidayService holidayService;

    public ResponseReservationDTO create(CreateReservationDTO reservationDTO){
        Reservation reservation = convertToEntity(reservationDTO);
        Reservation created = reservationRepository.save(reservation);
        return created.toResponseDto();
    }

    public ResponseReservationDTO findById(Long id){
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if(reservation.isPresent()){
            Holiday holiday = holidayService.findByIdHol(reservation.get().getHoliday().getId());
            reservation.get().setHoliday(holiday);
            return reservation.get().toResponseDto();
        }else{
            throw new NotFoundException("No reservation found!");
        }
    }
    public Reservation findByIdRes(Long id){
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if(reservation.isPresent()){
            Holiday holiday = holidayService.findByIdHol(reservation.get().getHoliday().getId());
            reservation.get().setHoliday(holiday);
            return reservation.get();
        }else{
            throw new NotFoundException("No reservation found!");
        }
    }

    public List<ResponseReservationDTO> findAll(){
        List<Reservation> reservations = reservationRepository.findAll();
        List<ResponseReservationDTO> responseReservationDTO = new ArrayList<>();
        if(reservations.size() == 0){
            throw new NotFoundException("No reservations found!");
        }else{
            for(Reservation reservation: reservations){
                reservation = findByIdRes(reservation.getId());
                responseReservationDTO.add(reservation.toResponseDto());
            }
            return responseReservationDTO;
        }
    }

    public boolean delete(Reservation reservation){
        Optional<Reservation> reservationToDelete = reservationRepository.findById(reservation.getId());
        if(reservationToDelete.isPresent()){
            reservationRepository.delete(reservationToDelete.get());
            return existsById(reservation.getId());
        }else{
            return existsById(reservation.getId());
        }
    }

    public void deleteById(Long id){
        if(existsById(id)){
            reservationRepository.deleteById(id);
        }
    }
    public boolean existsById(Long id){
        return reservationRepository.existsById(id);
    }

    public ResponseReservationDTO update(UpdateReservationDTO reservationDTO){
        Reservation reservation = convertToEntity(reservationDTO);
        Reservation reservationToUpdate = findByIdRes(reservation.getId());
        if(reservationToUpdate == null){
            throw new NotFoundException("Reservation not found");
        }

        if(reservation.getContactName() != null){
            reservationToUpdate.setContactName(reservation.getContactName());
        }
        if(reservation.getPhoneNumber() != null ){
            reservationToUpdate.setPhoneNumber(reservation.getPhoneNumber());
        }
        if(!reservation.getHoliday().equals(null)){
            reservationToUpdate.setHoliday(reservation.getHoliday());
        }
        Reservation updated = reservationRepository.save(reservationToUpdate);
        return updated.toResponseDto();
    }

    private Reservation convertToEntity(CreateReservationDTO reservationDTO) throws BadRequestException {
        if(reservationDTO.getHoliday() == 0){
            throw new NotFoundException("Location not found!");
        }
        Holiday holiday = holidayService.findByIdHol(reservationDTO.getHoliday());

        if(reservationDTO.getHoliday() == 0 && reservationDTO.getContactName() == null && reservationDTO.getPhoneNumber() == null ){
            throw new BadRequestException("Bad Request!");
        }
        Reservation reservation = new Reservation();
        reservation.setPhoneNumber(reservationDTO.getPhoneNumber());
        reservation.setContactName(reservationDTO.getContactName());
        reservation.setHoliday(holiday);
        return reservation;
    }

    private Reservation convertToEntity(UpdateReservationDTO reservationDTO) throws BadRequestException {
        if(reservationDTO.getId() == 0){
            throw new NotFoundException("Location not found!");
        }

        if(reservationDTO.getHoliday() == 0){
            throw new BadRequestException("Bad Request!");
        }
        Holiday holiday = holidayService.findByIdHol(reservationDTO.getHoliday());

        if(reservationDTO.getHoliday() == 0 && reservationDTO.getContactName() == null && reservationDTO.getPhoneNumber() == null ){
            throw new BadRequestException("Bad Request!");
        }
        Reservation reservation = new Reservation();
        reservation.setPhoneNumber(reservationDTO.getPhoneNumber());
        reservation.setContactName(reservationDTO.getContactName());
        reservation.setHoliday(holiday);
        return reservation;
    }
}
