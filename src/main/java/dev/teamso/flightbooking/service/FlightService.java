package dev.teamso.flightbooking.service;

import dev.teamso.flightbooking.exceptions.FlightAlreadyExistException;
import dev.teamso.flightbooking.exceptions.FlightBadRequestException;
import dev.teamso.flightbooking.exceptions.FlightNotFoundException;
import dev.teamso.flightbooking.model.entities.Flight;
import dev.teamso.flightbooking.model.dto.FlightCreateRequest;
import dev.teamso.flightbooking.model.dto.FlightDetailResponse;
import dev.teamso.flightbooking.model.dto.FlightSummaryResponse;
import dev.teamso.flightbooking.model.dto.FlightUpdateRequest;
import dev.teamso.flightbooking.model.entities.Seat;
import dev.teamso.flightbooking.model.entities.SeatType;
import dev.teamso.flightbooking.repository.JpaFlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);

    @Autowired
    private JpaFlightRepository flightRepository;

    @Transactional
    public Flight createFlight(FlightCreateRequest request) {
        logger.debug("Generating seats for the new flight.");
        validateFlightTimes(request);

        Optional<Flight> existingFlight = flightRepository.findByName(request.getName());
        if (existingFlight.isPresent()) {
            throw new FlightAlreadyExistException("Flight with name " + request.getName() + " already exists.");
        }

        List<Seat> seats = new ArrayList<>();

        Flight flight = new Flight(
                request.getName(),
                request.getDeparture(),
                request.getDepartureAt(),
                request.getArrival(),
                request.getArriveAt(),
                seats
        );

        for (int i = 1; i < request.getEconomySeatCount()+1; i++) {
            Seat seat = new Seat(i, SeatType.ECONOMY, request.getDefaultEconomyPrice());
            seat.setFlight(flight);
            seats.add(seat);
        }
        for (int i = request.getEconomySeatCount()+1; i < request.getEconomySeatCount()+1+request.getBusinessSeatCount(); i++) {
            Seat seat = new Seat(i, SeatType.BUSINESS, request.getDefaultBusinessPrice());
            seat.setFlight(flight);
            seats.add(seat);
        }

        flight.setSeats(seats);
        logger.debug("Saving the new flight to the repository.");
        return flightRepository.save(flight);
    }

    @Transactional
    public Flight updateFlight(Long id, FlightUpdateRequest request) {
        logger.debug("Looking for flight with ID: {} to update.", id);
        Flight flight = flightRepository.findById(id).orElseGet(() -> {
            logger.error("Flight with ID: {} not found", id);
            throw new FlightNotFoundException("Flight with id " + id + " not found.");
        });

        validateFlightTimes(request);

        flight.setName(request.getName());
        flight.setDeparture(request.getDeparture());
        flight.setDepartureAt(request.getDepartureAt());
        flight.setArrival(request.getArrival());
        flight.setArriveAt(request.getArriveAt());
        logger.debug("Flight with ID: {} updated successfully.", id);
        return flightRepository.save(flight);
    }

    @Transactional
    public void deleteFlight(Long id) {
        logger.debug("Checking if flight with ID: {} exists for deletion.", id);
        Flight flight = flightRepository.findById(id).orElseGet(() -> {
            logger.error("Flight with ID: {} not found", id);
            throw new FlightNotFoundException("Flight with id " + id + " not found.");
        });
        flightRepository.delete(flight);
        logger.debug("Flight with ID: {} deleted successfully.", id);
    }

    public FlightDetailResponse getFlightById(Long id) {
        logger.debug("Fetching flight with ID: {}.", id);
        Flight flight = flightRepository.findById(id).orElseGet(() -> {
            logger.error("Flight with ID: {} not found", id);
            throw new FlightNotFoundException("Flight with id " + id + " not found.");
        });

        logger.debug("Returning flight details for ID: {}.", id);
        return new FlightDetailResponse(flight);
    }

    public List<FlightSummaryResponse> getAllFlights() {
        logger.debug("Fetching all flights from repository.");
        List<Flight> flights = flightRepository.findAll();
        List<FlightSummaryResponse> summaries = new ArrayList<>();

        for (Flight flight : flights) {
            summaries.add(new FlightSummaryResponse(flight));
        }
        logger.debug("Returning all flight summaries.");
        return summaries;
    }

    private void validateFlightTimes(Object request) {
        LocalDateTime departureAt = null;
        LocalDateTime arriveAt = null;

        if (request instanceof FlightCreateRequest) {
            departureAt = ((FlightCreateRequest) request).getDepartureAt();
            arriveAt = ((FlightCreateRequest) request).getArriveAt();
        }
        else if (request instanceof FlightUpdateRequest) {
            departureAt = ((FlightUpdateRequest) request).getDepartureAt();
            arriveAt = ((FlightUpdateRequest) request).getArriveAt();
        }

        if (departureAt != null && arriveAt != null && arriveAt.isBefore(departureAt)) {
            logger.error("Arrival time {} is before departure time {}", arriveAt, departureAt);
            throw new FlightBadRequestException("Arrival time cannot be before departure time.");
        }
    }
}
