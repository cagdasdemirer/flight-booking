package dev.teamso.flightbooking.exceptions;

import org.springframework.http.HttpStatus;

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

