package dev.teamso.flightbooking.exceptions;

import org.springframework.http.HttpStatus;

public class PurchaseFailedException extends RuntimeException {
    private final HttpStatus errorCode;

    public PurchaseFailedException(String message) {
        super(message);
        this.errorCode = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }
}
