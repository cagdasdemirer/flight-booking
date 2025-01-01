package dev.teamso.flightbooking.model.dto;

import dev.teamso.flightbooking.model.entities.SeatType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class SeatCreateAndUpdateRequest {
    @NotBlank(message = "Seat type is mandatory (ECONOMY or BUSINESS)")
    private SeatType type;
    @Positive(message = "Price must be positive")
    private double price;

    public SeatCreateAndUpdateRequest(SeatType type, double price) {
        this.type = type;
        this.price = price;
    }

    public SeatType getType() {
        return type;
    }

    public void setType(SeatType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
