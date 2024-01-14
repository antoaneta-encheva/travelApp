package com.uni.fmi.travelAgency.repositories;

import com.uni.fmi.travelAgency.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location save(Location location);

    Optional<Location> findById(Long id);

    List<Location> findAll();

    void delete(Location location);

    void deleteById(Long location);
    boolean existsById(Long id);
}
