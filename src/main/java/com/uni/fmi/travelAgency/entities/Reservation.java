package com.uni.fmi.travelAgency.entities;

import com.uni.fmi.travelAgency.dtos.holidayDtos.ResponseHolidayDTO;
import com.uni.fmi.travelAgency.dtos.reservationDtos.ResponseReservationDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String contactName;
    @Size(max = 10)
    private String phoneNumber;

    @ManyToOne
    private Holiday holiday;

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Holiday getHoliday() {
        return holiday;
    }

    public void setHoliday(Holiday holiday) {
        this.holiday = holiday;
    }

    public ResponseReservationDTO toResponseDto(){
        return new ResponseReservationDTO(id, contactName, phoneNumber, holiday.toResponseDto());
    }
}
