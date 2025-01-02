package dev.teamso.flightbooking.service;

import dev.teamso.flightbooking.exceptions.PaymentFailedException;
import dev.teamso.flightbooking.exceptions.PurchaseFailedException;
import dev.teamso.flightbooking.exceptions.SeatAlreadyPurchasedException;
import dev.teamso.flightbooking.exceptions.SeatNotFoundException;
import dev.teamso.flightbooking.model.entities.Seat;
import dev.teamso.flightbooking.repository.JpaSeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private JpaSeatRepository seatRepository;

    @Transactional
    public CompletableFuture<Boolean> purchaseSeat(Long flightId, int seatNumber, boolean payment) {
        return CompletableFuture.supplyAsync(() -> {
            logger.debug("Purchasing seat with ID: {} in flight with ID: {}", seatNumber, flightId);

            try {
                Seat seat = seatRepository.findByFlightIdAndSeatNumber(flightId, seatNumber)
                        .orElseThrow(() -> new SeatNotFoundException("Seat " + seatNumber + " with flight_id " + flightId + " not found."));

                if (seat.isPurchased()) {
                    logger.error("Seat with ID: {} in flight with ID: {} is already purchased", seatNumber, flightId);
                    throw new SeatAlreadyPurchasedException("Seat " + seatNumber + " with flight_id " + flightId + " is already purchased.");
                }

                simulatePaymentProcessing(payment);

                seat.setPurchased(true);
                seatRepository.save(seat);

                return true;

            } catch (OptimisticLockException e) {
                Seat updatedSeat = seatRepository.findByFlightIdAndSeatNumber(flightId, seatNumber).orElse(null);
                if (updatedSeat != null && updatedSeat.isPurchased()) {
                    logger.error("Seat with ID: {} in flight with ID: {} is already purchased", seatNumber, flightId);
                    throw new SeatAlreadyPurchasedException("Seat " + seatNumber + " with flight_id " + flightId + " is already purchased.");
                } else {
                    logger.error("Conflict occurred while purchasing seat with ID: {} in flight with ID: {}", seatNumber, flightId);
                    throw new PurchaseFailedException("Seat " + seatNumber + " with flight_id " + flightId + " purchase conflicted.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
                logger.error("Failed to purchase seat with ID: {} in flight with ID: {}", seatNumber, flightId);
                throw new PaymentFailedException("Seat " + seatNumber + " with flight_id " + flightId + " payment interrupted.");
            }
        });
    }

    private void simulatePaymentProcessing(boolean payment) throws InterruptedException {
        Thread.sleep(1000);
        if (!payment) {
            throw new PaymentFailedException("Payment failed during seat purchase.");
        }
    }

}
