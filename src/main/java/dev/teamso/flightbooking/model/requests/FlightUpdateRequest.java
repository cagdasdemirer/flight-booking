package dev.teamso.flightbooking.model.requests;

import java.time.LocalDateTime;

public class FlightUpdateRequest {
    private String name;
    private String departure;
    private LocalDateTime departureAt;
    private String arrival;
    private LocalDateTime arriveAt;

    public FlightUpdateRequest(String name, String departure, LocalDateTime departureAt, String arrival, LocalDateTime arriveAt) {
        this.name = name;
        this.departure = departure;
        this.departureAt = departureAt;
        this.arrival = arrival;
        this.arriveAt = arriveAt;
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
}
