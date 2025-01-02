package dev.teamso.flightbooking.service;

import dev.teamso.flightbooking.exceptions.FlightAlreadyExistException;
import dev.teamso.flightbooking.exceptions.FlightBadRequestException;
import dev.teamso.flightbooking.exceptions.FlightNotFoundException;
import dev.teamso.flightbooking.model.dto.FlightCreateRequest;
import dev.teamso.flightbooking.model.dto.FlightUpdateRequest;
import dev.teamso.flightbooking.model.entities.Flight;
import dev.teamso.flightbooking.repository.JpaFlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightServiceTest {

    @Mock
    private JpaFlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    private FlightCreateRequest flightCreateRequest;
    private FlightUpdateRequest flightUpdateRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        flightCreateRequest = new FlightCreateRequest(
                "FlightName",
                "NYC",
                LocalDateTime.now().plusHours(2),
                "LA",
                LocalDateTime.now().plusHours(5),
                100,
                50,
                20,
                500.0
        );

        flightUpdateRequest = new FlightUpdateRequest(
                "UpdatedFlightName",
                "NYC",
                LocalDateTime.now().plusHours(3),
                "LA",
                LocalDateTime.now().plusHours(6)
        );
    }

    @Test
    void testCreateFlight() {
        when(flightRepository.findByName(flightCreateRequest.getName())).thenReturn(Optional.empty());
        when(flightRepository.save(Mockito.any(Flight.class))).thenReturn(new Flight());

        Flight flight = flightService.createFlight(flightCreateRequest);

        assertNotNull(flight);
        verify(flightRepository, times(1)).save(Mockito.any(Flight.class));
    }

    @Test
    void testCreateFlight_FlightAlreadyExists() {
        when(flightRepository.findByName(flightCreateRequest.getName())).thenReturn(Optional.of(new Flight()));

        FlightAlreadyExistException exception = assertThrows(FlightAlreadyExistException.class, () -> flightService.createFlight(flightCreateRequest));

        assertEquals("Flight with name FlightName already exists.", exception.getMessage());
    }

    @Test
    void testUpdateFlight() {
        Long flightId = 1L;
        Flight existingFlight = new Flight();
        existingFlight.setId(flightId);
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));
        when(flightRepository.save(Mockito.any(Flight.class))).thenReturn(existingFlight);

        Flight flight = flightService.updateFlight(flightId, flightUpdateRequest);

        assertNotNull(flight);
        verify(flightRepository, times(1)).save(Mockito.any(Flight.class));
    }

    @Test
    void testUpdateFlight_FlightNotFound() {
        Long flightId = 1L;
        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        FlightNotFoundException exception = assertThrows(FlightNotFoundException.class, () -> flightService.updateFlight(flightId, flightUpdateRequest));

        assertEquals("Flight with id 1 not found.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getErrorCode().value());
    }

    @Test
    void testDeleteFlight() {
        Long flightId = 1L;
        Flight existingFlight = new Flight();
        existingFlight.setId(flightId);
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));

        flightService.deleteFlight(flightId);

        verify(flightRepository, times(1)).delete(existingFlight);
    }

    @Test
    void testDeleteFlight_FlightNotFound() {
        Long flightId = 1L;
        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        FlightNotFoundException exception = assertThrows(FlightNotFoundException.class, () -> flightService.deleteFlight(flightId));

        assertEquals("Flight with id 1 not found.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getErrorCode().value());

    }

    @Test
    void testValidateFlightTimes_DepartureBeforeArrival() {
        FlightCreateRequest invalidRequest = new FlightCreateRequest(
                "FlightName",
                "NYC",
                LocalDateTime.now().plusHours(5),
                "LA",
                LocalDateTime.now().plusHours(3),
                100,
                50,
                20,
                500.0
        );

        FlightBadRequestException exception = assertThrows(FlightBadRequestException.class, () -> flightService.createFlight(invalidRequest));

        assertEquals("Arrival time cannot be before departure time.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getErrorCode().value());
    }

    @Test
    void testGetFlightById_FlightNotFound() {
        Long flightId = 1L;
        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        FlightNotFoundException exception = assertThrows(FlightNotFoundException.class, () -> flightService.getFlightById(flightId));

        assertEquals("Flight with id 1 not found.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getErrorCode().value());

    }
}
