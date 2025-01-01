package dev.teamso.flightbooking.exceptions;

import org.springframework.http.HttpStatus;

public class FlightAlreadyExistException extends RuntimeException {
    private final HttpStatus errorCode;

    public FlightAlreadyExistException(String message) {
        super(message);
        this.errorCode = HttpStatus.CONFLICT;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }
}
