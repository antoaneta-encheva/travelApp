package com.uni.fmi.travelAgency.entities;

import com.uni.fmi.travelAgency.dtos.holidayDtos.ResponseHolidayDTO;
import com.uni.fmi.travelAgency.dtos.locationDtos.ResponseLocationDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "holidays")
@Table(name = "holidays")
public class Holiday implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private LocalDate startDate;
    private int duration;
    private double price;
    private int freeSlots;

    @ManyToOne
    private Location location;

    public Holiday() {
    }

    public Holiday(String title, LocalDate startDate, int duration, double price, int freeSlots, Location location) {
        this.title = title;
        this.startDate = startDate;
        this.duration = duration;
        this.price = price;
        this.freeSlots = freeSlots;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFreeSlots() {
        return freeSlots;
    }

    public void setFreeSlots(int freeSlots) {
        this.freeSlots = freeSlots;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ResponseHolidayDTO toResponseDto(){
        return new ResponseHolidayDTO(id, location.toResponseDto(), title, startDate, duration, price, freeSlots);
    }

}
