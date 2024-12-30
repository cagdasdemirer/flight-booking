package dev.teamso.flightbooking.controller;


import dev.teamso.flightbooking.model.entities.Seat;
import dev.teamso.flightbooking.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seats")
public class SeatController {
    private static final Logger logger = LoggerFactory.getLogger(SeatController.class);

    @Autowired
    private SeatService seatService;

    // Add Seat
    @PostMapping("/{flightId}")
    public ResponseEntity<Seat> addSeat(@PathVariable Long flightId, @RequestBody Seat seat) {
        logger.info("Adding seat to flight with ID: {}", flightId);
        Seat newSeat = seatService.addSeat(flightId, seat);
        logger.info("Seat added successfully with ID: {}", newSeat.getId());
        return new ResponseEntity<>(newSeat, HttpStatus.CREATED);
    }

    // Update Seat
    @PostMapping("/{flightId}/{seatId}")
    public ResponseEntity<Seat> updateSeat(@PathVariable Long flightId, @PathVariable Long seatId, @RequestBody Seat seat) {
        logger.info("Updating seat with ID: {} in flight with ID: {}", seatId, flightId);
        Seat updatedSeat = seatService.updateSeat(flightId, seatId, seat);
        logger.info("Seat updated successfully: {}", updatedSeat);
        return new ResponseEntity<>(updatedSeat, HttpStatus.OK);
    }

    // Delete Seat
    @PostMapping("/{flightId}/{seatId}")
    public ResponseEntity<String> deleteSeat(@PathVariable Long flightId, @PathVariable Long seatId) {
        logger.info("Deleting seat with ID: {} from flight with ID: {}", seatId, flightId);
        seatService.deleteSeat(flightId, seatId);
        logger.info("Seat deleted successfully.");
        return new ResponseEntity<>("Seat deleted successfully.", HttpStatus.OK);
    }
}