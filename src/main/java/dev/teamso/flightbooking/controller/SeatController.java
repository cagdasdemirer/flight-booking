package dev.teamso.flightbooking.controller;


import dev.teamso.flightbooking.model.dto.SeatCreateAndUpdateRequest;
import dev.teamso.flightbooking.model.entities.Seat;
import dev.teamso.flightbooking.service.SeatService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seats")
@Tag(name = "Seat Controller", description = "Controller for managing seats")
public class SeatController {
    private static final Logger logger = LoggerFactory.getLogger(SeatController.class);

    @Autowired
    private SeatService seatService;

    // Add Seat
    @PostMapping("/{flightId}")
    @Operation(summary = "Add a seat to a flight", description = "Add a seat to a flight by providing the flight ID and seat details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Seat added successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Flight not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    })
    public ResponseEntity<Seat> addSeat(@PathVariable Long flightId, @RequestBody SeatCreateAndUpdateRequest seat) {
        logger.info("Adding seat to flight with ID: {}", flightId);
        Seat newSeat = seatService.addSeat(flightId, seat);
        logger.info("Seat added successfully with ID: {}", newSeat.getId());
        return new ResponseEntity<>(newSeat, HttpStatus.CREATED);
    }

    // Update Seat
    @PutMapping("/{flightId}/{seatNumber}")
    @Operation(summary = "Update a seat in a flight", description = "Update a seat in a flight by providing the flight ID, seat number and seat details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seat updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Flight or seat not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    })
    public ResponseEntity<Seat> updateSeat(@PathVariable Long flightId, @PathVariable int seatNumber, @RequestBody SeatCreateAndUpdateRequest seat) {
        logger.info("Updating seat with number: {} in flight with ID: {}", seatNumber, flightId);
        Seat updatedSeat = seatService.updateSeat(flightId, seatNumber, seat);
        logger.info("Seat updated successfully: {}", updatedSeat);
        return new ResponseEntity<>(updatedSeat, HttpStatus.OK);
    }

    // Delete Seat
    @DeleteMapping("/{flightId}/{seatNumber}")
    @Operation(summary = "Delete a seat from a flight", description = "Delete a seat from a flight by providing the flight ID and seat number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seat deleted successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Flight or seat not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    })
    public ResponseEntity<String> deleteSeat(@PathVariable Long flightId, @PathVariable int seatNumber) {
        logger.info("Deleting seat with ID: {} from flight with ID: {}", seatNumber, flightId);
        seatService.deleteSeat(flightId, seatNumber);
        logger.info("Seat deleted successfully.");
        return new ResponseEntity<>("Seat deleted successfully.", HttpStatus.OK);
    }
}
