package com.uni.fmi.travelAgency.dtos.reservationDtos;

import jakarta.validation.constraints.Pattern;

public class UpdateReservationDTO {
    private Long id;
    private String contactName;
    @Pattern(regexp = "^\\d{1,5}$", message = "The number is invalid.")
    private String phoneNumber;
    private Long holiday;

    public Long getId() {
        return id;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getHoliday() {
        return holiday;
    }
}
