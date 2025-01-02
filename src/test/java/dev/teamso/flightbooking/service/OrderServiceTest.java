package dev.teamso.flightbooking.service;

import dev.teamso.flightbooking.exceptions.SeatAlreadyPurchasedException;
import dev.teamso.flightbooking.model.entities.Flight;
import dev.teamso.flightbooking.model.entities.Seat;
import dev.teamso.flightbooking.model.entities.SeatType;
import dev.teamso.flightbooking.repository.JpaFlightRepository;
import dev.teamso.flightbooking.repository.JpaSeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private JpaSeatRepository seatRepository;

    @NotNull
    private JpaFlightRepository flightRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testPurchaseSeat_Success() throws Exception {
        Flight flight = new Flight();
        flight.setId(1L);

        Seat seat = new Seat(1, SeatType.ECONOMY, 200.0);
        seat.setFlight(flight);
        seat.setPurchased(false);

        when(seatRepository.findByFlightIdAndSeatNumber(1L, 1)).thenReturn(Optional.of(seat));
        when(seatRepository.save(any(Seat.class))).thenReturn(seat);

        boolean result = orderService.purchaseSeat(1L, 1, true).get();

        assertTrue(result, "Seat should be successfully purchased.");
        assertTrue(seat.isPurchased(), "Seat should be marked as purchased.");
        verify(seatRepository, times(1)).save(any(Seat.class));
    }

    @Test
    void testPurchaseSeat_SeatAlreadyPurchased() throws Exception {
        Flight flight = new Flight();
        flight.setId(1L);

        Seat seat = new Seat(1, SeatType.ECONOMY, 200.0);
        seat.setFlight(flight);
        seat.setPurchased(true);

        when(seatRepository.findByFlightIdAndSeatNumber(1L, 1)).thenReturn(Optional.of(seat));

        Exception exception = assertThrows(ExecutionException.class, () -> {
            orderService.purchaseSeat(1L, 1, true).get();
        });

        assertTrue(exception.getCause() instanceof SeatAlreadyPurchasedException);
        assertEquals("Seat 1 with flight_id 1 is already purchased.", exception.getCause().getMessage());
    }





    @Test
    void testPurchaseSeat_OptimisticLockException_SeatNotPurchased() throws Exception {
        Flight flight = new Flight();
        flight.setId(1L);

        Seat seat = new Seat(1, SeatType.ECONOMY, 200.0);
        seat.setFlight(flight);
        seat.setPurchased(false);

        when(seatRepository.findByFlightIdAndSeatNumber(1L, 1)).thenReturn(Optional.of(seat));
        when(seatRepository.save(any(Seat.class))).thenReturn(seat);

        boolean result = orderService.purchaseSeat(1L, 1, true).get();

        assertTrue(result, "Seat should be successfully purchased after optimistic lock resolution.");
        assertTrue(seat.isPurchased(), "Seat should be marked as purchased.");
        verify(seatRepository, times(1)).save(any(Seat.class));
    }


}