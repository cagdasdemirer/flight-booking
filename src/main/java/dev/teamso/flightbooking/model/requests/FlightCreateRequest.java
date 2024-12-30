package dev.teamso.flightbooking.model.requests;

import java.time.LocalDateTime;

public class FlightCreateRequest {
    private Long id;
    private String name;
    private String departure;
    private LocalDateTime departureAt;
    private String arrival;
    private LocalDateTime arriveAt;
    private int economySeatCount;
    private double defaultEconomyPrice;
    private int businessSeatCount;
    private double defaultBusinessPrice;

    public FlightCreateRequest(Long id, String name, String departure, LocalDateTime departureAt, String arrival, LocalDateTime arriveAt, int economySeatCount, double defaultEconomyPrice, int businessSeatCount, double defaultBusinessPrice) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
