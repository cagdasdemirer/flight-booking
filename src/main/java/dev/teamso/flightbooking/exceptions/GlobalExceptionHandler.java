package dev.teamso.flightbooking.exceptions;

import dev.teamso.flightbooking.model.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFlightNotFoundException(FlightNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode().toString());
        return new ResponseEntity<>(errorResponse, ex.getErrorCode());
    }

    @ExceptionHandler(FlightBadRequestException.class)
    public ResponseEntity<ErrorResponse> handleFlightBadRequestException(FlightBadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode().toString());
        return new ResponseEntity<>(errorResponse, ex.getErrorCode());
    }
}