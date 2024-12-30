package dev.teamso.flightbooking.model;

import java.time.LocalDateTime;

public class FlightSummaryResponse {
    private long id;
    private String name;
    private String departure;
    private LocalDateTime departureAt;
    private String arrival;
    private LocalDateTime arriveAt;
    private int availableEconomySeats;
    private int availableBusinessSeats;

    public FlightSummaryResponse(Flight flight) {
        this.id = flight.getId();
        this.name = flight.getName();
        this.departure = flight.getDeparture();
        this.departureAt = flight.getDepartureAt();
        this.arrival = flight.getArrival();
        this.arriveAt = flight.getArriveAt();
        this.availableEconomySeats = flight.availableEconomySeats();
        this.availableBusinessSeats = flight.availableBusinessSeats();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeparture() {
        return departure;
    }

    public LocalDateTime getDepartureAt() {
        return departureAt;
    }

    public String getArrival() {
        return arrival;
    }

    public LocalDateTime getArriveAt() {
        return arriveAt;
    }

    public int getAvailableEconomySeats() {
        return availableEconomySeats;
    }

    public int getAvailableBusinessSeats() {
        return availableBusinessSeats;
    }
}
