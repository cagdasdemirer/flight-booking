package dev.teamso.flightbooking.service;

import dev.teamso.flightbooking.model.entities.Seat;
import dev.teamso.flightbooking.repository.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {
    private static final Logger logger = LoggerFactory.getLogger(SeatService.class);

    @Autowired
    private FlightRepository flightRepository;

    public Seat addSeat(Long flightId, Seat seat) {
        logger.debug("Adding seat to flight with ID: {}", flightId);
        return flightRepository.addSeat(flightId, seat);
    }

    public Seat updateSeat(Long flightId, Long seatId, Seat seat) {
        logger.debug("Updating seat with ID: {} in flight with ID: {}", seatId, flightId);
        return flightRepository.updateSeat(flightId, seatId, seat);
    }

    public void deleteSeat(Long flightId, Long seatId) {
        logger.debug("Deleting seat with ID: {} from flight with ID: {}", seatId, flightId);
        flightRepository.deleteSeat(flightId, seatId);
    }
}
