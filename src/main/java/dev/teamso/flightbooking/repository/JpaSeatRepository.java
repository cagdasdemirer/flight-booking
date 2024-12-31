package dev.teamso.flightbooking.repository;

import dev.teamso.flightbooking.model.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaSeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByFlightIdAndSeatNumber(Long flightId, int seatNumber);
}
