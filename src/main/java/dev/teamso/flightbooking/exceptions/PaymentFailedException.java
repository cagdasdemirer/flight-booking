package dev.teamso.flightbooking.exceptions;

import org.springframework.http.HttpStatus;

public class PaymentFailedException extends RuntimeException {
    private final HttpStatus errorCode;

    public PaymentFailedException(String message) {
        super(message);
        this.errorCode = HttpStatus.PAYMENT_REQUIRED;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }
}
