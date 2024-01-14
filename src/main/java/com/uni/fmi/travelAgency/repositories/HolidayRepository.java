package com.uni.fmi.travelAgency.repositories;

import com.uni.fmi.travelAgency.entities.Holiday;
import com.uni.fmi.travelAgency.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    Holiday save(Holiday holiday);

    Optional<Holiday> findById(Long id);

    List<Holiday> findAll();

    void delete(Holiday holiday);

    void deleteById(Long id);
    boolean existsById(Long id);
}
