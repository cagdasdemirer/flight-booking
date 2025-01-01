package dev.teamso.flightbooking.service;

import dev.teamso.flightbooking.exceptions.FlightNotFoundException;
import dev.teamso.flightbooking.exceptions.SeatNotFoundException;
import dev.teamso.flightbooking.model.dto.SeatCreateAndUpdateRequest;
import dev.teamso.flightbooking.model.entities.Flight;
import dev.teamso.flightbooking.model.entities.Seat;
import dev.teamso.flightbooking.repository.JpaFlightRepository;
import dev.teamso.flightbooking.repository.JpaSeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.OptionalInt;

@Service
public class SeatService {
    private static final Logger logger = LoggerFactory.getLogger(SeatService.class);

    @Autowired
    private JpaSeatRepository seatRepository;

    @Autowired
    private JpaFlightRepository flightRepository;

    @Transactional
    public Seat addSeat(Long flightId, SeatCreateAndUpdateRequest seat) {
        logger.debug("Adding seat to flight with ID: {}", flightId);
        Flight flight = flightRepository.findById(flightId).orElseGet(() -> {
            logger.error("Flight with ID: {} not found", flightId);
            throw new FlightNotFoundException("Flight with id " + flightId + " not found.");
        });

        int seatNumber = 1;
        OptionalInt maxSeatNumber = flight.getMaxSeatNumber();
        if (maxSeatNumber.isPresent()) {
            seatNumber = maxSeatNumber.getAsInt() + 1;
        }

        Seat newSeat = new Seat(seatNumber, seat.getType(), seat.getPrice());

        newSeat.setFlight(flight);

        return seatRepository.save(newSeat);
    }

    @Transactional
    public Seat updateSeat(Long flightId, int seatNumber, SeatCreateAndUpdateRequest updatedSeat) {
        logger.debug("Updating seat with ID: {} in flight with ID: {}", seatNumber, flightId);
        Seat seat = seatRepository.findByFlightIdAndSeatNumber(flightId, seatNumber).orElseGet(() -> {
            logger.error("Flight with ID: {} - Seat Number: {} not found", flightId, seatNumber);
            throw new SeatNotFoundException("Seat "+seatNumber + " with flight_id " + flightId + " not found.");
        });

        seat.setType(updatedSeat.getType());
        seat.setPrice(updatedSeat.getPrice());

        return seatRepository.save(seat);
    }

    @Transactional
    public void deleteSeat(Long flightId, int seatId) {
        logger.debug("Deleting seat with ID: {} from flight with ID: {}", seatId, flightId);
        Seat seat = seatRepository.findByFlightIdAndSeatNumber(flightId, seatId).orElseGet(() -> {
            logger.error("Flight with ID: {} - Seat Number: {} not found", flightId, seatId);
            throw new SeatNotFoundException("Seat "+seatId + " with flight_id " + flightId + " not found.");
        });
        seatRepository.delete(seat);
    }
}
