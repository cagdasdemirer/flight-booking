package dev.teamso.flightbooking.exceptions;

import org.springframework.http.HttpStatus;

public class SeatNotFoundException extends RuntimeException {
    private final HttpStatus errorCode;

    public SeatNotFoundException(String message) {
        super(message);
        this.errorCode = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }
}
