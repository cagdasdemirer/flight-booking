package dev.teamso.flightbooking.exceptions;

import dev.teamso.flightbooking.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class FlightNotFoundException extends RuntimeException {
    private final HttpStatus errorCode;

    public FlightNotFoundException(String message) {
        super(message);
        this.errorCode = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }
}

