package dev.teamso.flightbooking.repository;

import dev.teamso.flightbooking.model.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaFlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByName(String name);
}
