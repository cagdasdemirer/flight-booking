package dev.teamso.flightbooking.controller;

import dev.teamso.flightbooking.model.dto.OrderRequest;
import dev.teamso.flightbooking.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/order")
@Tag(name = "Order Controller", description = "Controller for purchasing seats")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/{flightId}/{seatNumber}")
    @Operation(summary = "Purchase a seat in a flight", description = "Purchase a seat in a flight by providing the flight ID and seat number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seat purchased successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "402", description = "Payment Required."),
            @ApiResponse(responseCode = "404", description = "Flight or seat not found."),
            @ApiResponse(responseCode = "409", description = "Seat already purchased or seat changed."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    })
    public CompletableFuture<ResponseEntity<String>> purchaseSeat(
            @PathVariable Long flightId,
            @PathVariable int seatNumber,
            @RequestBody OrderRequest request) {
        logger.info("Purchasing seat with number: {} in flight with ID: {}", seatNumber, flightId);
        return orderService.purchaseSeat(flightId, seatNumber, request.getPayment())
                .thenApply(result -> ResponseEntity.ok("Seat purchased successfully."));
    }
}
