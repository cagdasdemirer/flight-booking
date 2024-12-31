package dev.teamso.flightbooking.repository;

import dev.teamso.flightbooking.model.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFlightRepository extends JpaRepository<Flight, Long> {
}
