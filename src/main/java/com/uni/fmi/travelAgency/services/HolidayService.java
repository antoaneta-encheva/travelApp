package com.uni.fmi.travelAgency.services;

import com.uni.fmi.travelAgency.dtos.holidayDtos.CreateHolidayDTO;
import com.uni.fmi.travelAgency.dtos.holidayDtos.ResponseHolidayDTO;
import com.uni.fmi.travelAgency.dtos.holidayDtos.UpdateHolidayDTO;
import com.uni.fmi.travelAgency.dtos.reservationDtos.ResponseReservationDTO;
import com.uni.fmi.travelAgency.entities.Holiday;
import com.uni.fmi.travelAgency.entities.Location;
import com.uni.fmi.travelAgency.exceptions.BadRequestException;
import com.uni.fmi.travelAgency.exceptions.NotFoundException;
import com.uni.fmi.travelAgency.repositories.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HolidayService {
    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private LocationService locationService;

    public ResponseHolidayDTO create(CreateHolidayDTO holidayDTO){
        Holiday holiday = convertToEntity(holidayDTO);
        Holiday createdHoliday = holidayRepository.save(holiday);
        return createdHoliday.toResponseDto();
    }

    public ResponseHolidayDTO findById(Long id){
        Optional<Holiday> holiday = holidayRepository.findById(id);
        if(holiday.isPresent()){
            Location location = locationService.findByIdLoc(holiday.get().getLocation().getId());
            holiday.get().setLocation(location);
            return holiday.get().toResponseDto();
        }else{
            throw new NotFoundException("No holiday found!");
        }
    }
    public Holiday findByIdHol(Long id){
        Optional<Holiday> holiday = holidayRepository.findById(id);
        if(holiday.isPresent()){
            Location location = locationService.findByIdLoc(holiday.get().getLocation().getId());
            holiday.get().setLocation(location);
            return holiday.get();
        }else{
            throw new NotFoundException("No holiday found!");
        }
    }
    public List<ResponseHolidayDTO> findAll(){
        List<Holiday> holidays = holidayRepository.findAll();
        List<ResponseHolidayDTO> responseHolidayDTOS = new ArrayList<>();
        if(holidays.size() == 0){
            throw new NotFoundException("NO holiday found!");
        }else{
            for(Holiday holiday: holidays){
                holiday = findByIdHol(holiday.getId());
                responseHolidayDTOS.add(holiday.toResponseDto());
            }
            return responseHolidayDTOS;
        }
    }

    public boolean delete(Holiday holiday){
        Optional<Holiday> holidayToDelete = holidayRepository.findById(holiday.getId());
        if(holidayToDelete.isPresent()){
            holidayRepository.delete(holidayToDelete.get());
            return existsById(holiday.getId());
        }else{
            return existsById(holiday.getId());
        }
    }

    public void deleteById(Long id){
        if(existsById(id)){
            holidayRepository.deleteById(id);
        }
    }
    public boolean existsById(Long id){
        return holidayRepository.existsById(id);
    }

    public ResponseHolidayDTO update(UpdateHolidayDTO holidayDTO){
        Holiday holiday = convertToEntity(holidayDTO);
        Holiday holidayToUpdate = findByIdHol(holiday.getId());
        if(holidayToUpdate == null){
            throw new NotFoundException("Holiday not found");
        }

        if(holiday.getDuration() == 0){
            holidayToUpdate.setDuration(holiday.getDuration());
        }
        if(holiday.getFreeSlots() > 0 ){
            holidayToUpdate.setFreeSlots(holiday.getFreeSlots());
        }
        if(holiday.getLocation() != null){
            holidayToUpdate.setLocation(holiday.getLocation());
        }
        if(holiday.getPrice() > 0){
            holidayToUpdate.setPrice(holiday.getPrice());
        }
        if(holiday.getTitle() != null){
            holidayToUpdate.setTitle(holiday.getTitle());
        }

        if(holiday.getStartDate().isAfter(LocalDate.now())){
            holidayToUpdate.setStartDate(holiday.getStartDate());
        }
        Holiday updated = holidayRepository.save(holidayToUpdate);
        return updated.toResponseDto();
    }

    private Holiday convertToEntity(CreateHolidayDTO holidayDTO) throws BadRequestException {
        if(holidayDTO.getLocation() == 0){
            throw new NotFoundException("Location not found!");
        }
        Location location = locationService.findByIdLoc(holidayDTO.getLocation());

        if(holidayDTO.getDuration() == 0 && holidayDTO.getLocation() == null && holidayDTO.getFreeSlots() == 0
                && holidayDTO.getPrice() == 0 && holidayDTO.getTitle() == null && holidayDTO.getStartDate() == null ){
            throw new BadRequestException("Bad Request!");
        }
        Holiday holiday = new Holiday(holidayDTO.getTitle(), holidayDTO.getStartDate(), holidayDTO.getDuration(), holidayDTO.getPrice(), holidayDTO.getFreeSlots(), location);

        return holiday;
    }

    private Holiday convertToEntity(UpdateHolidayDTO holidayDTO) throws BadRequestException {
        if(holidayDTO.getId() == null || holidayDTO.getId() == 0){
            throw new NotFoundException("No id provided");
        }
        if(holidayDTO.getDuration() == 0 && holidayDTO.getLocation() == null && holidayDTO.getFreeSlots() == 0
                && holidayDTO.getPrice() == 0 && holidayDTO.getTitle() == null && holidayDTO.getStartDate() == null ){
            throw new BadRequestException("Incorrect Body");
        }
        Location location = locationService.findByIdLoc(holidayDTO.getLocation());
        Holiday holiday = new Holiday(holidayDTO.getTitle(), holidayDTO.getStartDate(), holidayDTO.getDuration(), holidayDTO.getPrice(), holidayDTO.getFreeSlots(), location);

        return holiday;
    }
}
