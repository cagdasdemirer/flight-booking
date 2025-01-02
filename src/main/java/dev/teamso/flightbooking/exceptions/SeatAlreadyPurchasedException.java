package dev.teamso.flightbooking.exceptions;

import org.springframework.http.HttpStatus;

public class SeatAlreadyPurchasedException extends RuntimeException {
    private final HttpStatus errorCode;

    public SeatAlreadyPurchasedException(String message) {
        super(message);
        this.errorCode = HttpStatus.CONFLICT;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }
}
