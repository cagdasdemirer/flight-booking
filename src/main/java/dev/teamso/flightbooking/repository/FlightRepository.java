package dev.teamso.flightbooking.repository;

import dev.teamso.flightbooking.model.entities.Flight;
import dev.teamso.flightbooking.model.entities.Seat;
import dev.teamso.flightbooking.model.entities.SeatType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class FlightRepository {
    private static final Logger logger = LoggerFactory.getLogger(FlightRepository.class);

    private final Map<Long, Flight> flights = new HashMap<>();

    @PostConstruct
    public void init() {
        logger.info("Initializing repository with sample flights.");

        List<Seat> flight1Seats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            flight1Seats.add(new Seat(UUID.randomUUID(), SeatType.ECONOMY, 100.0));
        }
        for (int i = 0; i < 5; i++) {
            flight1Seats.add(new Seat(UUID.randomUUID(), SeatType.BUSINESS, 200.0));
        }
        LocalDateTime now = LocalDateTime.now();

        Flight flight1 = new Flight(1, "Flight A", "NYC", now, "LAX", now.plusHours(6), flight1Seats);

        List<Seat> flight2Seats = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            flight2Seats.add(new Seat(UUID.randomUUID(), SeatType.ECONOMY, 80.0));
        }
        for (int i = 0; i < 3; i++) {
            flight2Seats.add(new Seat(UUID.randomUUID(), SeatType.BUSINESS, 150.0));
        }
        Flight flight2 = new Flight(2, "Flight B", "SFO", now, "SEA", now.plusHours(2), flight2Seats);

        flights.put(flight1.getId(), flight1);
        flights.put(flight2.getId(), flight2);
        logger.info("Sample flights added to repository.");
    }

    public Flight save(Flight flight) {
        logger.debug("Saving flight with ID: {}.", flight.getId());
        flights.put(flight.getId(), flight);
        return flight;
    }

    public Flight findById(Long id) {
        logger.debug("Fetching flight with ID: {}.", id);
        return flights.get(id);
    }

    public List<Flight> findAll() {
        logger.debug("Fetching all flights.");
        return new ArrayList<>(flights.values());
    }

    public boolean existsById(Long id) {
        logger.debug("Checking if flight with ID: {} exists.", id);
        return flights.containsKey(id);
    }

    public void delete(Long id) {
        logger.debug("Deleting flight with ID: {}.", id);
        flights.remove(id);
    }

    public Seat addSeat(Long flightId, Seat seat) {
        logger.debug("Adding seat to flight with ID: {}", flightId);
        Flight flight = flights.get(flightId);
        flight.getSeats().add(seat);
        return seat;
    }

    public Seat updateSeat(Long flightId, Long seatId, Seat seat) {
        logger.debug("Updating seat with ID: {} in flight with ID: {}", seatId, flightId);
        Flight flight = flights.get(flightId);
        List<Seat> seats = flight.getSeats();
        for (int i = 0; i < seats.size(); i++) {
            if (seats.get(i).getId().equals(seatId)) {
                seats.set(i, seat);
                return seat;
            }
        }
        return null;
    }

    public void deleteSeat(Long flightId, Long seatId) {
        logger.debug("Deleting seat with ID: {} from flight with ID: {}", seatId, flightId);
        Flight flight = flights.get(flightId);
        List<Seat> seats = flight.getSeats();
        for (int i = 0; i < seats.size(); i++) {
            if (seats.get(i).getId().equals(seatId)) {
                seats.remove(i);
                return;
            }
        }
    }
}
