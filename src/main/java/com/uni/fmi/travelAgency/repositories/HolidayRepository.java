package com.uni.fmi.travelAgency.repositories;

import com.uni.fmi.travelAgency.entities.Holiday;
import com.uni.fmi.travelAgency.entities.Location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    Holiday save(Holiday holiday);

    Optional<Holiday> findById(Long id);

    List<Holiday> findByLocation(Location location);
    List<Holiday> findByDuration(int duration);
    List<Holiday> findByStartDate(LocalDate startDate);
    @Query("SELECT h FROM holidays h" +
            " WHERE h.location.id = :locationId and h.duration = :duration")
    List<Holiday> findByLocationAndDuration(@Param("locationId")Long location,  @Param("duration")int duration);
    @Query("SELECT h FROM holidays h" +
            " WHERE h.location.id = :locationId and h.startDate = :startDate")
    List<Holiday> findByLocationAndStartDate(@Param("locationId")Long location,@Param("startDate") LocalDate startDate);
    @Query("SELECT h FROM holidays h" +
            " WHERE h.location.id = :locationId and h.duration = :duration and h.startDate = :startDate")
    List<Holiday> findByLocationAndDurationAndStartDate(@Param("locationId")Long location,@Param("duration")int duration, @Param("startDate")LocalDate startDate);
    @Query("SELECT h FROM holidays h" +
            " WHERE h.duration = :duration and h.startDate = :startDate")
    List<Holiday> findByDurationAndStartDate(int duration, LocalDate startDate);

    List<Holiday> findAll();

    void delete(Holiday holiday);

    void deleteById(Long id);
    boolean existsById(Long id);
}
