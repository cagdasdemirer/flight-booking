package dev.teamso.flightbooking.model;

import java.time.LocalDateTime;
import java.util.List;

public class Flight {
    private long id;
    private String name;
    private String departure;
    private LocalDateTime departureAt;
    private String arrival;
    private LocalDateTime arriveAt;
    private List<Seat> seats;

    public Flight(long id, String name, String departure, LocalDateTime departureAt, String arrival, LocalDateTime arriveAt, List<Seat> seats) {
        this.id = id;
        this.name = name;
        this.departure = departure;
        this.departureAt = departureAt;
        this.arrival = arrival;
        this.arriveAt = arriveAt;
        this.seats = seats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", departure='" + departure + '\'' +
                ", departureAt=" + departureAt +
                ", arrival='" + arrival + '\'' +
                ", arriveAt=" + arriveAt +
                '}';
    }
}
