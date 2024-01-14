package com.uni.fmi.travelAgency.dtos.locationDtos;

import jakarta.validation.constraints.Pattern;

public class UpdateLocationDTO {
    private Long id;
    private String street;
    @Pattern(regexp = "^\\d{1,5}$", message = "The number is invalid.")
    private String number;
    private String city;
    private String country;
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
