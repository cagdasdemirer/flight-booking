package dev.teamso.flightbooking.service;

import dev.teamso.flightbooking.exceptions.FlightNotFoundException;
import dev.teamso.flightbooking.exceptions.SeatNotFoundException;
import dev.teamso.flightbooking.model.dto.SeatCreateAndUpdateRequest;
import dev.teamso.flightbooking.model.entities.Seat;
import dev.teamso.flightbooking.model.entities.Flight;
import dev.teamso.flightbooking.model.entities.SeatType;
import dev.teamso.flightbooking.repository.JpaSeatRepository;
import dev.teamso.flightbooking.repository.JpaFlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class SeatServiceTest {

    @Mock
    private JpaSeatRepository seatRepository;

    @Mock
    private JpaFlightRepository flightRepository;

    @InjectMocks
    private SeatService seatService;

    private Seat seat;
    private Flight flight;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        flight = new Flight();
        flight.setId(1L);
        seat = new Seat(1, SeatType.ECONOMY, 100.0);
    }

    @Test
    void testAddSeat_FlightNotFound() {
        when(flightRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(FlightNotFoundException.class, () -> seatService.addSeat(1L, new SeatCreateAndUpdateRequest(SeatType.ECONOMY, 100.0)));
    }

    @Test
    void testAddSeat_Success() {
        Flight flight = new Flight();
        flight.setId(1L);

        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            Seat seat = new Seat(i, SeatType.ECONOMY, 200.0);
            seat.setFlight(flight);
            seats.add(seat);
        }
        flight.setSeats(seats);

        when(flightRepository.findById(any(Long.class))).thenReturn(Optional.of(flight));
        when(seatRepository.save(any(Seat.class))).thenAnswer(invocation -> {
            Seat seat = invocation.getArgument(0);
            flight.getSeats().add(seat);
            return seat;
        });

        SeatCreateAndUpdateRequest seatRequest = new SeatCreateAndUpdateRequest(SeatType.ECONOMY, 200.0);
        Seat seat = seatService.addSeat(1L, seatRequest);

        assertFalse(flight.getSeats().isEmpty(), "Seats list should not be empty after adding a seat");
        verify(seatRepository, times(1)).save(any(Seat.class));
        assertEquals(51, flight.getSeats().size(), "Seat count should be 51 after adding a seat");
    }


    @Test
    void testUpdateSeat_SeatNotFound() {
        when(seatRepository.findByFlightIdAndSeatNumber(any(Long.class), anyInt())).thenReturn(Optional.empty());
        SeatCreateAndUpdateRequest updatedSeatRequest = new SeatCreateAndUpdateRequest(SeatType.BUSINESS, 200.0);
        assertThrows(SeatNotFoundException.class, () -> seatService.updateSeat(1L, 1, updatedSeatRequest));
    }

    @Test
    void testUpdateSeat_Success() {
        when(seatRepository.findByFlightIdAndSeatNumber(any(Long.class), anyInt())).thenReturn(Optional.of(seat));
        when(seatRepository.save(any(Seat.class))).thenReturn(seat);
        SeatCreateAndUpdateRequest updatedSeatRequest = new SeatCreateAndUpdateRequest(SeatType.BUSINESS, 200.0);
        Seat updatedSeat = seatService.updateSeat(1L, 1, updatedSeatRequest);
        assertNotNull(updatedSeat);
        assertEquals(SeatType.BUSINESS, updatedSeat.getType());
        assertEquals(200.0, updatedSeat.getPrice());
        verify(seatRepository, times(1)).save(any(Seat.class));
    }

    @Test
    void testDeleteSeat_SeatNotFound() {
        when(seatRepository.findByFlightIdAndSeatNumber(any(Long.class), anyInt())).thenReturn(Optional.empty());
        assertThrows(SeatNotFoundException.class, () -> seatService.deleteSeat(1L, 1));
    }

    @Test
    void testDeleteSeat_Success() {
        when(seatRepository.findByFlightIdAndSeatNumber(any(Long.class), anyInt())).thenReturn(Optional.of(seat));
        seatService.deleteSeat(1L, 1);
        verify(seatRepository, times(1)).delete(seat);
    }


    @Test
    void testUpdateSeat_PriceUpdated() {
        Seat seat = new Seat();
        seat.setId(1L);
        seat.setSeatNumber(1);
        seat.setType(SeatType.ECONOMY);
        seat.setPrice(200.0);

        when(seatRepository.findByFlightIdAndSeatNumber(any(Long.class), anyInt()))
                .thenReturn(Optional.of(seat));

        SeatCreateAndUpdateRequest updatedSeatRequest = new SeatCreateAndUpdateRequest(SeatType.BUSINESS, 250.0);
        when(seatRepository.save(any(Seat.class)))
                .thenReturn(seat);

        Seat updatedSeat = seatService.updateSeat(1L, 1, updatedSeatRequest);
        assertEquals(250.0, updatedSeat.getPrice(), "Seat price should be updated to 250.0");
    }
}

