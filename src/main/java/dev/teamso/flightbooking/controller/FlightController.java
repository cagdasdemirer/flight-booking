package dev.teamso.flightbooking.controller;

import dev.teamso.flightbooking.model.Flight;
import dev.teamso.flightbooking.model.FlightCreateRequest;
import dev.teamso.flightbooking.model.FlightDetailResponse;
import dev.teamso.flightbooking.model.FlightSummaryResponse;
import dev.teamso.flightbooking.model.FlightUpdateRequest;
import dev.teamso.flightbooking.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {
    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightService flightService;

    // Create Flight
    @PostMapping("/")
    public ResponseEntity<Flight> createFlight(@RequestBody FlightCreateRequest request) {
        logger.info("Creating a new flight with request: {}", request);
        Flight flight = flightService.createFlight(request);
        logger.info("Flight created successfully with ID: {}", flight.getId());
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }

    // Update Flight
    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody FlightUpdateRequest request) {
        logger.info("Updating flight with ID: {}", id);
        Flight flight = flightService.updateFlight(id, request);
        logger.info("Flight updated successfully: {}", flight);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    // Delete Flight
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        logger.info("Deleting flight with ID: {}", id);
        flightService.deleteFlight(id);
        logger.info("Flight deleted successfully.");
        return new ResponseEntity<>("Flight deleted successfully.", HttpStatus.OK);
    }

    // Get Flight by ID
    @GetMapping("/{id}")
    public ResponseEntity<FlightDetailResponse> getFlightById(@PathVariable Long id) {
        logger.info("Fetching flight details for ID: {}", id);
        FlightDetailResponse response = flightService.getFlightById(id);
        logger.info("Flight details retrieved successfully for ID: {}", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get All Flights
    @GetMapping("/all")
    public ResponseEntity<List<FlightSummaryResponse>> getAllFlights() {
        logger.info("Fetching all flights.");
        List<FlightSummaryResponse> response = flightService.getAllFlights();
        logger.info("All flights retrieved successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
