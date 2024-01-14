package com.uni.fmi.travelAgency.dtos.holidayDtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;

public class CreateHolidayDTO {
    private String title;
    private LocalDate startDate;
    @Min(value = 0, message = "Duration should not be less than 0")
    private int duration;
    @Min(value = 0, message = "Price should not be less than 0")
    private double price;
    @Min(value = 0, message = "Free slots should not be less than 0")
    private int freeSlots;
    private Long location;

    public String getTitle() {
        return title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }

    public int getFreeSlots() {
        return freeSlots;
    }

    public Long getLocation() {
        return location;
    }
}
