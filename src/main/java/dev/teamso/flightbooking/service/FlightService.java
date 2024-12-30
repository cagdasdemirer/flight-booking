package dev.teamso.flightbooking.service;

import dev.teamso.flightbooking.exceptions.FlightBadRequestException;
import dev.teamso.flightbooking.exceptions.FlightNotFoundException;
import dev.teamso.flightbooking.model.Flight;
import dev.teamso.flightbooking.model.FlightCreateRequest;
import dev.teamso.flightbooking.model.FlightDetailResponse;
import dev.teamso.flightbooking.model.FlightSummaryResponse;
import dev.teamso.flightbooking.model.FlightUpdateRequest;
import dev.teamso.flightbooking.model.Seat;
import dev.teamso.flightbooking.model.SeatType;
import dev.teamso.flightbooking.repository.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FlightService {

    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);

    @Autowired
    private FlightRepository flightRepository;

    private long flightIdCounter = 1;

    public Flight createFlight(FlightCreateRequest request) {
        logger.debug("Generating seats for the new flight.");
        validateFlightTimes(request);
        List<Seat> seats = new ArrayList<>();

        for (int i = 0; i < request.getEconomySeatCount(); i++) {
            seats.add(new Seat(UUID.randomUUID(), SeatType.ECONOMY, request.getDefaultEconomyPrice()));
        }
        for (int i = 0; i < request.getBusinessSeatCount(); i++) {
            seats.add(new Seat(UUID.randomUUID(), SeatType.BUSINESS, request.getDefaultBusinessPrice()));
        }

        Flight flight = new Flight(
                request.getId(),
                request.getName(),
                request.getDeparture(),
                request.getDepartureAt(),
                request.getArrival(),
                request.getArriveAt(),
                seats
        );
        logger.debug("Saving the new flight to the repository.");
        return flightRepository.save(flight);
    }

    public Flight updateFlight(Long id, FlightUpdateRequest request) {
        logger.debug("Looking for flight with ID: {} to update.", id);
        Flight flight = flightRepository.findById(id);
        if (flight == null) {
            logger.error("Flight with ID: {} not found.", id);
            throw new FlightNotFoundException("Flight with id " + id + " not found.");
        }

        flight.setName(request.getName());
        flight.setDeparture(request.getDeparture());
        flight.setDepartureAt(request.getDepartureAt());
        flight.setArrival(request.getArrival());
        flight.setArriveAt(request.getArriveAt());
        logger.debug("Flight with ID: {} updated successfully.", id);
        return flight;
    }

    public void deleteFlight(Long id) {
        logger.debug("Checking if flight with ID: {} exists for deletion.", id);
        if (!flightRepository.existsById(id)) {
            logger.error("Flight with ID: {} not found for deletion.", id);
            throw new FlightNotFoundException("Flight with id " + id + " not found.");
        }
        flightRepository.delete(id);
        logger.debug("Flight with ID: {} deleted successfully.", id);
    }

    public FlightDetailResponse getFlightById(Long id) {
        logger.debug("Fetching flight with ID: {}.", id);
        Flight flight = flightRepository.findById(id);
        if (flight == null) {
            logger.error("Flight with ID: {} not found.", id);
            throw new FlightNotFoundException("Flight with id " + id + " not found.");
        }

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

    private void validateFlightTimes(FlightCreateRequest request) {
        if (request.getArriveAt().isBefore(request.getDepartureAt())) {
            logger.error("Arrival time {} is before departure time {}", request.getArriveAt(), request.getDepartureAt());
            throw new FlightBadRequestException("Arrival time cannot be before departure time.");
        }
    }
}
