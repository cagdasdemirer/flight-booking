package dev.teamso.flightbooking.model;

import java.time.LocalDateTime;
import java.util.List;

public class FlightDetailResponse {
    private long id;
    private String name;
    private String departure;
    private LocalDateTime departureAt;
    private String arrival;
    private LocalDateTime arriveAt;
    private int totalEconomySeats;
    private int availableEconomySeats;
    private int totalBusinessSeats;
    private int availableBusinessSeats;
    private List<Seat> seats;

    public FlightDetailResponse(Flight flight) {
        this.id = flight.getId();
        this.name = flight.getName();
        this.departure = flight.getDeparture();
        this.departureAt = flight.getDepartureAt();
        this.arrival = flight.getArrival();
        this.arriveAt = flight.getArriveAt();
        this.totalEconomySeats = flight.totalEconomySeats();
        this.availableEconomySeats = flight.availableEconomySeats();
        this.totalBusinessSeats = flight.totalBusinessSeats();
        this.availableBusinessSeats = flight.availableBusinessSeats();
        this.seats = flight.getSeats();
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

    public int getTotalEconomySeats() {
        return totalEconomySeats;
    }

    public int getAvailableEconomySeats() {
        return availableEconomySeats;
    }

    public int getTotalBusinessSeats() {
        return totalBusinessSeats;
    }

    public int getAvailableBusinessSeats() {
        return availableBusinessSeats;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
