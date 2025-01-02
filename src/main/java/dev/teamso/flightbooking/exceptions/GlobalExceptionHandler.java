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

    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSeatNotFoundException(SeatNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode().toString());
        return new ResponseEntity<>(errorResponse, ex.getErrorCode());
    }

    @ExceptionHandler(FlightBadRequestException.class)
    public ResponseEntity<ErrorResponse> handleFlightBadRequestException(FlightBadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode().toString());
        return new ResponseEntity<>(errorResponse, ex.getErrorCode());
    }

    @ExceptionHandler(FlightAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleFlightAlreadyExistException(FlightAlreadyExistException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode().toString());
        return new ResponseEntity<>(errorResponse, ex.getErrorCode());
    }

    @ExceptionHandler(SeatAlreadyPurchasedException.class)
    public ResponseEntity<ErrorResponse> handleSeatAlreadyPurchasedException(SeatAlreadyPurchasedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode().toString());
        return new ResponseEntity<>(errorResponse, ex.getErrorCode());
    }

    @ExceptionHandler(PurchaseFailedException.class)
    public ResponseEntity<ErrorResponse> handlePurchaseFailedException(PurchaseFailedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode().toString());
        return new ResponseEntity<>(errorResponse, ex.getErrorCode());
    }

    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<ErrorResponse> handlePaymentFailedException(PaymentFailedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode().toString());
        return new ResponseEntity<>(errorResponse, ex.getErrorCode());
    }
}