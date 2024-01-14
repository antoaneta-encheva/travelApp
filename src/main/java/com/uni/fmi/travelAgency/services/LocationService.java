package com.uni.fmi.travelAgency.services;

import com.uni.fmi.travelAgency.dtos.holidayDtos.ResponseHolidayDTO;
import com.uni.fmi.travelAgency.dtos.locationDtos.CreateLocationDTO;
import com.uni.fmi.travelAgency.dtos.locationDtos.ResponseLocationDTO;
import com.uni.fmi.travelAgency.dtos.locationDtos.UpdateLocationDTO;
import com.uni.fmi.travelAgency.entities.Location;
import com.uni.fmi.travelAgency.exceptions.BadRequestException;
import com.uni.fmi.travelAgency.repositories.LocationRepository;
import com.uni.fmi.travelAgency.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;
   // @Autowired
  //  HolidayService holidayService;

    public LocationService(){
//        Location location1 = new Location("pobeda", "43", "Plovdiv", "Bulgaria", "");
//        Location location2 = new Location("osvobozhdenie", "87", "Ruse", "Bulgaria", "");
//        Location location3 = new Location("svoboda", "65", "Varna", "Bulgaria", "");
//        locationRepository.save(location1);
//        locationRepository.save(location2);
//        locationRepository.save(location3);
    }

    public ResponseLocationDTO create(CreateLocationDTO locationDTO){
        Location location = convertToEntity(locationDTO);
        Location createdLocation = locationRepository.save(location);
        return createdLocation.toResponseDto();
    }

    public ResponseLocationDTO findById(Long id){
        if(!locationRepository.findById(id).isPresent()){
            throw new NotFoundException("Locations not found");
        }
        return  locationRepository.findById(id).get().toResponseDto();
    }

    public Location findByIdLoc(Long id){
        if(!locationRepository.findById(id).isPresent()){
            throw new NotFoundException("Locations not found");
        }
        return  locationRepository.findById(id).get();
    }


    public List<ResponseLocationDTO> findAll(){
        List<Location> locations = locationRepository.findAll();
        List<ResponseLocationDTO> locationsDtos = new ArrayList<>();
        if(locations.size() == 0){
            throw new NotFoundException("Locations not found");
        }
        for(Location location: locations){
            locationsDtos.add(location.toResponseDto());
        }
        return locationsDtos;
    }

    public void delete(Location location){
        if(findById(location.getId()) != null){
            locationRepository.delete(location);
        }else{
            throw new NotFoundException("Location not found");
        }
    }

    public void deleteById(Long id){
    //    List<ResponseHolidayDTO> holidayDTOS = holidayService.findAll();
        if(existsById(id)){
//            for(ResponseHolidayDTO holidayDTO : holidayDTOS){
//                if(holidayDTO.getLocation().getId() == id){
//                    throw new BadRequestException("You can not remove location that is in use");
//                }
//            }
            locationRepository.deleteById(id);
        }
    }
    public boolean existsById(Long id){
        return locationRepository.existsById(id);
    }
    public ResponseLocationDTO update(UpdateLocationDTO locationDTO){
        Location location = convertToEntity(locationDTO);
        Optional<Location> locationToUpdate = locationRepository.findById(locationDTO.getId());
        if(locationToUpdate.isPresent()){
            throw new NotFoundException("Location not found");
        }

        if(location.getCity() != null){
            locationToUpdate.get().setCity(location.getCity());
        }
        if(location.getCountry() != null){
            locationToUpdate.get().setCountry(location.getCountry());
        }
        if(location.getImageUrl() != null){
            locationToUpdate.get().setImageUrl(location.getImageUrl());
        }
        if(location.getStreet() != null){
            locationToUpdate.get().setStreet(location.getStreet());
        }
        if(location.getNumber() != null){
            locationToUpdate.get().setNumber(location.getNumber());
        }
        Location updated = locationRepository.save(locationToUpdate.get());
        return updated.toResponseDto();
    }

    private Location convertToEntity(CreateLocationDTO locationDTO) throws BadRequestException {
        if(locationDTO.getCity() == null && locationDTO.getCountry() == null && locationDTO.getImageUrl() == null
                && locationDTO.getNumber() == null && locationDTO.getStreet() == null){
            throw new BadRequestException("Incorrect Body");
        }
        Location location = new Location();
        location.setNumber(locationDTO.getNumber());
        location.setStreet(locationDTO.getStreet());
        location.setCountry(locationDTO.getCountry());
        location.setCity(locationDTO.getCity());
        location.setImageUrl(locationDTO.getImageUrl());
        return location;
    }

    private Location convertToEntity(UpdateLocationDTO locationDTO) throws BadRequestException {
        if(locationDTO.getId() == null || locationDTO.getId() == 0){
            throw new NotFoundException("No id provided");
        }
        if(locationDTO.getCity() == null && locationDTO.getCountry() == null && locationDTO.getImageUrl() == null
                && locationDTO.getNumber() == null && locationDTO.getStreet() == null ){
            throw new BadRequestException("Incorrect Body");
        }
        Location location = new Location();
        location.setId(locationDTO.getId());
        location.setNumber(locationDTO.getNumber());
        location.setStreet(locationDTO.getStreet());
        location.setCountry(locationDTO.getCountry());
        location.setCity(locationDTO.getCity());
        location.setImageUrl(locationDTO.getImageUrl());
        return location;
    }
}
