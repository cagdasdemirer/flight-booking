package dev.teamso.flightbooking.controller;

import dev.teamso.flightbooking.model.entities.Flight;
import dev.teamso.flightbooking.model.dto.FlightCreateRequest;
import dev.teamso.flightbooking.model.dto.FlightDetailResponse;
import dev.teamso.flightbooking.model.dto.FlightSummaryResponse;
import dev.teamso.flightbooking.model.dto.FlightUpdateRequest;
import dev.teamso.flightbooking.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Flight Controller", description = "Controller for managing flights")
public class FlightController {
    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightService flightService;

    // Create Flight
    @PostMapping("/")
    @Operation(summary = "Create a new flight", description = "Create a new flight by providing the flight details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Flight created successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "409", description = "Flight already exists."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    })
    public ResponseEntity<Flight> createFlight(@RequestBody FlightCreateRequest request) {
        logger.info("Creating a new flight with request: {}", request);
        Flight flight = flightService.createFlight(request);
        logger.info("Flight created successfully with ID: {}", flight.getId());
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }

    // Update Flight
    @PutMapping("/{id}")
    @Operation(summary = "Update a flight", description = "Update a flight by providing the flight ID and details to update.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Flight not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    })
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody FlightUpdateRequest request) {
        logger.info("Updating flight with ID: {}", id);
        Flight flight = flightService.updateFlight(id, request);
        logger.info("Flight updated successfully: {}", flight);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    // Delete Flight
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a flight", description = "Delete a flight by providing the flight ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight deleted successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Flight not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    })
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        logger.info("Deleting flight with ID: {}", id);
        flightService.deleteFlight(id);
        logger.info("Flight deleted successfully.");
        return new ResponseEntity<>("Flight deleted successfully.", HttpStatus.OK);
    }

    // Get Flight by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get flight details", description = "Get flight details by providing the flight ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight details retrieved successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Flight not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    })
    public ResponseEntity<FlightDetailResponse> getFlightById(@PathVariable Long id) {
        logger.info("Fetching flight details for ID: {}", id);
        FlightDetailResponse response = flightService.getFlightById(id);
        logger.info("Flight details retrieved successfully for ID: {}", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get All Flights
    @GetMapping("/all")
    @Operation(summary = "Get all flights", description = "Get all flights in summary format.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All flights retrieved successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    })
    public ResponseEntity<List<FlightSummaryResponse>> getAllFlights() {
        logger.info("Fetching all flights.");
        List<FlightSummaryResponse> response = flightService.getAllFlights();
        logger.info("All flights retrieved successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
