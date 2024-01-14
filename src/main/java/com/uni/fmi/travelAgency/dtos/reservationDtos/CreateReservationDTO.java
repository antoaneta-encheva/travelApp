package com.uni.fmi.travelAgency.dtos.reservationDtos;

import jakarta.validation.constraints.Pattern;

public class CreateReservationDTO {
    private String contactName;
    @Pattern(regexp = "^\\d{0,10}$", message = "The number is invalid.")
    private String phoneNumber;
    private Long holiday;

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
