package dev.teamso.flightbooking.exceptions;

import org.springframework.http.HttpStatus;

public class FlightBadRequestException extends RuntimeException {
    private final HttpStatus errorCode;

    public FlightBadRequestException(String message) {
        super(message);
        this.errorCode = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }
}
