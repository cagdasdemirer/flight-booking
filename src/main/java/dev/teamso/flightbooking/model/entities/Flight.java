package dev.teamso.flightbooking.model.entities;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.List;
import java.util.OptionalInt;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String departure;
    @Column(nullable = false)
    private LocalDateTime departureAt;
    @Column(nullable = false)
    private String arrival;
    @Column(nullable = false)
    private LocalDateTime arriveAt;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;

    public Flight(String name, String departure, LocalDateTime departureAt, String arrival, LocalDateTime arriveAt, List<Seat> seats) {
        this.name = name;
        this.departure = departure;
        this.departureAt = departureAt;
        this.arrival = arrival;
        this.arriveAt = arriveAt;
        this.seats = seats;
    }

    public Flight() {
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

    public int totalEconomySeats() {
        return Math.toIntExact(seats.stream().filter(seat -> seat.getType() == SeatType.ECONOMY).count());
    }

    public int availableEconomySeats() {
        return Math.toIntExact(seats.stream().filter(seat -> seat.getType() == SeatType.ECONOMY && !seat.isPurchased()).count());
    }

    public int totalBusinessSeats() {
        return Math.toIntExact(seats.stream().filter(seat -> seat.getType() == SeatType.BUSINESS).count());
    }

    public int availableBusinessSeats() {
        return Math.toIntExact(seats.stream().filter(seat -> seat.getType() == SeatType.BUSINESS && !seat.isPurchased()).count());
    }

    public OptionalInt getMaxSeatNumber() {
        return seats.stream()
                .mapToInt(Seat::getSeatNumber)
                .max();
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
