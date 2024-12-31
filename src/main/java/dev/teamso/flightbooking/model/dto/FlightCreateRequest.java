package dev.teamso.flightbooking.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class FlightCreateRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Departure location is mandatory")
    private String departure;
    @NotNull(message = "Departure time is mandatory")
    private LocalDateTime departureAt;
    @NotBlank(message = "Arrival location is mandatory")
    private String arrival;
    @NotNull(message = "Arrival time is mandatory")
    private LocalDateTime arriveAt;
    @Positive(message = "Economy seat count must be positive")
    private int economySeatCount;
    @Positive(message = "Default economy price must be positive")
    private double defaultEconomyPrice;
    @Positive(message = "Business seat count must be positive")
    private int businessSeatCount;
    @Positive(message = "Default business price must be positive")
    private double defaultBusinessPrice;

    public FlightCreateRequest(String name, String departure, LocalDateTime departureAt, String arrival, LocalDateTime arriveAt, int economySeatCount, double defaultEconomyPrice, int businessSeatCount, double defaultBusinessPrice) {
        this.name = name;
        this.departure = departure;
        this.departureAt = departureAt;
        this.arrival = arrival;
        this.arriveAt = arriveAt;
        this.economySeatCount = economySeatCount;
        this.defaultEconomyPrice = defaultEconomyPrice;
        this.businessSeatCount = businessSeatCount;
        this.defaultBusinessPrice = defaultBusinessPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public LocalDateTime getDepartureAt() {
        return departureAt;
    }

    public void setDepartureAt(LocalDateTime departureAt) {
        this.departureAt = departureAt;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public LocalDateTime getArriveAt() {
        return arriveAt;
    }

    public void setArriveAt(LocalDateTime arriveAt) {
        this.arriveAt = arriveAt;
    }

    public int getEconomySeatCount() {
        return economySeatCount;
    }

    public void setEconomySeatCount(int economySeatCount) {
        this.economySeatCount = economySeatCount;
    }

    public double getDefaultEconomyPrice() {
        return defaultEconomyPrice;
    }

    public void setDefaultEconomyPrice(double defaultEconomyPrice) {
        this.defaultEconomyPrice = defaultEconomyPrice;
    }

    public int getBusinessSeatCount() {
        return businessSeatCount;
    }

    public void setBusinessSeatCount(int businessSeatCount) {
        this.businessSeatCount = businessSeatCount;
    }

    public double getDefaultBusinessPrice() {
        return defaultBusinessPrice;
    }

    public void setDefaultBusinessPrice(double defaultBusinessPrice) {
        this.defaultBusinessPrice = defaultBusinessPrice;
    }
}
