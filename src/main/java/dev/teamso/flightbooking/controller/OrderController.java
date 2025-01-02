package dev.teamso.flightbooking.controller;

import dev.teamso.flightbooking.model.dto.OrderRequest;
import dev.teamso.flightbooking.service.OrderService;
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
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/{flightId}/{seatNumber}")
    public CompletableFuture<ResponseEntity<String>> purchaseSeat(
            @PathVariable Long flightId,
            @PathVariable int seatNumber,
            @RequestBody OrderRequest request) {

        return orderService.purchaseSeat(flightId, seatNumber, request.getPayment())
                .thenApply(result -> ResponseEntity.ok("Seat purchased successfully."));
    }
}
